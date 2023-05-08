package com.example.demo.services;

import com.example.demo.converter.OrderConverter;
import com.example.demo.converter.OrderProductsValuesConverter;
import com.example.demo.dto.OrderDto;
import com.example.demo.dto.OrderProductDto;
import com.example.demo.models.Order;
import com.example.demo.models.OrderProductsValues;
import com.example.demo.models.Product;
import com.example.demo.repositories.CustomerRepository;
import com.example.demo.repositories.OrderProductsValuesRepository;
import com.example.demo.repositories.OrderRepository;
import com.example.demo.repositories.ProductRepository;
import com.example.demo.response.CustomerCreationException;
import org.hibernate.PersistentObjectException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.OpenOption;
import java.sql.Date;
import java.util.ArrayList;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;


@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    OrderProductsValuesRepository orderProductsValuesRepository;
    @Autowired
    ProductRepository productRepository;

    public ArrayList<Order> getOrders() {
        return (ArrayList<Order>) orderRepository.findAll();
    }

    public Order saveOrder(Order order) {
        if (customerRepository.findById(order.getCustomer().getId()).isPresent()) {
            return orderRepository.save(order);
        } else {
            return new Order();
        }

    }

    @Transactional
    public OrderDto saveOrder(OrderDto order) {
        try {
            Order resul = new Order();
            if (order.getProducts() == null || order.getProducts().isEmpty()) {
                throw new CustomerCreationException("Error when Order was created", "Products order empty", null, null);
            }

            resul.setDate(new Date(System.currentTimeMillis()));
            resul.setCustomer(order.getCustomer());
            resul.setOrderNumber(order.getOrderNumber());
            resul.setPaymentType(order.getPaymentType());
            final Order orderModel = orderRepository.save(resul);

            order.getProducts().forEach((productProduct) -> {
                Optional<Product> productModel = productRepository.findById(productProduct.getProduct().getId());
                if (productModel.isPresent()) {
                    OrderProductsValues orderProduct = new OrderProductsValues();
                    orderProduct.setOrderId(orderModel.getId());
                    orderProduct.setProduct(productModel.get());
                    orderProduct.setQuantities(productProduct.getQuantities());
                    orderProduct.setTotal(productProduct.getQuantities() * productModel.get().getPrice());
                    orderProductsValuesRepository.save(orderProduct);
                }else {
                    throw new CustomerCreationException("Error when Order was created", "Product not found id: "+productProduct.getProduct().getId(), null, null);
                }
            });
            order.setId(orderModel.getId());
            return order;
        } catch (ConstraintViolationException e) {
            throw new CustomerCreationException("Error when Order was created", "Integrity constraint violation error: " + e.getMessage(), null, null);
        } catch (DataIntegrityViolationException e) {
            throw new CustomerCreationException("Error when creating the Order", null, "Data integrity violation error: " + e.getMessage(), null);
        } catch (PersistentObjectException e) {
            throw new CustomerCreationException("Error when creating the Order", null, null, "Persistent object error: " + e.getMessage());
        }

    }

    public  Optional<OrderDto> getOrderById(Integer id) {
        Optional<Order> order = orderRepository.findById(id);
        Optional<OrderDto> orderDtoResult = Optional.empty();

        if (order.isPresent()) {
            OrderDto orderDto = OrderConverter.orderModelToOrderDto(order.get());
            List<OrderProductsValues> orderProductsValues = orderProductsValuesRepository.findByOrderId(order.get().getId());
            AtomicReference<Float> totalAmount = new AtomicReference<>(0f);
            AtomicReference<Float> totalWeight = new AtomicReference<>(0f);
            orderProductsValues.forEach((orderProductsValue) -> {
                OrderProductDto orderProductDto = OrderProductsValuesConverter.convert(orderProductsValue);
                Product product = orderProductsValue.getProduct();
                float productTotal = orderProductDto.getQuantities() * product.getPrice();
                totalAmount.updateAndGet(oldValue -> oldValue + productTotal);
                float productWeight = orderProductDto.getQuantities() * product.getWeight();
                totalWeight.updateAndGet(oldValue -> oldValue + productWeight);

                orderProductDto.setProduct(product);
                orderDto.getProducts().add(orderProductDto);
            });
            orderDto.setTotalAmount(totalAmount.get());
            orderDto.setTotalWeight(totalWeight.get());
            orderDtoResult = Optional.of(orderDto);
        }
        return orderDtoResult;
    }

    @Transactional
    public boolean deleteOrder(Integer id) {
        try {
            orderProductsValuesRepository.deleteByOrderId(id);
            orderRepository.deleteById(id);
            return true;
        } catch (ConstraintViolationException e) {
            throw new CustomerCreationException("Error when Order was deleted", "Integrity constraint violation error: " + e.getMessage(), null, null);
        } catch (DataIntegrityViolationException e) {
            throw new CustomerCreationException("Error when Order was deleted", null, "Data integrity violation error: " + e.getMessage(), null);
        } catch (PersistentObjectException e) {
            throw new CustomerCreationException("Error when Order was deleted", null, null, "Persistent object error: " + e.getMessage());
        }
    }
}
