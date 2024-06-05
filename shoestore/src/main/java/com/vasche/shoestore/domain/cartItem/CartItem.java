package com.vasche.shoestore.domain.cartItem;

import lombok.Data;

@Data
public class CartItem {

    private Long id;
    private Long shoeId;
    private Integer quantity;

}
