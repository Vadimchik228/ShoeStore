package com.vasche.shoestore.web.controller;

import com.vasche.shoestore.domain.user.User;
import com.vasche.shoestore.service.UserService;
import com.vasche.shoestore.web.dto.user.UserDto;
import com.vasche.shoestore.web.dto.validation.OnUpdate;
import com.vasche.shoestore.web.mappers.UserMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Validated
@Tag(name = "User Controller", description = "User API")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping("/{id}")
    @Operation(summary = "Get userDto by id")
    @PreAuthorize("@customSecurityExpression.canAccessUser(#id)")
    public UserDto getById(@PathVariable Long id) {
        User user = userService.getById(id);
        return userMapper.toDto(user);
    }

    @PutMapping
    @Operation(summary = "Update user")
    @PreAuthorize("@customSecurityExpression.canAccessUser(#dto.id)")
    public UserDto update(@Validated(OnUpdate.class) @RequestBody UserDto dto) {
        User user = userMapper.toEntity(dto);
        User updatedUser = userService.update(user);
        return userMapper.toDto(updatedUser);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete user by id")
    @PreAuthorize("@customSecurityExpression.hasAdminRights()")
    public void deleteById(@PathVariable Long id) {
        userService.delete(id);
    }
}
