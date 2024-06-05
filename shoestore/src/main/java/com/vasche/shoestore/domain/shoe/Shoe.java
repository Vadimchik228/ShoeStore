package com.vasche.shoestore.domain.shoe;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Set;

@Data
public class Shoe {

    private Long id;
    private String title;
    private String description;
    private BigDecimal price;
    private Sex sex;
    private ShoeModel shoeModel;
    private Season season;
    private Integer size;

}
