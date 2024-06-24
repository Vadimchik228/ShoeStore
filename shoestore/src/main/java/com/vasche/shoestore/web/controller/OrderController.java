package com.vasche.shoestore.web.controller;

import com.vasche.shoestore.domain.order.Order;
import com.vasche.shoestore.service.OrderService;
import com.vasche.shoestore.web.dto.order.OrderDto;
import com.vasche.shoestore.web.dto.validation.OnCreate;
import com.vasche.shoestore.web.mappers.OrderMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@Validated
@Tag(name = "Order Controller", description = "Order API")
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/{id}")
    @Operation(summary = "Get order by id")
    @PreAuthorize("@customSecurityExpression.canAccessOrder(#id)")
    public OrderDto getById(@PathVariable Long id) {
        return orderService.getById(id);
    }

    @GetMapping
    @Operation(summary = "Get all orders")
    @PreAuthorize("@customSecurityExpression.hasAdminRights()")
    public List<OrderDto> getAllOrders() {
        return orderService.getAll();
    }

    @GetMapping("/{userId}/orders")
    @Operation(summary = "Get all user's orders")
    @PreAuthorize("@customSecurityExpression.canAccessUser(#userId)")
    public List<OrderDto> getOrdersByUserId(@PathVariable Long userId) {
        return orderService.getAllByUserId(userId);
    }

    @PutMapping
    @Operation(summary = "Update order")
    @PreAuthorize("@customSecurityExpression.hasAdminRights()")
    public OrderDto update(@Validated(OnCreate.class) @RequestBody OrderDto dto) {
        return orderService.update(dto);
    }

    @PostMapping("/{userId}/checkout")
    @Operation(summary = "Create order")
    @PreAuthorize("@customSecurityExpression.canAccessUser(#userId)")
    public OrderDto create(@PathVariable Long userId,
                           @Validated(OnCreate.class) @RequestBody OrderDto dto) {
        return orderService.create(dto, userId);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete order by id")
    @PreAuthorize("@customSecurityExpression.hasAdminRights()")
    public void deleteById(@PathVariable Long id) {
        orderService.delete(id);
    }

}
