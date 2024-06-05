package com.vasche.shoestore.repository.mappers;

import com.vasche.shoestore.domain.order.Order;
import lombok.SneakyThrows;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class OrderRowMapper {

    @SneakyThrows
    public static Order mapRow(ResultSet resultSet) {
        if (resultSet.next()) {
            Order order = new Order();
            order.setId(resultSet.getLong("order_id"));
            order.setOrderTime(resultSet.getTimestamp("order_order_time").toLocalDateTime());
            order.setFirstName(resultSet.getString("order_first_name"));
            order.setLastName(resultSet.getString("order_last_name"));
            order.setCity(resultSet.getString("order_city"));
            order.setAddress(resultSet.getString("order_address"));
            order.setEmail(resultSet.getString("order_email"));
            order.setPhoneNumber(resultSet.getString("order_phone_number"));
            order.setUserId(resultSet.getLong("order_user_id"));

            return order;
        }
        return null;
    }

    @SneakyThrows
    public static List<Order> mapRows(ResultSet resultSet) {
        List<Order> orders = new ArrayList<>();
        while (resultSet.next()) {
            Order order = new Order();
            order.setId(resultSet.getLong("order_id"));
            if (!resultSet.wasNull()) {
                order.setOrderTime(resultSet.getTimestamp("order_order_time").toLocalDateTime());
                order.setFirstName(resultSet.getString("order_first_name"));
                order.setLastName(resultSet.getString("order_last_name"));
                order.setCity(resultSet.getString("order_city"));
                order.setAddress(resultSet.getString("order_address"));
                order.setEmail(resultSet.getString("order_email"));
                order.setPhoneNumber(resultSet.getString("order_phone_number"));
                order.setUserId(resultSet.getLong("order_user_id"));
                orders.add(order);
            }
        }
        return orders;
    }

}
