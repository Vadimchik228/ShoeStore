package com.vasche.shoestore.web.mappers;

import com.vasche.shoestore.domain.shoe.Shoe;
import com.vasche.shoestore.web.dto.shoe.ShoeDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ShoeMapper {

    ShoeMapper INSTANCE = Mappers.getMapper(ShoeMapper.class);

    @Mapping(target = "images", ignore = true)
    ShoeDto toDto(Shoe shoe);

    @Mapping(target = "id", ignore = true)
    Shoe toEntity(ShoeDto shoeDto);
}
