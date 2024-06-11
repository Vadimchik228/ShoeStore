package com.vasche.shoestore.domain.orderItem;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class OrderItem implements Serializable {

    private Long id;
    private Long orderId;
    private Status status;
    private LocalDateTime arrivalTime;
    private Long shoeId;
    private Integer quantity;

}
