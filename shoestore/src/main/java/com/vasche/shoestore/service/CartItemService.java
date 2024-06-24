package com.vasche.shoestore.service;

import com.vasche.shoestore.domain.cartItem.CartItem;
import com.vasche.shoestore.web.dto.cartItem.CartItemDto;

import java.util.List;

public interface CartItemService {

    CartItemDto getById(Long id);

    List<CartItemDto> getAllByUserId(Long userId);

    CartItemDto update(CartItemDto cartItemDto);

    CartItemDto create(CartItemDto cartItemDto, Long userId);

    void delete(Long id);

}
