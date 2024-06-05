package com.vasche.shoestore.repository.impl;

import com.vasche.shoestore.domain.exception.ResourceMappingException;
import com.vasche.shoestore.domain.user.Role;
import com.vasche.shoestore.domain.user.User;
import com.vasche.shoestore.repository.DataSourceConfig;
import com.vasche.shoestore.repository.UserRepository;
import com.vasche.shoestore.repository.mappers.UserRowMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private static final String FIND_BY_ID = """
            SELECT u.id as user_id,
                   u.name as user_name,
                   u.username as user_username,
                   u.password as user_password,
                   ur.role as user_role_role
            FROM users u
            LEFT JOIN users_roles ur ON u.id = ur.user_id
            WHERE u.id = ?
            """;

    private static final String FIND_BY_USERNAME = """
            SELECT u.id as user_id,
                   u.name as user_name,
                   u.username as user_username,
                   u.password as user_password,
                   ur.role as user_role_role
            FROM users u
            LEFT JOIN users_roles ur ON u.id = ur.user_id
            WHERE u.username = ?
            """;

    private static final String UPDATE = """
            UPDATE users
            SET name = ?,
                username = ?,
                password = ?
            WHERE id = ?
            """;

    private static final String CREATE = """
            INSERT INTO users (name, username, password)
            VALUES (?, ?, ?)
            """;

    private static final String INSERT_USER_ROLE = """
            INSERT INTO users_roles (user_id, role)
            VALUES (?, ?)
            """;

    private static final String IS_CART_ITEM_OWNER = """
            SELECT EXISTS(
                SELECT 1
                FROM carts
                WHERE user_id = ? AND cart_item_id = ?
            )
            """;

    private static final String IS_ORDER_ITEM_OWNER = """
            SELECT EXISTS(
                SELECT 1
                FROM order_items ot
                JOIN orders o on o.id = ot.order_id
                WHERE o.user_id = ? AND ot.id = ?
            )
            """;

    private static final String IS_ORDER_OWNER = """
            SELECT EXISTS(
                SELECT 1
                FROM orders
                WHERE user_id = ? AND id = ?
            )
            """;

    private static final String DELETE = """
            DELETE FROM users
            WHERE id = ?
            """;

    private final DataSourceConfig dataSourceConfig;

    @Override
    public Optional<User> findById(Long id) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_BY_ID,
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                return Optional.ofNullable(UserRowMapper.mapRow(resultSet));
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new ResourceMappingException("Error while finding user by id.");
        }
    }

    @Override
    public Optional<User> findByUsername(String username) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_BY_USERNAME,
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            statement.setString(1, username);
            try (ResultSet resultSet = statement.executeQuery()) {
                return Optional.ofNullable(UserRowMapper.mapRow(resultSet));
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new ResourceMappingException("Error while finding user by username.");
        }
    }

    @Override
    public void update(User user) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(UPDATE);
            statement.setString(1, user.getName());
            statement.setString(2, user.getUsername());
            statement.setString(3, user.getPassword());
            statement.setLong(4, user.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new ResourceMappingException("Error while updating user.");
        }
    }

    @Override
    public void create(User user) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(CREATE,
                    PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getName());
            statement.setString(2, user.getUsername());
            statement.setString(3, user.getPassword());
            statement.executeUpdate();
            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                resultSet.next();
                user.setId(resultSet.getLong(1));
            }

        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new ResourceMappingException("Error while creating user.");
        }
    }

    @Override
    public void insertUserRole(Long userId, Role role) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(INSERT_USER_ROLE);
            statement.setLong(1, userId);
            statement.setString(2, role.name());
            statement.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new ResourceMappingException("Error while inserting user role.");
        }
    }

    @Override
    public boolean isCartItemOwner(Long userId, Long cartItemId) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(IS_CART_ITEM_OWNER);
            statement.setLong(1, userId);
            statement.setLong(2, cartItemId);
            try (ResultSet resultSet = statement.executeQuery()) {
                resultSet.next();
                return resultSet.getBoolean(1);
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new ResourceMappingException("Error while checking if user is cart item owner.");
        }
    }

    @Override
    public boolean isOrderItemOwner(Long userId, Long orderItemId) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(IS_ORDER_ITEM_OWNER);
            statement.setLong(1, userId);
            statement.setLong(2, orderItemId);
            try (ResultSet resultSet = statement.executeQuery()) {
                resultSet.next();
                return resultSet.getBoolean(1);
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new ResourceMappingException("Error while checking if user is order item owner.");
        }
    }

    @Override
    public boolean isOrderOwner(Long userId, Long orderId) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(IS_ORDER_OWNER);
            statement.setLong(1, userId);
            statement.setLong(2, orderId);
            try (ResultSet resultSet = statement.executeQuery()) {
                resultSet.next();
                return resultSet.getBoolean(1);
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new ResourceMappingException("Error while checking if user is order owner.");
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
            throw new ResourceMappingException("Error while deleting user.");
        }
    }

}
