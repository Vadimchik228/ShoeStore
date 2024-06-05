package com.vasche.shoestore.web.dto.shoe;

import com.vasche.shoestore.domain.shoe.Season;
import com.vasche.shoestore.domain.shoe.Sex;
import com.vasche.shoestore.domain.shoe.ShoeModel;
import com.vasche.shoestore.web.dto.validation.OnCreate;
import com.vasche.shoestore.web.dto.validation.OnUpdate;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Schema(description = "Shoe DTO")
public class ShoeDto {

    @NotNull(
            message = "Id must be not null.",
            groups = OnUpdate.class
    )
    private Long id;

    @NotNull(
            message = "Title must be not null.",
            groups = {OnCreate.class, OnUpdate.class}
    )
    private String title;

    private String description;

    @NotNull(
            message = "Price must be not null.",
            groups = {OnCreate.class, OnUpdate.class}
    )
    @Min(
            value = 0,
            message = "Price must be at least 0."
    )
    private BigDecimal price;

    private Sex sex;

    private ShoeModel shoeModel;

    private Season season;

    @NotNull(
            message = "Size must be not null.",
            groups = {OnCreate.class, OnUpdate.class}
    )
    @Min(
            value = 16,
            message = "Size must be at least 1."
    )
    @Max(
            value = 59,
            message = "Size must be at most 10."
    )
    private Integer size;

}
