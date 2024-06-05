package com.vasche.shoestore.service;

import com.vasche.shoestore.domain.order.Order;

import java.util.List;

public interface OrderService {

    Order getById(Long id);

    List<Order> getAll();

    List<Order> getAllByUserId(Long userId);

    Order update(Order order);

    Order create(Order order, Long userId);

    void delete(Long id);

}
