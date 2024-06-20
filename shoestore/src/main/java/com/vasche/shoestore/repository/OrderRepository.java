package com.vasche.shoestore.repository;

import com.vasche.shoestore.domain.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query(value = """
            SELECT o.id,
                   order_time,
                   first_name,
                   last_name,
                   city,
                   address,
                   email,
                   phone_number,
                   user_id
            FROM shoestore.orders o
            JOIN shoestore.users u on u.id = o.user_id
            WHERE u.id = :userId
            """, nativeQuery = true)
    List<Order> findAllByUserId(@Param("userId") Long userId);

}
