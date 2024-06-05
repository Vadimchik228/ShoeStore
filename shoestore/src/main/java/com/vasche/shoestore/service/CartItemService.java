package com.vasche.shoestore.service;

import com.vasche.shoestore.domain.cartItem.CartItem;

import java.util.List;

public interface CartItemService {

    CartItem getById(Long id);

    List<CartItem> getAllByUserId(Long userId);

    CartItem update(CartItem cartItem);

    CartItem create(CartItem cartItem, Long userId);

    void delete(Long id);

}
