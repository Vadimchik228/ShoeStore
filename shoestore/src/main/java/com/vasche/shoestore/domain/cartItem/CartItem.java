package com.vasche.shoestore.domain.cartItem;

import com.vasche.shoestore.domain.shoe.Shoe;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "cart_items")
public class CartItem implements Serializable {

    @Serial
    private static final long serialVersionUID = 5L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shoe_id")
    private Shoe shoe;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;
}
