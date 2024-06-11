package com.vasche.shoestore.service.impl;

import com.vasche.shoestore.domain.cartItem.CartItem;
import com.vasche.shoestore.domain.exception.ResourceNotFoundException;
import com.vasche.shoestore.domain.orderItem.OrderItem;
import com.vasche.shoestore.domain.orderItem.Status;
import com.vasche.shoestore.repository.OrderItemRepository;
import com.vasche.shoestore.service.OrderItemService;
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
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepository orderItemRepository;


    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "OrderItemService::getById", key = "#id")
    public OrderItem getById(Long id) {
        return orderItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order item not found."));
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderItem> getAllByOrderId(Long orderId) {
        return orderItemRepository.findAllByOrderId(orderId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderItem> getAllByUserId(Long userId) {
        return orderItemRepository.findAllByUserId(userId);
    }

    @Override
    @Transactional
    @Caching(put = @CachePut(value = "OrderItemService::getById", key = "orderItem.id"))
    public OrderItem update(OrderItem orderItem) {
        orderItemRepository.update(orderItem);
        return orderItem;
    }

    @Override
    @Transactional
    public OrderItem create(CartItem cartItem, Long orderId) {
        OrderItem orderItem = new OrderItem();
        orderItem.setOrderId(orderId);
        orderItem.setShoeId(cartItem.getShoeId());
        orderItem.setQuantity(cartItem.getQuantity());
        orderItem.setStatus(Status.IN_ASSEMBLY);
        orderItemRepository.create(orderItem);
        return orderItem;
    }

    @Override
    @Transactional
    @CacheEvict(value = "OrderItemService::getById", key = "#id")
    public void delete(Long id) {
        orderItemRepository.delete(id);
    }
}
