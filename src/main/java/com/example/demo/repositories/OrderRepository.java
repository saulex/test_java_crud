package com.example.demo.repositories;

import com.example.demo.models.Order;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;


@Repository
public interface OrderRepository extends CrudRepository<Order, Integer> {
}
