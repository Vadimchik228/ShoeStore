package com.vasche.shoestore.repository;

import com.vasche.shoestore.domain.cartItem.CartItem;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository {

    Optional<CartItem> findById(Long id);

    List<CartItem> findAllByUserId(Long userId);

    void assignToUserById(Long cartItemId, Long userId);

    void update(CartItem cartItem);

    void create(CartItem cartItem);

    void delete(Long id);

}
