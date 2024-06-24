package com.vasche.shoestore.service.impl;

import com.vasche.shoestore.domain.exception.ResourceNotFoundException;
import com.vasche.shoestore.domain.mail.MailType;
import com.vasche.shoestore.domain.order.Order;
import com.vasche.shoestore.domain.orderItem.OrderItem;
import com.vasche.shoestore.domain.orderItem.Status;
import com.vasche.shoestore.domain.shoe.Shoe;
import com.vasche.shoestore.repository.OrderItemRepository;
import com.vasche.shoestore.repository.OrderRepository;
import com.vasche.shoestore.repository.ShoeRepository;
import com.vasche.shoestore.service.MailService;
import com.vasche.shoestore.service.OrderItemService;
import com.vasche.shoestore.web.dto.cartItem.CartItemDto;
import com.vasche.shoestore.web.dto.orderItem.OrderItemDto;
import com.vasche.shoestore.web.mappers.OrderItemMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;
    private final ShoeRepository shoeRepository;
    private final OrderItemMapper orderItemMapper;
    private final MailService mailService;

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "OrderItemService::getById", key = "#id")
    public OrderItemDto getById(Long id) {
        Optional<OrderItem> orderItem = orderItemRepository.findById(id);
        if (orderItem.isPresent()) {
            var dto = orderItemMapper.toDto(orderItem.get());
            dto.setOrderId(orderItem.get().getOrder().getId());
            dto.setShoeId(orderItem.get().getShoe().getId());
            return dto;
        } else {
            throw new ResourceNotFoundException("Order item not found.");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderItemDto> getAllByOrderId(Long orderId) {
        return orderItemRepository.findAllByOrderId(orderId)
                .stream()
                .map(orderItem -> {
                    var dto = orderItemMapper.toDto(orderItem);
                    dto.setOrderId(orderId);
                    dto.setShoeId(orderItem.getShoe().getId());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderItemDto> getAllByUserId(Long userId) {
        return orderItemRepository.findAllByUserId(userId)
                .stream()
                .map(orderItem -> {
                    var dto = orderItemMapper.toDto(orderItem);
                    dto.setOrderId(orderItem.getOrder().getId());
                    dto.setShoeId(orderItem.getShoe().getId());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    @CachePut(value = "OrderItemService::getById", key = "#orderItemDto.id")
    public OrderItemDto update(OrderItemDto orderItemDto) {
        Optional<Order> order = orderRepository.findById(orderItemDto.getOrderId());
        if (order.isEmpty()) {
            throw new IllegalStateException("There is no such order in the database.");
        }
        Optional<Shoe> shoe = shoeRepository.findById(orderItemDto.getShoeId());
        if (shoe.isEmpty()) {
            throw new IllegalStateException("There is no such shoe in the database.");
        }
        var orderItem = orderItemMapper.toEntity(orderItemDto);
        orderItem.setOrder(order.get());
        orderItem.setShoe(shoe.get());
        orderItemRepository.save(orderItem);

        if (orderItem.getStatus() == Status.DELIVERED) {
            var properties = new Properties();
            properties.put("city", orderItem.getOrder().getCity());
            properties.put("address", orderItem.getOrder().getAddress());
            properties.put("title", orderItem.getShoe().getTitle());
//            properties.put("quantity", orderItem.getQuantity());
            mailService.sendEmail(orderItem.getOrder().getUser(), MailType.NOTIFICATION, properties);
        }

        return orderItemDto;
    }

    @Override
    @Transactional
    @CachePut(value = "OrderItemService::getById", key = "#result.id", condition = "#result != null")
    public OrderItemDto create(CartItemDto cartItemDto, Long orderId) {

        Optional<Order> order = orderRepository.findById(orderId);
        if (order.isEmpty()) {
            throw new IllegalStateException("There is no such order in the database.");
        }

        Optional<Shoe> shoe = shoeRepository.findById(cartItemDto.getShoeId());
        if (shoe.isEmpty()) {
            throw new IllegalStateException("There is no such shoe in the database.");
        }

        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(order.get());
        orderItem.setShoe(shoe.get());
        orderItem.setQuantity(cartItemDto.getQuantity());
        orderItem.setStatus(Status.IN_ASSEMBLY);

        var dto = orderItemMapper.toDto(orderItemRepository.save(orderItem));

        dto.setId(orderItem.getId());
        dto.setShoeId(shoe.get().getId());
        dto.setOrderId(order.get().getId());
        return dto;
    }

    @Override
    @Transactional
    @CacheEvict(value = "OrderItemService::getById", key = "#id")
    public void delete(Long id) {
        orderItemRepository.deleteById(id);
    }
}
