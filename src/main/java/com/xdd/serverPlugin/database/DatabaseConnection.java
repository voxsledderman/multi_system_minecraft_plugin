package com.xdd.serverPlugin.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseConnection {
    private HikariDataSource dataSource;

    public void connect() {
        final HikariConfig hikariConfig = getHikariConfig();

        this.dataSource = new HikariDataSource(hikariConfig);

        try (Connection conn = this.dataSource.getConnection()) {
            if (!conn.isValid(5)) {
                throw new SQLException("Błąd w łączeniu z bazą danych");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Nie udało się nawiązać połączenia z bazą danych", e);
        }
    }

    private static @NotNull HikariConfig getHikariConfig() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl("jdbc:mysql://" +
                DatabaseManager.getHost() + ":" +
                DatabaseManager.getPort() + "/" +
                DatabaseManager.getDatabase());
        hikariConfig.setUsername(DatabaseManager.getUsername());
        hikariConfig.setPassword(DatabaseManager.getPassword());

        hikariConfig.setMinimumIdle(5);
        hikariConfig.setMaximumPoolSize(40);
        hikariConfig.setIdleTimeout(30_000);
        hikariConfig.setMaxLifetime(1_800_000);
        hikariConfig.setConnectionTimeout(10_000);
        hikariConfig.setLeakDetectionThreshold(600_000);
        return hikariConfig;
    }

    public Connection getConnection() throws SQLException {
        if (this.dataSource == null) {
            throw new IllegalStateException("DataSource nie został zainicjalizowany.");
        }
        return this.dataSource.getConnection();
    }

    public void close() {
        if (this.dataSource != null) {
            this.dataSource.close();
        }
    }
}
