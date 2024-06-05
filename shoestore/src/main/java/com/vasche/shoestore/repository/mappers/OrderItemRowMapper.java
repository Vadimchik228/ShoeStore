package com.vasche.shoestore.repository.mappers;

import com.vasche.shoestore.domain.orderItem.OrderItem;
import com.vasche.shoestore.domain.orderItem.Status;
import lombok.SneakyThrows;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class OrderItemRowMapper {

    @SneakyThrows
    public static OrderItem mapRow(ResultSet resultSet) {
        if (resultSet.next()) {
            OrderItem item = new OrderItem();
            item.setId(resultSet.getLong("order_item_id"));
            item.setOrderId(resultSet.getLong("order_item_order_id"));
            Timestamp timestamp = resultSet.getTimestamp("order_item_arrival_time");
            if (timestamp != null) {
                item.setArrivalTime(timestamp.toLocalDateTime());
            }
            item.setShoeId(resultSet.getLong("order_item_shoe_id"));
            item.setQuantity(resultSet.getInt("order_item_quantity"));
            item.setStatus(Status.valueOf(resultSet.getString("order_item_status")));
            return item;
        }
        return null;
    }

    @SneakyThrows
    public static List<OrderItem> mapRows(ResultSet resultSet) {
        List<OrderItem> items = new ArrayList<>();
        while (resultSet.next()) {
            OrderItem item = new OrderItem();
            item.setId(resultSet.getLong("order_item_id"));
            if (!resultSet.wasNull()) {
                item.setOrderId(resultSet.getLong("order_item_order_id"));
                Timestamp timestamp = resultSet.getTimestamp("order_item_arrival_time");
                if (timestamp != null) {
                    item.setArrivalTime(timestamp.toLocalDateTime());
                }
                item.setShoeId(resultSet.getLong("order_item_shoe_id"));
                item.setQuantity(resultSet.getInt("order_item_quantity"));
                item.setStatus(Status.valueOf(resultSet.getString("order_item_status")));
                items.add(item);
            }
        }
        return items;
    }

}
