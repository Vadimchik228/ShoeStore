package com.vasche.shoestore.repository;

import com.vasche.shoestore.domain.order.Order;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {

    Optional<Order> findById(Long id);

    List<Order> findAllByUserId(Long userId);

    List<Order> findAll();

    void update(Order order);

    void create(Order order);

    void delete(Long id);

}
