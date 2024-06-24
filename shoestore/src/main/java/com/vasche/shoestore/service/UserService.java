package com.vasche.shoestore.service;

import com.vasche.shoestore.web.dto.user.UserDto;

public interface UserService {
    UserDto getById(Long id);

    UserDto getByUsername(String username);

    UserDto update(UserDto userDto);

    UserDto create(UserDto userDto);

    boolean isCartItemOwner(Long userId, Long cartItemId);

    boolean isOrderItemOwner(Long userId, Long orderItemId);

    boolean isOrderOwner(Long userId, Long orderId);

    void delete(Long id);
}
