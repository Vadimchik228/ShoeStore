package com.vasche.shoestore.service.impl;

import com.vasche.shoestore.domain.cartItem.CartItem;
import com.vasche.shoestore.domain.exception.ResourceNotFoundException;
import com.vasche.shoestore.domain.shoe.Shoe;
import com.vasche.shoestore.domain.user.User;
import com.vasche.shoestore.repository.CartItemRepository;
import com.vasche.shoestore.repository.ShoeRepository;
import com.vasche.shoestore.repository.UserRepository;
import com.vasche.shoestore.service.CartItemService;
import com.vasche.shoestore.web.dto.cartItem.CartItemDto;
import com.vasche.shoestore.web.mappers.CartItemMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements CartItemService {

    private final CartItemRepository cartItemRepository;
    private final ShoeRepository shoeRepository;
    private final UserRepository userRepository;
    private final CartItemMapper cartItemMapper;

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "CartItemService::getById", key = "#id")
    public CartItemDto getById(Long id) {
        Optional<CartItem> cartItem = cartItemRepository.findById(id);
        if (cartItem.isPresent()) {
            var dto = cartItemMapper.toDto(cartItem.get());
            dto.setShoeId(cartItem.get().getId());
            return dto;
        } else {
            throw new ResourceNotFoundException("Cart item not found.");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<CartItemDto> getAllByUserId(Long userId) {
        return cartItemRepository.findAllByUserId(userId)
                .stream()
                .map(cartItem -> {
                    var dto = cartItemMapper.toDto(cartItem);
                    dto.setShoeId(cartItem.getId());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    @CachePut(value = "CartItemService::getById", key = "#cartItemDto.id")
    public CartItemDto update(CartItemDto cartItemDto) {
        Optional<Shoe> shoe = shoeRepository.findById(cartItemDto.getShoeId());
        if (shoe.isEmpty()) {
            throw new IllegalStateException("There is no such shoe in the database.");
        }
        var cartItem = cartItemMapper.toEntity(cartItemDto);
        cartItem.setShoe(shoe.get());
        cartItemRepository.save(cartItem);
        return cartItemDto;
    }

    @Override
    @Transactional
    @CachePut(value = "CartItemService::getById", key = "#result.id", condition = "#result != null")
    public CartItemDto create(CartItemDto cartItemDto, Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new IllegalStateException("There is no such user in the database.");
        }
        Optional<Shoe> shoe = shoeRepository.findById(cartItemDto.getShoeId());
        if (shoe.isEmpty()) {
            throw new IllegalStateException("There is no such shoe in the database.");
        }
        var cartItem = cartItemMapper.toEntity(cartItemDto);
        cartItem.setShoe(shoe.get());
        cartItemRepository.save(cartItem);
        user.get().getCartItems().add(cartItem);
        cartItemDto.setId(cartItem.getId());
        return cartItemDto;
    }

    @Override
    @Transactional
    @CacheEvict(value = "CartItemService::getById", key = "#id")
    public void delete(Long id) {
        cartItemRepository.deleteById(id);
    }
}
