package com.vasche.shoestore.repository;

import com.vasche.shoestore.domain.orderItem.OrderItem;

import java.util.List;
import java.util.Optional;

public interface OrderItemRepository {

    Optional<OrderItem> findById(Long id);

    List<OrderItem> findAllByOrderId(Long orderId);

    List<OrderItem> findAllByUserId(Long userId);

    void update(OrderItem orderItem);

    void create(OrderItem orderItem);

    void delete(Long id);

}
