package com.github.sarhatabaot.kraken.core.db;

import com.github.sarhatabaot.kraken.core.logging.LoggerUtil;
import com.lapzupi.dev.connection.ConnectionFactory;
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
    private final ConnectionFactory connectionFactory;
    private final Settings settings;

    public ExecuteUpdate(final ConnectionFactory connectionFactory, final Settings settings) {
        this.connectionFactory = connectionFactory;
        this.settings = settings;
    }

    public ExecuteUpdate(final ConnectionFactory connectionFactory) {
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
            return DSL.using(connection, JooqUtil.getDialect(connectionFactory.getType()));

        return DSL.using(connection, JooqUtil.getDialect(connectionFactory.getType()), settings);
    }
    protected abstract void onRunUpdate(DSLContext dslContext);
}
