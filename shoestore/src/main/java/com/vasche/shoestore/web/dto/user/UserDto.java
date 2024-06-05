package com.vasche.shoestore.web.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vasche.shoestore.web.dto.validation.OnCreate;
import com.vasche.shoestore.web.dto.validation.OnUpdate;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "User DTO")
public class UserDto {

    @NotNull(
            message = "Id must be not null.",
            groups = OnUpdate.class
    )
    private Long id;

    @NotNull(
            message = "Name must be not null.",
            groups = {OnCreate.class, OnUpdate.class}
    )
    private String name;

    @NotNull(
            message = "Username must be not null.",
            groups = {OnCreate.class, OnUpdate.class}
    )
    private String username;

    @JsonProperty(
            access = JsonProperty.Access.WRITE_ONLY
    )
    @NotNull(
            message = "Password must be not null.",
            groups = {OnCreate.class, OnUpdate.class}
    )
    private String password;

    @JsonProperty(
            access = JsonProperty.Access.WRITE_ONLY
    )
    @NotNull(
            message = "Password confirmation must be not null.",
            groups = {OnCreate.class}
    )
    private String passwordConfirmation;

}
