package com.vasche.shoestore.web.dto.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.vasche.shoestore.web.dto.validation.OnCreate;
import com.vasche.shoestore.web.dto.validation.OnUpdate;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Schema(description = "Order DTO")
public class OrderDto implements Serializable {

    @NotNull(
            message = "Id must be not null.",
            groups = OnUpdate.class
    )
    private Long id;

    @DateTimeFormat( // Для форматирования даты и времени, когда данные получаются от клиента из JSON-запроса.
            iso = DateTimeFormat.ISO.TIME
    )
    @JsonFormat( // Для форматирования даты и времени, когда данные отправляются клиенту в JSON-ответе
            pattern = "yyyy-MM-dd HH:mm"
    )
    private LocalDateTime orderTime;

    @NotNull(
            message = "First name must be not null.",
            groups = {OnCreate.class, OnUpdate.class}
    )
    private String firstName;

    @NotNull(
            message = "Last name must be not null.",
            groups = {OnCreate.class, OnUpdate.class}
    )
    private String lastName;

    @NotNull(
            message = "City must be not null.",
            groups = {OnCreate.class, OnUpdate.class}
    )
    private String city;

    @NotNull(
            message = "Address must be not null.",
            groups = {OnCreate.class, OnUpdate.class}
    )
    private String address;

    @NotNull(
            message = "Email must be not null.",
            groups = {OnCreate.class, OnUpdate.class}
    )
    private String email;

    @NotNull(
            message = "Phone number must be not null.",
            groups = {OnCreate.class, OnUpdate.class}
    )
    private String phoneNumber;

    @NotNull(
            message = "UserId must be not null.",
            groups = {OnCreate.class, OnUpdate.class}
    )
    private Long userId;

}
