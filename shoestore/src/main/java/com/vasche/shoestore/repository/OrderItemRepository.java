package com.vasche.shoestore.repository;

import com.vasche.shoestore.domain.orderItem.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    @Query(value = """
            SELECT oi.id,
                   order_id,
                   arrival_time,
                   shoe_id,
                   quantity,
                   status
            FROM shoestore.orders o
            JOIN shoestore.order_items oi on o.id = oi.order_id
            WHERE o.id = :orderId
            """, nativeQuery = true)
    List<OrderItem> findAllByOrderId(@Param("orderId") Long orderId);

    @Query(value = """
            SELECT oi.id,
                   order_id,
                   arrival_time,
                   shoe_id,
                   quantity,
                   status
            FROM shoestore.orders o
            JOIN shoestore.order_items oi on o.id = oi.order_id
            WHERE o.user_id = :userId
            """, nativeQuery = true)
    List<OrderItem> findAllByUserId(@Param("userId") Long userId);

}
