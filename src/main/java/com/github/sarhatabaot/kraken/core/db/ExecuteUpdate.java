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
import java.sql.SQLException;

/**
 * @author sarhatabaot
 */
public abstract class ExecuteUpdate {
    private final Logger logger = LoggerFactory.getLogger(ExecuteUpdate.class);
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
            LoggerExtensionsKt.logSevereException(logger, e);
        }
    }
    private @NotNull DSLContext getContext(Connection connection) {
        if (settings ==null)
            return DSL.using(connection, JooqUtil.getDialect(connectionFactory.getType()));

        return DSL.using(connection, JooqUtil.getDialect(connectionFactory.getType()), settings);
    }
    protected abstract void onRunUpdate(DSLContext dslContext);
}
