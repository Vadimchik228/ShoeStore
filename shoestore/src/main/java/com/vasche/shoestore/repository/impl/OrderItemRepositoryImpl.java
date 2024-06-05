package com.vasche.shoestore.repository.impl;

import com.vasche.shoestore.domain.exception.ResourceMappingException;
import com.vasche.shoestore.domain.orderItem.OrderItem;
import com.vasche.shoestore.repository.DataSourceConfig;
import com.vasche.shoestore.repository.OrderItemRepository;
import com.vasche.shoestore.repository.mappers.OrderItemRowMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class OrderItemRepositoryImpl implements OrderItemRepository {

    private static final String FIND_BY_ID = """
            SELECT id AS order_item_id,
                   order_id AS order_item_order_id,
                   arrival_time AS order_item_arrival_time,
                   shoe_id AS order_item_shoe_id,
                   quantity AS order_item_quantity,
                   status AS order_item_status
            FROM order_items
            WHERE id = ?
            """;

    private static final String FIND_ALL_BY_ORDER_ID = """
            SELECT ot.id AS order_item_id,
                   ot.order_id AS order_item_order_id,
                   ot.arrival_time AS order_item_arrival_time,
                   ot.shoe_id AS order_item_shoe_id,
                   ot.quantity AS order_item_quantity,
                   ot.status AS order_item_status
            FROM order_items ot
            JOIN orders o on o.id = ot.order_id
            WHERE o.id = ?
            """;

    private static final String FIND_ALL_BY_USER_ID = """
            SELECT ot.id AS order_item_id,
                   ot.order_id AS order_item_order_id,
                   ot.arrival_time AS order_item_arrival_time,
                   ot.shoe_id AS order_item_shoe_id,
                   ot.quantity AS order_item_quantity,
                   ot.status AS order_item_status
            FROM order_items ot
            JOIN orders o on o.id = ot.order_id
            WHERE o.user_id = ?
            """;

    private static final String UPDATE = """
            UPDATE order_items
            SET order_id = ?,
                arrival_time = ?,
                shoe_id = ?,
                quantity = ?,
                status = ?
            WHERE id = ?
            """;

    private static final String CREATE = """
            INSERT INTO order_items (order_id, arrival_time, shoe_id, quantity, status)
            VALUES (?, ?, ?, ?, ?)
            """;

    private static final String DELETE = """
            DELETE FROM order_items
            WHERE id = ?
            """;

    private final DataSourceConfig dataSourceConfig;

    @Override
    public Optional<OrderItem> findById(Long id) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_BY_ID,
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                return Optional.ofNullable(OrderItemRowMapper.mapRow(resultSet));
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new ResourceMappingException("Error while finding order item by id.");
        }
    }

    @Override
    public List<OrderItem> findAllByOrderId(Long orderId) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_ALL_BY_ORDER_ID);
            statement.setLong(1, orderId);
            try (ResultSet resultSet = statement.executeQuery()) {
                return OrderItemRowMapper.mapRows(resultSet);
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new ResourceMappingException("Error while finding all order items by orderId.");
        }
    }

    @Override
    public List<OrderItem> findAllByUserId(Long userId) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_ALL_BY_USER_ID);
            statement.setLong(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                return OrderItemRowMapper.mapRows(resultSet);
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new ResourceMappingException("Error while finding all order items by userId.");
        }
    }

    @Override
    public void update(OrderItem orderItem) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(UPDATE);
            statement.setLong(1, orderItem.getOrderId());
            if (orderItem.getArrivalTime() == null) {
                statement.setNull(2, Types.TIMESTAMP);
            } else {
                statement.setTimestamp(2, Timestamp.valueOf(orderItem.getArrivalTime()));
            }
            statement.setLong(3, orderItem.getShoeId());
            statement.setInt(4, orderItem.getQuantity());
            statement.setString(5, orderItem.getStatus().name());
            statement.setLong(6, orderItem.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new ResourceMappingException("Error while updating order item.");
        }
    }

    @Override
    public void create(OrderItem orderItem) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(CREATE,
                    PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setLong(1, orderItem.getOrderId());
            if (orderItem.getArrivalTime() == null) {
                statement.setNull(2, Types.TIMESTAMP);
            } else {
                statement.setTimestamp(2, Timestamp.valueOf(orderItem.getArrivalTime()));
            }
            statement.setLong(3, orderItem.getShoeId());
            statement.setInt(4, orderItem.getQuantity());
            statement.setString(5, orderItem.getStatus().name());
            statement.executeUpdate();
            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                resultSet.next();
                orderItem.setId(resultSet.getLong(1));
            }

        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new ResourceMappingException("Error while creating order item.");
        }
    }

    @Override
    public void delete(Long id) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(DELETE);
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new ResourceMappingException("Error while deleting order item.");
        }
    }
}
