package com.github.sarhatabaot.kraken.core.db;

import com.zaxxer.hikari.HikariConfig;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * @author sarhatabaot
 */
public class MariaDbConnectionFactory<T extends JavaPlugin> extends HikariConnectionFactory<T>{
    public MariaDbConnectionFactory(final String poolName) {
        super(poolName);
    }

    @Override
    protected void configureDatabase(final @NotNull HikariConfig config, final String address, final int port, final String databaseName, final String username, final String password) {
        config.setDataSourceClassName("org.mariadb.jdbc.MariaDbDataSource");
        config.addDataSourceProperty("serverName", address);
        config.addDataSourceProperty("port", port);
        config.addDataSourceProperty("databaseName", databaseName);
        config.setUsername(username);
        config.setPassword(password);
    }

    @Override
    public StorageType getType() {
        return StorageType.MARAIADB;
    }

    @Override
    protected void overrideProperties(final @NotNull Map<String, String> properties) {
        //don't override anything
    }
}
