package com.vasche.shoestore.service;

import com.vasche.shoestore.domain.cartItem.CartItem;
import com.vasche.shoestore.domain.orderItem.OrderItem;
import com.vasche.shoestore.web.dto.cartItem.CartItemDto;
import com.vasche.shoestore.web.dto.orderItem.OrderItemDto;

import java.util.List;

public interface OrderItemService {

    OrderItemDto getById(Long id);

    List<OrderItemDto> getAllByOrderId(Long orderId);

    List<OrderItemDto> getAllByUserId(Long userId);

    OrderItemDto update(OrderItemDto orderItemDto);

    OrderItemDto create(CartItemDto cartItemDto, Long orderId);

    void delete(Long id);

}
