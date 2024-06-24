package com.vasche.shoestore.repository;

import com.vasche.shoestore.domain.cartItem.CartItem;
import com.vasche.shoestore.domain.shoe.Shoe;
import com.vasche.shoestore.domain.user.User;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CartItemRepositoryTest extends RepositoryTestBase {


    @Test
    void findAllByUserIdTest() {
        Shoe shoe1 = getShoe("Title 1");
        Shoe shoe2 = getShoe("Title 2");

        shoeRepository.save(shoe1);
        shoeRepository.save(shoe2);

        CartItem cartItem1 = CartItem.builder()
                .shoe(shoe1)
                .quantity(1)
                .build();
        CartItem cartItem2 = CartItem.builder()
                .shoe(shoe2)
                .quantity(1)
                .build();
        cartItemRepository.save(cartItem1);
        cartItemRepository.save(cartItem2);

        var resultList = List.of(cartItem1, cartItem2);

        User user = getUser();
        user.setCartItems(resultList);
        userRepository.save(user);

        assertThat(cartItemRepository.findAllByUserId(user.getId())).isEqualTo(resultList);
    }

}
