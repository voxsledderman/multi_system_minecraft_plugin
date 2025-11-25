package com.xdd.serverPlugin.database;

import com.j256.ormlite.jdbc.DataSourceConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.SQLException;

public class DatabaseConnection {

    private HikariDataSource dataSource;
    private ConnectionSource connectionSource;

    public void connect() throws SQLException {

        HikariConfig config = getHikariConfig();
        this.dataSource = new HikariDataSource(config);
        this.connectionSource = new DataSourceConnectionSource(this.dataSource, config.getJdbcUrl());
    }

    public ConnectionSource getConnectionSource() {
        if (connectionSource == null) {
            throw new IllegalStateException("ConnectionSource nie został zainicjalizowany");
        }
        return connectionSource;
    }

    public void close() throws Exception {
        if (connectionSource != null) {
            connectionSource.close();
        }
        if (dataSource != null) {
            dataSource.close();
        }
    }

    private static HikariConfig getHikariConfig() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl("jdbc:mysql://" +
                DatabaseManager.getHost() + ":" +
                DatabaseManager.getPort() + "/" +
                DatabaseManager.getDatabase() +
                "?useSSL=false&allowPublicKeyRetrieval=true&characterEncoding=utf8&serverTimezone=UTC");
        hikariConfig.setUsername(DatabaseManager.getUsername());
        hikariConfig.setPassword(DatabaseManager.getPassword());
        hikariConfig.setMinimumIdle(5);
        hikariConfig.setMaximumPoolSize(40);
        hikariConfig.setIdleTimeout(30_000);
        hikariConfig.setMaxLifetime(1_800_000);
        hikariConfig.setConnectionTimeout(10_000);
        hikariConfig.setLeakDetectionThreshold(600_000);
        hikariConfig.setDriverClassName("com.mysql.cj.jdbc.Driver");
        return hikariConfig;
    }
}
