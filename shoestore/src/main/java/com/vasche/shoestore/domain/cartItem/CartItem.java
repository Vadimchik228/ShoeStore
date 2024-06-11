package com.vasche.shoestore.domain.cartItem;

import lombok.Data;

import java.io.Serializable;

@Data
public class CartItem implements Serializable {

    private Long id;
    private Long shoeId;
    private Integer quantity;

}
