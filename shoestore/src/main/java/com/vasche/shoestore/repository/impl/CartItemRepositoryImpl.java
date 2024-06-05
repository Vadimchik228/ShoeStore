package com.vasche.shoestore.repository.impl;

import com.vasche.shoestore.domain.cartItem.CartItem;
import com.vasche.shoestore.domain.exception.ResourceMappingException;
import com.vasche.shoestore.repository.CartItemRepository;
import com.vasche.shoestore.repository.DataSourceConfig;
import com.vasche.shoestore.repository.mappers.CartItemRowMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class CartItemRepositoryImpl implements CartItemRepository {

    private static final String FIND_BY_ID = """
            SELECT ci.id AS cart_item_id,
                   ci.shoe_id AS cart_item_shoe_id,
                   ci.quantity AS cart_item_quantity
            FROM cart_items ci
            WHERE ci.id = ?
            """;

    private static final String FIND_ALL_BY_USER_ID = """
            SELECT ci.id AS cart_item_id,
                   ci.shoe_id AS cart_item_shoe_id,
                   ci.quantity AS cart_item_quantity
            FROM cart_items ci
            JOIN carts c on ci.id = c.cart_item_id
            WHERE c.user_id = ?
            """;

    private static final String ASSIGN = """
            INSERT INTO carts (cart_item_id, user_id)
            VALUES (?, ?)
            """;

    private static final String UPDATE = """
            UPDATE cart_items
            SET shoe_id = ?,
                quantity = ?
            WHERE id = ?
            """;

    private static final String CREATE = """
            INSERT INTO cart_items (shoe_id, quantity)
            VALUES (?, ?)
            """;

    private static final String DELETE = """
            DELETE FROM cart_items
            WHERE id = ?
            """;

    private final DataSourceConfig dataSourceConfig;

    @Override
    public Optional<CartItem> findById(Long id) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_BY_ID,
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                return Optional.ofNullable(CartItemRowMapper.mapRow(resultSet));
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new ResourceMappingException("Error while finding cart item by id.");
        }
    }

    @Override
    public List<CartItem> findAllByUserId(Long userId) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_ALL_BY_USER_ID);
            statement.setLong(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                return CartItemRowMapper.mapRows(resultSet);
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new ResourceMappingException("Error while finding all cart items by userId.");
        }
    }

    @Override
    public void assignToUserById(Long cartItemId, Long userId) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(ASSIGN);
            statement.setLong(1, cartItemId);
            statement.setLong(2, userId);
            statement.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new ResourceMappingException("Error while assigning to user.");
        }
    }

    @Override
    public void update(CartItem cartItem) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(UPDATE);
            statement.setLong(1, cartItem.getShoeId());
            statement.setInt(2, cartItem.getQuantity());
            statement.setLong(3, cartItem.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new ResourceMappingException("Error while updating cart item.");
        }
    }

    @Override
    public void create(CartItem cartItem) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(CREATE,
                    PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setLong(1, cartItem.getShoeId());
            statement.setInt(2, cartItem.getQuantity());
            statement.executeUpdate();
            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                resultSet.next();
                cartItem.setId(resultSet.getLong(1));
            }

        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new ResourceMappingException("Error while creating cart item.");
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
            throw new ResourceMappingException("Error while deleting cart item.");
        }
    }
}
