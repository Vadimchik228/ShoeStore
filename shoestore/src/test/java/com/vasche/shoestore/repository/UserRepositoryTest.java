package com.vasche.shoestore.repository;

import com.vasche.shoestore.domain.cartItem.CartItem;
import com.vasche.shoestore.domain.order.Order;
import com.vasche.shoestore.domain.orderItem.OrderItem;
import com.vasche.shoestore.domain.orderItem.Status;
import com.vasche.shoestore.domain.shoe.Shoe;
import com.vasche.shoestore.domain.user.Role;
import com.vasche.shoestore.domain.user.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class UserRepositoryTest extends RepositoryTestBase {


    private static Stream<Arguments> getUserLists() {
        User user1 = User.builder()
                .name("Vadim1")
                .username("schebet1@gmail.com")
                .password("12345")
                .roles(Set.of(Role.ROLE_USER))
                .build();

        User user2 = User.builder()
                .name("Vadim2")
                .username("schebet2@gmail.com")
                .password("12345")
                .roles(Set.of(Role.ROLE_USER))
                .build();

        User user3 = User.builder()
                .name("Vadim3")
                .username("schebet3@gmail.com")
                .password("12345")
                .roles(Set.of(Role.ROLE_ADMIN))
                .build();

        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);

        List<User> admins = new ArrayList<>();
        admins.add(user3);

        return Stream.of(
                Arguments.of(users, Role.ROLE_USER),
                Arguments.of(admins, Role.ROLE_ADMIN)
        );
    }

    @Test
    void findByUsernameTest() {
        User user = getUser();
        userRepository.save(user);

        User actualUser = userRepository.findByUsername(user.getUsername()).orElse(null);

        assertThat(actualUser).isEqualTo(user);
    }

    @Test
    void findByUnexistingUsernameTest() {
        User actualUser = userRepository.findByUsername("").orElse(null);

        assertThat(actualUser).isNull();
    }

    @ParameterizedTest
    @MethodSource("getUserLists")
    void findAllByRoleTest(List<User> users, Role role) {
        userRepository.saveAll(users);

        List<User> actualUsers = userRepository.findAll()
                .stream()
                .filter(user -> user.getRoles().equals(Set.of(role)))
                .collect(Collectors.toList());

        assertThat(actualUsers).isEqualTo(users);
    }


    @Test
    void isCartItemOwnerTest() {
        Shoe shoe = getShoe();
        shoeRepository.save(shoe);

        CartItem cartItem = CartItem.builder()
                .shoe(shoe)
                .quantity(1)
                .build();
        cartItemRepository.save(cartItem);

        User user = getUser();
        user.setCartItems(List.of(cartItem));
        userRepository.save(user);

        assertThat(userRepository.isCartItemOwner(user.getId(), cartItem.getId())).isTrue();
    }


    @Test
    void isOrderItemOwnerTest() {
        User user = getUser();
        Order order = getOrder();
        Shoe shoe = getShoe();
        userRepository.save(user);
        shoeRepository.save(shoe);

        order.setUser(user);
        orderRepository.save(order);

        OrderItem orderItem = OrderItem.builder()
                .status(Status.IN_ASSEMBLY)
                .quantity(2)
                .shoe(shoe)
                .order(order)
                .build();
        orderItemRepository.save(orderItem);

        assertThat(userRepository.isOrderItemOwner(user.getId(), orderItem.getId())).isTrue();
    }


    @Test
    void isOrderOwnerTest() {
        User user = getUser();
        Order order = getOrder();
        Shoe shoe = getShoe();
        userRepository.save(user);
        shoeRepository.save(shoe);

        OrderItem orderItem = OrderItem.builder()
                .status(Status.IN_ASSEMBLY)
                .quantity(2)
                .shoe(shoe)
                .build();

        order.setOrderItems(List.of(orderItem));
        order.setUser(user);

        orderItem.setOrder(order);
        orderRepository.save(order);

        assertThat(userRepository.isOrderOwner(user.getId(), order.getId())).isTrue();
    }

}
