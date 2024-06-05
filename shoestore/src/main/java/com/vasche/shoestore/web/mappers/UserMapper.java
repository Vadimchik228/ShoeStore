package com.vasche.shoestore.web.mappers;

import com.vasche.shoestore.domain.user.User;
import com.vasche.shoestore.web.dto.user.UserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toDto(User user);

    User toEntity(UserDto dto);

}
