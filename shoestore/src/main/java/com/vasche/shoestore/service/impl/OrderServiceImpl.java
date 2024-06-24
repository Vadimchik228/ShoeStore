package com.vasche.shoestore.service.impl;

import com.vasche.shoestore.domain.cartItem.CartItem;
import com.vasche.shoestore.domain.exception.ResourceNotFoundException;
import com.vasche.shoestore.domain.order.Order;
import com.vasche.shoestore.domain.orderItem.OrderItem;
import com.vasche.shoestore.domain.orderItem.Status;
import com.vasche.shoestore.domain.user.User;
import com.vasche.shoestore.repository.CartItemRepository;
import com.vasche.shoestore.repository.OrderItemRepository;
import com.vasche.shoestore.repository.OrderRepository;
import com.vasche.shoestore.repository.UserRepository;
import com.vasche.shoestore.service.OrderService;
import com.vasche.shoestore.web.dto.order.OrderDto;
import com.vasche.shoestore.web.mappers.OrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CartItemRepository cartItemRepository;
    private final OrderItemRepository orderItemRepository;
    private final UserRepository userRepository;
    private final OrderMapper orderMapper;

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "OrderService::getById", key = "#id")
    public OrderDto getById(Long id) {
        Optional<Order> order = orderRepository.findById(id);
        if (order.isPresent()) {
            var dto = orderMapper.toDto(order.get());
            dto.setUserId(order.get().getUser().getId());
            return dto;
        } else {
            throw new ResourceNotFoundException("Order not found.");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderDto> getAll() {
        return orderRepository.findAll()
                .stream()
                .map(order -> {
                    var dto = orderMapper.toDto(order);
                    dto.setUserId(order.getUser().getId());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderDto> getAllByUserId(Long userId) {
        return orderRepository.findAllByUserId(userId)
                .stream()
                .map(order -> {
                    var dto = orderMapper.toDto(order);
                    dto.setUserId(userId);
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    @CachePut(value = "OrderService::getById", key = "#result.id", condition = "#result != null")
    public OrderDto update(OrderDto orderDto) {
        Optional<User> user = userRepository.findById(orderDto.getUserId());
        if (user.isEmpty()) {
            throw new IllegalStateException("There is no such user in the database.");
        }
        var shoe = orderMapper.toEntity(orderDto);
        shoe.setUser(user.get());
        orderRepository.save(shoe);
        return orderDto;
    }

    @Override
    @Transactional
    @CachePut(value = "OrderService::getById", key = "#result.id", condition = "#result != null")
    public OrderDto create(OrderDto orderDto, Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new IllegalStateException("There is no such user in the database.");
        }
        List<CartItem> cartItems = cartItemRepository.findAllByUserId(userId);
        if (!cartItems.isEmpty()) {

            orderDto.setUserId(userId);
            orderDto.setOrderTime(LocalDateTime.now());

            Order order = orderMapper.toEntity(orderDto);
            order.setUser(user.get());
            orderRepository.save(order);

            orderDto.setId(order.getId());

            for (CartItem item : cartItems) {
                OrderItem orderItem = new OrderItem();
                orderItem.setOrder(order);
                orderItem.setShoe(item.getShoe());
                orderItem.setQuantity(item.getQuantity());
                orderItem.setStatus(Status.IN_ASSEMBLY);
                orderItemRepository.save(orderItem);

                cartItemRepository.deleteById(item.getId());
            }
            return orderDto;
        }
        return null;
    }

    @Override
    @Transactional
    @CacheEvict(value = "OrderService::getById", key = "#id")
    public void delete(Long id) {
        orderRepository.deleteById(id);
    }
}
