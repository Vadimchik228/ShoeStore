package com.vasche.shoestore.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.sql.DataSource;
import java.sql.Connection;

@Configuration
@RequiredArgsConstructor
public class DataSourceConfig {

    private final DataSource dataSource; // интерфейс в Java JDBC, который представляет источник данных.

    public Connection getConnection() {
        return DataSourceUtils.getConnection(dataSource);
    }
}
