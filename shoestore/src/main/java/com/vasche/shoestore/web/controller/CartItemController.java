package com.vasche.shoestore.web.controller;

import com.vasche.shoestore.service.CartItemService;
import com.vasche.shoestore.web.dto.cartItem.CartItemDto;
import com.vasche.shoestore.web.dto.validation.OnCreate;
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
@Tag(name = "Cart Item Controller", description = "Cart Item API")
public class CartItemController {

    private final CartItemService cartItemService;

    @GetMapping("/{id}")
    @Operation(summary = "Get cart item by id")
    @PreAuthorize("@customSecurityExpression.canAccessCartItem(#id)")
    public CartItemDto getById(@PathVariable Long id) {
        return cartItemService.getById(id);
    }

    @GetMapping("/{userId}/cartItems")
    @Operation(summary = "Get all user's cart items")
    @PreAuthorize("@customSecurityExpression.canAccessUser(#userId)")
    public List<CartItemDto> getCartItemsByUserId(@PathVariable Long userId) {
        return cartItemService.getAllByUserId(userId);
    }

    @PutMapping("/{userId}/cartItems")
    @Operation(summary = "Add shoe to user's cart")
    @PreAuthorize("@customSecurityExpression.canAccessUser(#userId)")
    public CartItemDto create(@PathVariable Long userId,
                              @Validated(OnCreate.class) @RequestBody CartItemDto dto) {
        return cartItemService.create(dto, userId);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete cart item by id")
    @PreAuthorize("@customSecurityExpression.canAccessCartItem(#id)")
    public void deleteById(@PathVariable Long id) {
        cartItemService.delete(id);
    }

}