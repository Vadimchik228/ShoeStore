package com.vasche.shoestore.service.impl;

import com.vasche.shoestore.domain.cartItem.CartItem;
import com.vasche.shoestore.domain.exception.ResourceNotFoundException;
import com.vasche.shoestore.domain.order.Order;
import com.vasche.shoestore.domain.orderItem.OrderItem;
import com.vasche.shoestore.domain.orderItem.Status;
import com.vasche.shoestore.repository.CartItemRepository;
import com.vasche.shoestore.repository.OrderItemRepository;
import com.vasche.shoestore.repository.OrderRepository;
import com.vasche.shoestore.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
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

    @Override
    @Transactional(readOnly = true)
//    @Cacheable(value = "OrderService::getById", key = "#id")
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
//    @Caching(put = @CachePut(value = "OrderService::getById", key = "order.id"))
    public Order update(Order order) {
        orderRepository.save(order);
        return order;
    }

    @Override
    @Transactional
//    @Caching(cacheable = @Cacheable(value = "OrderService::getById", key = "order.id"))
    public Order create(Order order, Long userId) {
        List<CartItem> cartItems = cartItemRepository.findAllByUserId(userId);
        if (!cartItems.isEmpty()) {
            order.setOrderTime(LocalDateTime.now());
            orderRepository.save(order);
            for (CartItem item : cartItems) {
                OrderItem orderItem = new OrderItem();
                orderItem.setOrder(order);
                orderItem.setShoe(item.getShoe());
                orderItem.setQuantity(item.getQuantity());
                orderItem.setStatus(Status.IN_ASSEMBLY);
                orderItemRepository.save(orderItem);
                cartItemRepository.deleteById(item.getId());
            }
            return order;
        }
        return null;
    }

    @Override
    @Transactional
//    @CacheEvict(value = "OrderService::getById", key = "#id")
    public void delete(Long id) {
        orderRepository.deleteById(id);
    }
}
