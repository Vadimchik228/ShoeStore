package com.vasche.shoestore.repository.mappers;

import com.vasche.shoestore.domain.shoe.Season;
import com.vasche.shoestore.domain.shoe.Sex;
import com.vasche.shoestore.domain.shoe.Shoe;
import com.vasche.shoestore.domain.shoe.ShoeModel;
import lombok.SneakyThrows;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ShoeRowMapper {

    @SneakyThrows
    public static Shoe mapRow(ResultSet resultSet) {
        if (resultSet.next()) {
            Shoe shoe = new Shoe();
            shoe.setId(resultSet.getLong("shoe_id"));
            shoe.setTitle(resultSet.getString("shoe_title"));
            shoe.setDescription(resultSet.getString("shoe_description"));
            shoe.setPrice(resultSet.getBigDecimal("shoe_price"));
            shoe.setSex(Sex.valueOf(resultSet.getString("shoe_sex")));
            shoe.setShoeModel(ShoeModel.valueOf(resultSet.getString("shoe_shoe_model")));
            shoe.setSeason(Season.valueOf(resultSet.getString("shoe_season")));
            shoe.setSize(resultSet.getInt("shoe_size"));
            return shoe;
        }
        return null;
    }

    @SneakyThrows
    public static List<Shoe> mapRows(ResultSet resultSet) {
        List<Shoe> shoes = new ArrayList<>();
        while (resultSet.next()) {
            Shoe shoe = new Shoe();
            shoe.setId(resultSet.getLong("shoe_id"));
            if (!resultSet.wasNull()) {
                shoe.setTitle(resultSet.getString("shoe_title"));
                shoe.setDescription(resultSet.getString("shoe_description"));
                shoe.setPrice(resultSet.getBigDecimal("shoe_price"));
                shoe.setSex(Sex.valueOf(resultSet.getString("shoe_sex")));
                shoe.setShoeModel(ShoeModel.valueOf(resultSet.getString("shoe_shoe_model")));
                shoe.setSeason(Season.valueOf(resultSet.getString("shoe_season")));
                shoe.setSize(resultSet.getInt("shoe_size"));
                shoes.add(shoe);
            }
        }
        return shoes;
    }

}
