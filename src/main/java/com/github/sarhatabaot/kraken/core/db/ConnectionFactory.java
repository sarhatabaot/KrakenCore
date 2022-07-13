package com.github.sarhatabaot.kraken.core.db;

import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author sarhatabaot
 */
public interface ConnectionFactory<T extends JavaPlugin> {
    /**
     * Initialize any settings using a plugin object.
     * @param plugin The plugin
     */
    void init(T plugin, String address, int port, String databaseName, String username, String password);

    /**
     * Shutdown the connection
     * @throws Exception exception
     */
    void shutdown() throws Exception;

    /**
     * @return Returns the connection
     * @throws SQLException Throws SQLException
     */
    Connection getConnection() throws SQLException;

    /**
     * @return SQLite, SQL, MariaDB
     */
    StorageType getType();
}
