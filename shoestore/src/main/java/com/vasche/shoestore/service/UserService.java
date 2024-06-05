package com.vasche.shoestore.service;

import com.vasche.shoestore.domain.user.User;

public interface UserService {
    User getById(Long id);

    User getByUsername(String username);

    User update(User user);

    User create(User user);

    boolean isCartItemOwner(Long userId, Long cartItemId);

    boolean isOrderItemOwner(Long userId, Long orderItemId);

    boolean isOrderOwner(Long userId, Long orderId);

    void delete(Long id);
}
