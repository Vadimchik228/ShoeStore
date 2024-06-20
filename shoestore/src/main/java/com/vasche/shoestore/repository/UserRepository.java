package com.vasche.shoestore.repository;

import com.vasche.shoestore.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(@Param("username") String username);

    @Query(value = """
            SELECT EXISTS(
                SELECT 1
                FROM shoestore.carts
                WHERE user_id = :userId
                  AND cart_item_id = :cartItemId
            )
            """, nativeQuery = true)
    boolean isCartItemOwner(@Param("userId") Long userId, @Param("cartItemId") Long cartItemId);

    @Query(value = """
            SELECT EXISTS(
                SELECT 1
                FROM shoestore.order_items ot
                JOIN shoestore.orders o on o.id = ot.order_id
                WHERE o.user_id = :userId
                  AND ot.id = :orderItemId
            )
            """, nativeQuery = true)
    boolean isOrderItemOwner(@Param("userId") Long userId, @Param("orderItemId") Long orderItemId);

    @Query(value = """
            SELECT EXISTS(
                SELECT 1
                FROM shoestore.orders o
                WHERE o.user_id = :userId
                  AND o.id = :orderId
            )
            """, nativeQuery = true)
    boolean isOrderOwner(@Param("userId") Long userId, @Param("orderId") Long orderId);

}
