package com.devsuoerior.dsdelivery.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devsuoerior.dsdelivery.entities.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
