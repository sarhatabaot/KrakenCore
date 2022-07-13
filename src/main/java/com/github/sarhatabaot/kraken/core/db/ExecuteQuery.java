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
public abstract class ExecuteQuery<T, R, U extends JavaPlugin> {
    private final ConnectionFactory<U> connectionFactory;
    private final Settings settings;

    public ExecuteQuery(final ConnectionFactory<U> connectionFactory, final Settings settings) {
        this.connectionFactory = connectionFactory;
        this.settings = settings;
    }

    public ExecuteQuery(final ConnectionFactory<U> connectionFactory) {
        this.connectionFactory = connectionFactory;
        this.settings = null;
    }

    public T prepareAndRunQuery() {
        try (Connection connection = connectionFactory.getConnection()) {
            DSLContext dslContext = getContext(connection);
            return onRunQuery(dslContext);
        } catch (Exception e) {
            LoggerUtil.logSevereException(e);
        }
        return empty();
    }

    private @NotNull DSLContext getContext(Connection connection) {
        if (settings ==null)
            return DSL.using(connection, connectionFactory.getType().getDialect());

        return DSL.using(connection, connectionFactory.getType().getDialect(), settings);
    }

    public abstract T onRunQuery(DSLContext dslContext) throws Exception;

    public abstract T getQuery(@NotNull final R result) throws Exception;

    public abstract T empty();

}
