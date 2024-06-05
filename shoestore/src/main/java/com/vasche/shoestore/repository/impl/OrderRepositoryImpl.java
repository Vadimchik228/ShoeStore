package com.vasche.shoestore.repository.impl;

import com.vasche.shoestore.domain.exception.ResourceMappingException;
import com.vasche.shoestore.domain.order.Order;
import com.vasche.shoestore.repository.DataSourceConfig;
import com.vasche.shoestore.repository.OrderRepository;
import com.vasche.shoestore.repository.mappers.OrderRowMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepository {

    private static final String FIND_BY_ID = """
            SELECT id as order_id,
                   order_time as order_order_time,
                   first_name as order_first_name,
                   last_name as order_last_name,
                   city as order_city,
                   address as order_address,
                   email as order_email,
                   phone_number as order_phone_number,
                   user_id as order_user_id
            FROM orders
            WHERE id = ?
            """;

    private static final String FIND_ALL_BY_USER_ID = """
            SELECT id as order_id,
                   order_time as order_order_time,
                   first_name as order_first_name,
                   last_name as order_last_name,
                   city as order_city,
                   address as order_address,
                   email as order_email,
                   phone_number as order_phone_number,
                   user_id as order_user_id
            FROM orders
            WHERE user_id = ?
            """;

    private static final String FIND_ALL = """
            SELECT id as order_id,
                   order_time as order_order_time,
                   first_name as order_first_name,
                   last_name as order_last_name,
                   city as order_city,
                   address as order_address,
                   email as order_email,
                   phone_number as order_phone_number,
                   user_id as order_user_id
            FROM orders
            """;

    private static final String UPDATE = """
            UPDATE orders
            SET order_time = ?,
                first_name = ?,
                last_name = ?,
                city = ?,
                address = ?,
                email = ?,
                phone_number = ?,
                user_id = ?
            WHERE id = ?
            """;

    private static final String CREATE = """
            INSERT INTO orders (order_time, first_name, last_name, city, address, email, phone_number, user_id)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?)
            """;

    private static final String DELETE = """
            DELETE FROM orders
            WHERE id = ?
            """;


    private final DataSourceConfig dataSourceConfig;

    @Override
    public Optional<Order> findById(Long id) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_BY_ID,
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                return Optional.ofNullable(OrderRowMapper.mapRow(resultSet));
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new ResourceMappingException("Error while finding order by id.");
        }
    }

    @Override
    public List<Order> findAllByUserId(Long userId) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_ALL_BY_USER_ID);
            statement.setLong(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                return OrderRowMapper.mapRows(resultSet);
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new ResourceMappingException("Error while finding all orders by userId.");
        }
    }

    @Override
    public List<Order> findAll() {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_ALL);
            try (ResultSet resultSet = statement.executeQuery()) {
                return OrderRowMapper.mapRows(resultSet);
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new ResourceMappingException("Error while finding all orders.");
        }
    }

    @Override
    public void update(Order order) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(UPDATE);
            statement.setTimestamp(1, Timestamp.valueOf(order.getOrderTime()));
            statement.setString(2, order.getFirstName());
            statement.setString(3, order.getLastName());
            statement.setString(4, order.getCity());
            statement.setString(5, order.getAddress());
            statement.setString(6, order.getEmail());
            statement.setString(7, order.getPhoneNumber());
            statement.setLong(8, order.getUserId());
            statement.setLong(9, order.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new ResourceMappingException("Error while updating order.");
        }
    }

    @Override
    public void create(Order order) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(CREATE,
                    PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setTimestamp(1, Timestamp.valueOf(order.getOrderTime()));
            statement.setString(2, order.getFirstName());
            statement.setString(3, order.getLastName());
            statement.setString(4, order.getCity());
            statement.setString(5, order.getAddress());
            statement.setString(6, order.getEmail());
            statement.setString(7, order.getPhoneNumber());
            statement.setLong(8, order.getUserId());
            statement.executeUpdate();
            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                resultSet.next();
                order.setId(resultSet.getLong(1));
            }

        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new ResourceMappingException("Error while creating order.");
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
            throw new ResourceMappingException("Error while deleting order.");
        }
    }
}
