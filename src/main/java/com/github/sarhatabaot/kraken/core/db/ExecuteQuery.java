package com.github.sarhatabaot.kraken.core.db;

import com.github.sarhatabaot.kraken.core.extensions.LoggerExtensionsKt;
import com.lapzupi.dev.connection.ConnectionFactory;
import org.jetbrains.annotations.NotNull;
import org.jooq.DSLContext;
import org.jooq.conf.Settings;
import org.jooq.impl.DSL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;

/**
 * @author sarhatabaot
 */
public abstract class ExecuteQuery<T, R> {
    private final Logger logger = LoggerFactory.getLogger(ExecuteQuery.class);
    private final ConnectionFactory connectionFactory;
    private final Settings settings;

    public ExecuteQuery(final ConnectionFactory connectionFactory, final Settings settings) {
        this.connectionFactory = connectionFactory;
        this.settings = settings;
    }

    public ExecuteQuery(final ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
        this.settings = null;
    }

    public T prepareAndRunQuery() {
        try (Connection connection = connectionFactory.getConnection()) {
            DSLContext dslContext = getContext(connection);
            return onRunQuery(dslContext);
        } catch (Exception e) {
            LoggerExtensionsKt.logSevereException(logger, e);
        }
        return empty();
    }

    private @NotNull DSLContext getContext(Connection connection) {
        if (settings ==null)
            return DSL.using(connection, JooqUtil.getDialect(connectionFactory.getType()));

        return DSL.using(connection, JooqUtil.getDialect(connectionFactory.getType()), settings);
    }

    public abstract T onRunQuery(DSLContext dslContext) throws Exception;

    public abstract T getQuery(@NotNull final R result) throws Exception;

    public abstract T empty();

}
