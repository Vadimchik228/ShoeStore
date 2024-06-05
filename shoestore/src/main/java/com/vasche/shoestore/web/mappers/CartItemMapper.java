package com.vasche.shoestore.web.mappers;

import com.vasche.shoestore.domain.cartItem.CartItem;
import com.vasche.shoestore.web.dto.cartItem.CartItemDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
// componentModel = "spring": Эта опция сообщает MapStruct, что маппер будет использоваться в контексте Spring.
// MapStruct автоматически интегрируется со Spring и внедряет бины мапперов в контекст приложения
public interface CartItemMapper {

    CartItemDto toDto(CartItem cartItem);

    List<CartItemDto> toDto(List<CartItem> cartItems);

    CartItem toEntity(CartItemDto dto);

}
