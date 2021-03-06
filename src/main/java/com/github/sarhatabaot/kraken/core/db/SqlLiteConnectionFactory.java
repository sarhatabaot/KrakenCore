package com.github.sarhatabaot.kraken.core.db;

import com.zaxxer.hikari.HikariConfig;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Map;

/**
 * @author sarhatabaot
 */
public class SqlLiteConnectionFactory<T extends JavaPlugin> extends HikariConnectionFactory<T> {
    public SqlLiteConnectionFactory(final String poolName) {
        super(poolName);
    }

    @Override
    public StorageType getType() {
        return StorageType.SQLITE;
    }

    @Override
    protected void configureDatabase(final @NotNull HikariConfig config, final String address, final int port, final String databaseName, final String username, final String password) {
        config.setJdbcUrl("jdbc:sqlite:" + plugin.getDataFolder() + File.separator + databaseName + ".db");
    }

    @Override
    protected void overrideProperties(@NotNull final Map<String, String> properties) {
        // sqlite doesn't have sockettimeout
    }
}
