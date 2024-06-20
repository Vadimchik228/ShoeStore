package com.vasche.shoestore.web.mappers;

import com.vasche.shoestore.domain.shoe.Shoe;
import com.vasche.shoestore.web.dto.shoe.ShoeDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ShoeMapper extends Mappable<Shoe, ShoeDto> {
}
