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
    private final OrderMapper orderMapper;

    @GetMapping("/{id}")
    @Operation(summary = "Get order by id")
    @PreAuthorize("@customSecurityExpression.canAccessOrder(#id)")
    public OrderDto getById(@PathVariable Long id) {
        Order order = orderService.getById(id);
        return orderMapper.toDto(order);
    }

    @GetMapping
    @Operation(summary = "Get all orders")
    @PreAuthorize("@customSecurityExpression.hasAdminRights()")
    public List<OrderDto> getAllOrders() {
        List<Order> orders = orderService.getAll();
        return orderMapper.toDto(orders);
    }

    @GetMapping("/{userId}/orders")
    @Operation(summary = "Get all user's orders")
    @PreAuthorize("@customSecurityExpression.canAccessUser(#userId)")
    public List<OrderDto> getOrdersByUserId(@PathVariable Long userId) {
        List<Order> orders = orderService.getAllByUserId(userId);
        return orderMapper.toDto(orders);
    }

    @PutMapping
    @Operation(summary = "Update order")
    @PreAuthorize("@customSecurityExpression.hasAdminRights()")
    public OrderDto update(@Validated(OnCreate.class) @RequestBody OrderDto dto) {
        Order order = orderMapper.toEntity(dto);
        Order updatedOrder = orderService.update(order);
        return orderMapper.toDto(updatedOrder);
    }

    @PostMapping("/{userId}/checkout")
    @Operation(summary = "Create order")
    @PreAuthorize("@customSecurityExpression.canAccessUser(#userId)")
    public OrderDto create(@PathVariable Long userId,
                           @Validated(OnCreate.class) @RequestBody OrderDto dto) {
        Order order = orderMapper.toEntity(dto);
        Order createdOrder = orderService.create(order, userId);
        return orderMapper.toDto(createdOrder);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete order by id")
    @PreAuthorize("@customSecurityExpression.hasAdminRights()")
    public void deleteById(@PathVariable Long id) {
        orderService.delete(id);
    }

}
