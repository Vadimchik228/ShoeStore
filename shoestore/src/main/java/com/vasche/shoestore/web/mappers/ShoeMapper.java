package com.vasche.shoestore.web.mappers;

import com.vasche.shoestore.domain.shoe.Shoe;
import com.vasche.shoestore.web.dto.shoe.ShoeDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ShoeMapper {

    ShoeDto toDto(Shoe shoe);

    List<ShoeDto> toDto(List<Shoe> shoes);

    Shoe toEntity(ShoeDto dto);

}
