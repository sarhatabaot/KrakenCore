package com.github.sarhatabaot.kraken.core.db;

import com.zaxxer.hikari.HikariConfig;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * @author sarhatabaot
 */
public class MySqlConnectionFactory<T extends JavaPlugin> extends HikariConnectionFactory<T>{
    public MySqlConnectionFactory(final String poolName) {
        super(poolName);
    }

    @Override
    protected void configureDatabase(final HikariConfig config, final String address, final int port, final String databaseName, final String username, final String password) {
        config.setJdbcUrl("jdbc:mysql://"+address+":"+port+"/"+databaseName);
        config.setUsername(username);
        config.setPassword(password);
    }


    @Override
    public StorageType getType() {
        return StorageType.MYSQL;
    }

    @Override
    protected void overrideProperties(final @NotNull Map<String, String> properties) {
        properties.putIfAbsent("cachePrepStmts", "true");
        properties.putIfAbsent("prepStmtCacheSize", "250");
        properties.putIfAbsent("prepStmtCacheSqlLimit", "2048");
        properties.putIfAbsent("useServerPrepStmts", "true");
        properties.putIfAbsent("useLocalSessionState", "true");
        properties.putIfAbsent("rewriteBatchedStatements", "true");
        properties.putIfAbsent("cacheResultSetMetadata", "true");
        properties.putIfAbsent("cacheServerConfiguration", "true");
        properties.putIfAbsent("elideSetAutoCommits", "true");
        properties.putIfAbsent("maintainTimeStats", "false");
        properties.putIfAbsent("alwaysSendSetIsolation", "false");
        properties.putIfAbsent("cacheCallableStmts", "true");

        // https://stackoverflow.com/a/54256150
        properties.putIfAbsent("serverTimezone", "UTC");

        super.overrideProperties(properties);
    }
}
