package com.vasche.shoestore.web.mappers;

import com.vasche.shoestore.domain.shoe.ShoeImage;
import com.vasche.shoestore.web.dto.shoe.ShoeImageDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ShoeImageMapper extends Mappable<ShoeImage, ShoeImageDto> {
}
