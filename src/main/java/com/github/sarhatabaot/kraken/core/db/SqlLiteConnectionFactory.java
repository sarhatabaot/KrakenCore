package com.github.sarhatabaot.kraken.core.db;

import com.zaxxer.hikari.HikariConfig;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

/**
 * @author sarhatabaot
 */
public class SqlLiteConnectionFactory<T extends JavaPlugin> extends HikariConnectionFactory<T>{
    protected SqlLiteConnectionFactory(final String poolName) {
        super(poolName);
    }

    @Override
    public StorageType getType() {
        return StorageType.SQLITE;
    }

    @Override
    protected void configureDatabase(final @NotNull HikariConfig config, final String address, final int port, final String databaseName, final String username, final String password) {
        config.setDataSourceClassName("org.sqlite.SQLiteDataSource");
        config.addDataSourceProperty("serverName", address);
        config.addDataSourceProperty("port", port);
        config.addDataSourceProperty("databaseName", databaseName);
        config.setUsername(username);
        config.setPassword(password);
    }
}
