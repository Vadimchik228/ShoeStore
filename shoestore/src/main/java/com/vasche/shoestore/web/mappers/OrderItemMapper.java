package com.vasche.shoestore.web.mappers;

import com.vasche.shoestore.domain.orderItem.OrderItem;
import com.vasche.shoestore.web.dto.orderItem.OrderItemDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {

    OrderItemDto toDto(OrderItem orderItem);

    List<OrderItemDto> toDto(List<OrderItem> orderItems);

    OrderItem toEntity(OrderItemDto dto);

}
