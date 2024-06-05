package com.vasche.shoestore.repository.mappers;


import com.vasche.shoestore.domain.cartItem.CartItem;
import lombok.SneakyThrows;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CartItemRowMapper {

    @SneakyThrows
    public static CartItem mapRow(ResultSet resultSet) {
        if (resultSet.next()) {
            CartItem item = new CartItem();
            item.setId(resultSet.getLong("cart_item_id"));
            item.setShoeId(resultSet.getLong("cart_item_shoe_id"));
            item.setQuantity(resultSet.getInt("cart_item_quantity"));
            return item;
        }
        return null;
    }

    @SneakyThrows
    public static List<CartItem> mapRows(ResultSet resultSet) {
        List<CartItem> items = new ArrayList<>();
        while (resultSet.next()) {
            CartItem item = new CartItem();
            item.setId(resultSet.getLong("cart_item_id"));
            if (!resultSet.wasNull()) {
                item.setShoeId(resultSet.getLong("cart_item_shoe_id"));
                item.setQuantity(resultSet.getInt("cart_item_quantity"));
                items.add(item);
            }
        }
        return items;
    }

}
