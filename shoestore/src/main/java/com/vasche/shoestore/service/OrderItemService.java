package com.vasche.shoestore.service;

import com.vasche.shoestore.domain.cartItem.CartItem;
import com.vasche.shoestore.domain.orderItem.OrderItem;

import java.util.List;

public interface OrderItemService {

    OrderItem getById(Long id);

    List<OrderItem> getAllByOrderId(Long orderId);

    List<OrderItem> getAllByUserId(Long userId);

    OrderItem update(OrderItem orderItem);

    OrderItem create(CartItem cartItem, Long orderId);

    void delete(Long id);

}
