package com.vasche.shoestore.web.mappers;

import com.vasche.shoestore.domain.order.Order;
import com.vasche.shoestore.web.dto.order.OrderDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    OrderDto toDto(Order order);

    List<OrderDto> toDto(List<Order> orders);

    Order toEntity(OrderDto dto);

}
