package com.vasche.shoestore.service;

import com.vasche.shoestore.domain.order.Order;
import com.vasche.shoestore.web.dto.order.OrderDto;

import java.util.List;

public interface OrderService {

    OrderDto getById(Long id);

    List<OrderDto> getAll();

    List<OrderDto> getAllByUserId(Long userId);

    OrderDto update(OrderDto order);

    OrderDto create(OrderDto order, Long userId);

    void delete(Long id);

}
