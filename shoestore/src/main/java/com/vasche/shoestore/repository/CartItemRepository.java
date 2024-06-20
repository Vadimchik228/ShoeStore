package com.vasche.shoestore.repository;

import com.vasche.shoestore.domain.cartItem.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    @Query(value = """
            SELECT ci.id,
                   shoe_id,
                   quantity
            FROM shoestore.carts c
            JOIN shoestore.cart_items ci on ci.id = c.cart_item_id
            WHERE c.user_id = :userId
            """, nativeQuery = true)
    List<CartItem> findAllByUserId(@Param("userId") Long userId);

}
