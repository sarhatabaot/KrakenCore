package com.github.sarhatabaot.kraken.core.db

import com.github.sarhatabaot.kraken.core.db.JooqUtil.getDialect
import com.github.sarhatabaot.kraken.core.extensions.logSevereException
import com.lapzupi.dev.connection.ConnectionFactory
import org.jooq.DSLContext
import org.jooq.conf.Settings
import org.jooq.impl.DSL
import org.slf4j.LoggerFactory
import java.sql.Connection
import java.sql.SQLException

/**
 *
 * @author sarhatabaot
 */
abstract class ExecuteUpdate(
    private var  connectionFactory: ConnectionFactory,
    private var settings: Settings
) {
    private val logger = LoggerFactory.getLogger(ExecuteUpdate::class.java)

    open fun executeUpdate() {
        try {
            connectionFactory.connection.use { connection ->
                val dslContext = getContext(connection)
                onRunUpdate(dslContext)
            }
        } catch (e: SQLException) {
            logger.logSevereException(e)
        }
    }

    private fun getContext(connection: Connection): DSLContext {
        return DSL.using(
            connection,
            getDialect(connectionFactory.type),
            settings
        )
    }

    protected abstract fun onRunUpdate(dslContext: DSLContext?)
}