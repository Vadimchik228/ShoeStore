package com.vasche.shoestore.domain.order;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class Order implements Serializable {

    private Long id;
    private LocalDateTime orderTime;
    private String firstName;
    private String lastName;
    private String city;
    private String address;
    private String email;
    private String phoneNumber;
    private Long userId;

}
