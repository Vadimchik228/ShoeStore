package com.vasche.shoestore.web.mappers;

import com.vasche.shoestore.domain.orderItem.OrderItem;
import com.vasche.shoestore.web.dto.orderItem.OrderItemDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderItemMapper extends Mappable<OrderItem, OrderItemDto> {
}
