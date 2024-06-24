package com.vasche.shoestore.repository;

import com.vasche.shoestore.domain.order.Order;
import com.vasche.shoestore.domain.user.User;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


public class OrderRepositoryTest extends RepositoryTestBase {

    @Test
    void findAllByUserIdTest() {
        User user = getUser();
        userRepository.save(user);

        Order order1 = getOrder(user);
        Order order2 = getOrder(user);
        orderRepository.saveAll(List.of(order1, order2));

        List<Order> actualOrders = orderRepository.findAllByUserId(user.getId());

        assertThat(actualOrders).containsExactlyInAnyOrder(order1, order2);
    }

}
