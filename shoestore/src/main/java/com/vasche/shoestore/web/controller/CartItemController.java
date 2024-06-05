package com.vasche.shoestore.web.controller;

import com.vasche.shoestore.domain.cartItem.CartItem;
import com.vasche.shoestore.service.CartItemService;
import com.vasche.shoestore.web.dto.cartItem.CartItemDto;
import com.vasche.shoestore.web.dto.validation.OnCreate;
import com.vasche.shoestore.web.mappers.CartItemMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cartItems")
@RequiredArgsConstructor
@Validated
// Валидация выполняется перед вызовом метода контроллера.
// Если валидация не удалась, возвращается ошибка 400 (Неправильный запрос)
@Tag(name = "Cart Item Controller", description = "Cart Item API")
public class CartItemController {

    private final CartItemService cartItemService;
    private final CartItemMapper cartItemMapper;

    @GetMapping("/{id}")
    @Operation(summary = "Get cart item by id")
    @PreAuthorize("@customSecurityExpression.canAccessCartItem(#id)")
    public CartItemDto getById(@PathVariable Long id) {
        CartItem cartItem = cartItemService.getById(id);
        return cartItemMapper.toDto(cartItem);
    }

    @GetMapping("/{userId}/cartItems")
    @Operation(summary = "Get all user's cart items")
    @PreAuthorize("@customSecurityExpression.canAccessUser(#userId)")
    public List<CartItemDto> getCartItemsByUserId(@PathVariable Long userId) {
        List<CartItem> items = cartItemService.getAllByUserId(userId);
        return cartItemMapper.toDto(items);
    }

    @PutMapping("/{userId}/cartItems")
    @Operation(summary = "Add shoe to user's cart")
    @PreAuthorize("@customSecurityExpression.canAccessUser(#userId)")
    public CartItemDto create(@PathVariable Long userId,
                              @Validated(OnCreate.class) @RequestBody CartItemDto dto) {
        CartItem cartItem = cartItemMapper.toEntity(dto);
        CartItem createdCartItem = cartItemService.create(cartItem, userId);
        return cartItemMapper.toDto(createdCartItem);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete cart item by id")
    @PreAuthorize("@customSecurityExpression.canAccessCartItem(#id)")
    public void deleteById(@PathVariable Long id) {
        cartItemService.delete(id);
    }

}