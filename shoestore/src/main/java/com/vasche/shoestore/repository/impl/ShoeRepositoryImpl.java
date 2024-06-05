package com.vasche.shoestore.repository.impl;

import com.vasche.shoestore.domain.exception.ResourceMappingException;
import com.vasche.shoestore.domain.shoe.Shoe;
import com.vasche.shoestore.repository.DataSourceConfig;
import com.vasche.shoestore.repository.ShoeRepository;
import com.vasche.shoestore.repository.mappers.ShoeRowMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class ShoeRepositoryImpl implements ShoeRepository {

    private static final String FIND_BY_ID = """
            SELECT id as shoe_id,
                   title as shoe_title,
                   description as shoe_description,
                   price as shoe_price,
                   sex as shoe_sex,
                   shoe_model as shoe_shoe_model,
                   season as shoe_season,
                   size as shoe_size
            FROM shoes
            WHERE id = ?
            """;

    private static final String FIND_ALL = """
            SELECT id as shoe_id,
                   title as shoe_title,
                   description as shoe_description,
                   price as shoe_price,
                   sex as shoe_sex,
                   shoe_model as shoe_shoe_model,
                   season as shoe_season,
                   size as shoe_size
            FROM shoes
            """;

    private static final String UPDATE = """
            UPDATE shoes
            SET title = ?,
                description = ?,
                price = ?,
                sex = ?,
                shoe_model = ?,
                season = ?,
                size = ?
            WHERE id = ?
            """;

    private static final String CREATE = """
            INSERT INTO shoes (title, description, price, sex, shoe_model, season, size)
            VALUES (?, ?, ?, ?, ?, ?, ?)
            """;

    private static final String DELETE = """
            DELETE FROM shoes
            WHERE id = ?
            """;

    private final DataSourceConfig dataSourceConfig;


    @Override
    public Optional<Shoe> findById(Long id) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_BY_ID,
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                return Optional.ofNullable(ShoeRowMapper.mapRow(resultSet));
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new ResourceMappingException("Error while finding shoe by id.");
        }
    }

    @Override
    public List<Shoe> findAll() {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_ALL);
            try (ResultSet resultSet = statement.executeQuery()) {
                return ShoeRowMapper.mapRows(resultSet);
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new ResourceMappingException("Error while finding all shoes.");
        }
    }

    @Override
    public void update(Shoe shoe) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(UPDATE);
            statement.setString(1, shoe.getTitle());
            if (shoe.getDescription() == null) {
                statement.setNull(2, Types.VARCHAR);
            } else {
                statement.setString(2, shoe.getDescription());
            }
            statement.setBigDecimal(3, shoe.getPrice());
            statement.setString(4, shoe.getSex().name());
            statement.setString(5, shoe.getShoeModel().name());
            statement.setString(6, shoe.getSeason().name());
            statement.setInt(7, shoe.getSize());
            statement.setLong(8, shoe.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new ResourceMappingException("Error while updating user.");
        }
    }

    @Override
    public void create(Shoe shoe) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(CREATE,
                    PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, shoe.getTitle());
            if (shoe.getDescription() == null) {
                statement.setNull(2, Types.VARCHAR);
            } else {
                statement.setString(2, shoe.getDescription());
            }
            statement.setBigDecimal(3, shoe.getPrice());
            statement.setString(4, shoe.getSex().name());
            statement.setString(5, shoe.getShoeModel().name());
            statement.setString(6, shoe.getSeason().name());
            statement.setInt(7, shoe.getSize());
            statement.executeUpdate();
            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                resultSet.next();
                shoe.setId(resultSet.getLong(1));
            }

        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new ResourceMappingException("Error while creating shoe.");
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
            throw new ResourceMappingException("Error while deleting shoe.");
        }
    }
}
