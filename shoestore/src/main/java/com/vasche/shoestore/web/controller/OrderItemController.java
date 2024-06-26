package com.vasche.shoestore.web.controller;

import com.vasche.shoestore.domain.orderItem.OrderItem;
import com.vasche.shoestore.service.OrderItemService;
import com.vasche.shoestore.web.dto.orderItem.OrderItemDto;
import com.vasche.shoestore.web.dto.validation.OnUpdate;
import com.vasche.shoestore.web.mappers.OrderItemMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orderItems")
@RequiredArgsConstructor
@Validated
@Tag(name = "Order Item Controller", description = "Order Item API")
public class OrderItemController {

    private final OrderItemService orderItemService;
    private final OrderItemMapper orderItemMapper;

    @GetMapping("/{id}")
    @Operation(summary = "Get order item by id")
    @PreAuthorize("@customSecurityExpression.canAccessOrderItem(#id)")
    public OrderItemDto getById(@PathVariable Long id) {
        return orderItemService.getById(id);
    }

    @GetMapping("/{userId}/orderItems")
    @Operation(summary = "Get all user's order items")
    @PreAuthorize("@customSecurityExpression.canAccessUser(#userId)")
    public List<OrderItemDto> getOrderItemsByUserId(@PathVariable Long userId) {
        return orderItemService.getAllByUserId(userId);
    }

    @GetMapping("/{orderId}/items")
    @Operation(summary = "Get all order items by orderId")
    @PreAuthorize("@customSecurityExpression.hasAdminRights()")
    public List<OrderItemDto> getOrderItemsByOrderId(@PathVariable Long orderId) {
        return orderItemService.getAllByOrderId(orderId);
    }

    @PutMapping
    @Operation(summary = "Update order item")
    @PreAuthorize("@customSecurityExpression.hasAdminRights()")
    public OrderItemDto update(@Validated(OnUpdate.class) @RequestBody OrderItemDto dto) {
        return orderItemService.update(dto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete order item by id")
    @PreAuthorize("@customSecurityExpression.hasAdminRights()")
    public void deleteById(@PathVariable Long id) {
        orderItemService.delete(id);
    }

}