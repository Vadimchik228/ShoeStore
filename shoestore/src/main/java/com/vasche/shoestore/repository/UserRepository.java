package com.vasche.shoestore.repository;

import com.vasche.shoestore.domain.user.Role;
import com.vasche.shoestore.domain.user.User;

import java.util.Optional;


public interface UserRepository {
    Optional<User> findById(Long id);

    Optional<User> findByUsername(String username);

    void update(User user);

    void create(User user);

    void insertUserRole(Long userId, Role role);

    boolean isCartItemOwner(Long userId, Long cartItemId);

    boolean isOrderItemOwner(Long userId, Long orderItemId);

    boolean isOrderOwner(Long userId, Long orderId);

    void delete(Long id);
}
