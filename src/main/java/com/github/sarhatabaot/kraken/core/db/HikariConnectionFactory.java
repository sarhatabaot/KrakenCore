package com.github.sarhatabaot.kraken.core.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author sarhatabaot
 */
public abstract class HikariConnectionFactory<T extends JavaPlugin> implements ConnectionFactory<T> {
    private final String poolName;
    protected HikariDataSource dataSource;
    private final Logger logger = LoggerFactory.getLogger(HikariConnectionFactory.class);

    protected HikariConnectionFactory(final String poolName) {
        this.poolName = poolName;
    }

    /**
     * This may be different with every database type.
     *
     * @param config       hikari config
     * @param address      address
     * @param port         port
     * @param databaseName databaseName
     * @param username     username
     * @param password     password
     */
    protected abstract void configureDatabase(HikariConfig config, String address, int port, String databaseName, String username, String password);

    @Override
    public void init(final T plugin, String address, int port, String databaseName, String username, String password) {
        HikariConfig config = new HikariConfig();
        config.setPoolName(poolName);

        configureDatabase(config, address, port, databaseName, username, password);
        config.setInitializationFailTimeout(-1);

        Map<String, String> properties = new HashMap<>();

        overrideProperties(properties);
        setProperties(config, properties);

        this.dataSource = new HikariDataSource(config);
        logger.info("Connected to database!");

        postInitialize();
    }

    /**
     * Called after the pool has been initialised
     */
    protected void postInitialize() {

    }

    //LP
    protected void overrideProperties(@NotNull Map<String, String> properties) {
        properties.putIfAbsent("socketTimeout", String.valueOf(TimeUnit.SECONDS.toMillis(30)));
    }

    //LP
    protected void setProperties(HikariConfig config, @NotNull Map<String, String> properties) {
        for (Map.Entry<String, String> property : properties.entrySet()) {
            config.addDataSourceProperty(property.getKey(), property.getValue());
        }
    }

    @Override
    public void shutdown() throws Exception {
        if (this.dataSource != null) {
            this.dataSource.close();
        }
    }

    @Override
    public Connection getConnection() throws SQLException {
        if (this.dataSource == null) {
            throw new SQLException("Data Source is null.");
        }

        Connection connection = this.dataSource.getConnection();
        if (connection == null) {
            throw new SQLException("Connection is null");
        }

        return connection;
    }

}
