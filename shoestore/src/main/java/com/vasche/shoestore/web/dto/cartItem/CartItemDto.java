package com.vasche.shoestore.web.dto.cartItem;

import com.vasche.shoestore.web.dto.validation.OnCreate;
import com.vasche.shoestore.web.dto.validation.OnUpdate;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "CartItem DTO")
public class CartItemDto {

    @NotNull(
            message = "Id must be not null.",
            groups = OnUpdate.class
    )
    private Long id;

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
