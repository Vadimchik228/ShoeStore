package com.vasche.shoestore.repository;

import com.vasche.shoestore.domain.order.Order;
import com.vasche.shoestore.domain.orderItem.OrderItem;
import com.vasche.shoestore.domain.shoe.Shoe;
import com.vasche.shoestore.domain.user.User;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class OrderItemRepositoryTest extends RepositoryTestBase {

    @Test
    void findAllByOrderIdTest() {
        User user = getUser();
        Order order = getOrder();
        Shoe shoe1 = getShoe("Title 1");
        Shoe shoe2 = getShoe("Title 2");

        userRepository.save(user);
        shoeRepository.save(shoe1);
        shoeRepository.save(shoe2);

        order.setUser(user);
        orderRepository.save(order);

        OrderItem orderItem1 = getOrderItem(order, shoe1);

        OrderItem orderItem2 = getOrderItem(order, shoe2);

        orderItemRepository.save(orderItem1);
        orderItemRepository.save(orderItem2);
        var resultList = List.of(orderItem1, orderItem2);

        assertThat(orderItemRepository.findAllByOrderId(order.getId())).isEqualTo(resultList);
    }

    @Test
    void findAllByUserIdTest() {
        User user = getUser();
        Order order = getOrder();
        Shoe shoe1 = getShoe("Title 1");
        Shoe shoe2 = getShoe("Title 2");

        userRepository.save(user);
        shoeRepository.save(shoe1);
        shoeRepository.save(shoe2);

        order.setUser(user);
        orderRepository.save(order);

        OrderItem orderItem1 = getOrderItem(order, shoe1);

        OrderItem orderItem2 = getOrderItem(order, shoe2);

        orderItemRepository.save(orderItem1);
        orderItemRepository.save(orderItem2);
        var resultList = List.of(orderItem1, orderItem2);

        assertThat(orderItemRepository.findAllByUserId(user.getId())).isEqualTo(resultList);
    }

}
