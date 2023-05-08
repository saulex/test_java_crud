package com.example.demo.repositories;

import com.example.demo.models.ShippingAddress;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShippingAddressRepository extends CrudRepository<ShippingAddress, Integer> {
}
