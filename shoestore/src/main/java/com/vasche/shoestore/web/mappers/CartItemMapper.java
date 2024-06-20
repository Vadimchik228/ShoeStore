package com.vasche.shoestore.web.mappers;

import com.vasche.shoestore.domain.cartItem.CartItem;
import com.vasche.shoestore.web.dto.cartItem.CartItemDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CartItemMapper extends Mappable<CartItem, CartItemDto> {
}
