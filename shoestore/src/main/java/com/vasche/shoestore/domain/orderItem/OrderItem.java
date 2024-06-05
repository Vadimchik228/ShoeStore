package com.vasche.shoestore.domain.orderItem;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderItem {

    private Long id;
    private Long orderId;
    private Status status;
    private LocalDateTime arrivalTime;
    private Long shoeId;
    private Integer quantity;

}
