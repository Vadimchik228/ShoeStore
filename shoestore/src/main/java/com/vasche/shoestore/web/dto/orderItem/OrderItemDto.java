package com.vasche.shoestore.web.dto.orderItem;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.vasche.shoestore.domain.orderItem.Status;
import com.vasche.shoestore.web.dto.validation.OnCreate;
import com.vasche.shoestore.web.dto.validation.OnUpdate;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@Schema(description = "OrderItem DTO")
public class OrderItemDto {

    @NotNull(
            message = "Id must be not null.",
            groups = OnUpdate.class
    )
    private Long id;

    @NotNull(
            message = "OrderId must be not null.",
            groups = {OnCreate.class, OnUpdate.class}
    )
    private Long orderId;

    private Status status;

    @DateTimeFormat(
            iso = DateTimeFormat.ISO.TIME
    )
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm"
    )
    private LocalDateTime arrivalTime;

    @NotNull(
            message = "ShoeId must be not null.",
            groups = {OnCreate.class, OnUpdate.class}
    )
    private Long shoeId;

    @NotNull(
            message = "Quantity must be not null.",
            groups = {OnCreate.class, OnUpdate.class}
    )
    @Min(
            value = 1,
            message = "Quantity must be at least 1."
    )
    @Max(
            value = 10,
            message = "Quantity must be at most 10."
    )
    private Integer quantity;

}
