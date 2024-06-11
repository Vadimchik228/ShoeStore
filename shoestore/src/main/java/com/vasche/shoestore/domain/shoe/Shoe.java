package com.vasche.shoestore.domain.shoe;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class Shoe implements Serializable {

    private Long id;
    private String title;
    private String description;
    private BigDecimal price;
    private Sex sex;
    private ShoeModel shoeModel;
    private Season season;
    private Integer size;

}
