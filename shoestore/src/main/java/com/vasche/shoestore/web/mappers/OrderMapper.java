package com.vasche.shoestore.web.mappers;

import com.vasche.shoestore.domain.order.Order;
import com.vasche.shoestore.web.dto.order.OrderDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper extends Mappable<Order, OrderDto> {
}
