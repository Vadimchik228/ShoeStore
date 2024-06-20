package com.vasche.shoestore.service.impl;

import com.vasche.shoestore.domain.cartItem.CartItem;
import com.vasche.shoestore.domain.exception.ResourceNotFoundException;
import com.vasche.shoestore.domain.user.User;
import com.vasche.shoestore.repository.CartItemRepository;
import com.vasche.shoestore.repository.ShoeRepository;
import com.vasche.shoestore.service.CartItemService;
import com.vasche.shoestore.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements CartItemService {

    private final CartItemRepository cartItemRepository;
    private final ShoeRepository shoeRepository;
    private final UserService userService;

    @Override
    @Transactional(readOnly = true)
//    @Cacheable(value = "CartItemService::getById", key = "#id")
    public CartItem getById(Long id) {
        return cartItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cart item not found."));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CartItem> getAllByUserId(Long userId) {
        return cartItemRepository.findAllByUserId(userId);
    }

    @Override
    @Transactional
//    @Caching(put = @CachePut(value = "CartItemService::getById", key = "cartItem.id"))
    public CartItem update(CartItem cartItem) {
        if (shoeRepository.findById(cartItem.getShoe().getId()).isEmpty()) {
            throw new IllegalStateException("There is no such shoe in the database.");
        }
        cartItemRepository.save(cartItem);
        return cartItem;
    }

    @Override
    @Transactional
//    @Caching(cacheable = @Cacheable(value = "CartItemService::getById", key = "cartItem.id"))
    public CartItem create(CartItem cartItem, Long userId) {
        User user = userService.getById(userId);
        if (shoeRepository.findById(cartItem.getShoe().getId()).isEmpty()) {
            throw new IllegalStateException("There is no such shoe in the database.");
        }
//        user.getCartItems().add(cartItem);
        userService.update(user);
        return cartItem;
    }

    @Override
    @Transactional
//    @CacheEvict(value = "CartItemService::getById", key = "#id")
    public void delete(Long id) {
        cartItemRepository.deleteById(id);
    }
}
