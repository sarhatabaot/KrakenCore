package com.github.sarhatabaot.kraken.core.db;

import com.github.sarhatabaot.kraken.core.logging.LoggerUtil;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jooq.DSLContext;
import org.jooq.conf.Settings;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author sarhatabaot
 */
public abstract class ExecuteUpdate<T extends JavaPlugin> {
    private final ConnectionFactory<T> connectionFactory;
    private final Settings settings;

    public ExecuteUpdate(final ConnectionFactory<T> connectionFactory, final Settings settings) {
        this.connectionFactory = connectionFactory;
        this.settings = settings;
    }

    public ExecuteUpdate(final ConnectionFactory<T> connectionFactory) {
        this.connectionFactory = connectionFactory;
        this.settings = null;
    }

    public void executeUpdate() {
        try (Connection connection = connectionFactory.getConnection()) {
            DSLContext dslContext = getContext(connection);
            onRunUpdate(dslContext);
        } catch (SQLException e) {
            LoggerUtil.logSevereException(e);
        }
    }
    private @NotNull DSLContext getContext(Connection connection) {
        if (settings ==null)
            return DSL.using(connection, connectionFactory.getType().getDialect());

        return DSL.using(connection, connectionFactory.getType().getDialect(), settings);
    }
    protected abstract void onRunUpdate(DSLContext dslContext);
}
