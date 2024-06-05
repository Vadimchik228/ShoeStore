package com.vasche.shoestore.service.impl;

import com.vasche.shoestore.domain.cartItem.CartItem;
import com.vasche.shoestore.domain.exception.ResourceNotFoundException;
import com.vasche.shoestore.domain.order.Order;
import com.vasche.shoestore.domain.orderItem.OrderItem;
import com.vasche.shoestore.domain.orderItem.Status;
import com.vasche.shoestore.repository.CartItemRepository;
import com.vasche.shoestore.repository.OrderItemRepository;
import com.vasche.shoestore.repository.OrderRepository;
import com.vasche.shoestore.repository.UserRepository;
import com.vasche.shoestore.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CartItemRepository cartItemRepository;
    private final OrderItemRepository orderItemRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public Order getById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found."));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Order> getAll() {
        return orderRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Order> getAllByUserId(Long userId) {
        return orderRepository.findAllByUserId(userId);
    }

    @Override
    @Transactional
    public Order update(Order order) {
        if (userRepository.findById(order.getUserId()).isEmpty()) {
            throw new IllegalStateException("There is no such user in the database.");
        }
        orderRepository.update(order);
        return order;
    }

    @Override
    @Transactional
    public Order create(Order order, Long userId) {
        if (userRepository.findById(order.getUserId()).isEmpty()) {
            throw new IllegalStateException("There is no such user in the database.");
        }
        List<CartItem> cartItems = cartItemRepository.findAllByUserId(userId);
        if (!cartItems.isEmpty()) {
            order.setOrderTime(LocalDateTime.now());
            orderRepository.create(order);
            for (CartItem item : cartItems) {
                OrderItem orderItem = new OrderItem();
                orderItem.setOrderId(order.getId());
                orderItem.setShoeId(item.getShoeId());
                orderItem.setQuantity(item.getQuantity());
                orderItem.setStatus(Status.IN_ASSEMBLY);
                orderItemRepository.create(orderItem);
                cartItemRepository.delete(item.getId());
            }
            return order;
        }
        return null;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        orderRepository.delete(id);
    }
}
