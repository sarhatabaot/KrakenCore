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
abstract class ExecuteUpdate {
    private val logger = LoggerFactory.getLogger(ExecuteUpdate::class.java)
    private var connectionFactory: ConnectionFactory? = null
    private var settings: Settings? = null

    open fun ExecuteUpdate(connectionFactory: ConnectionFactory?, settings: Settings?) {
        this.connectionFactory = connectionFactory
        this.settings = settings
    }

    open fun ExecuteUpdate(connectionFactory: ConnectionFactory?) {
        this.connectionFactory = connectionFactory
        settings = null
    }

    open fun executeUpdate() {
        try {
            connectionFactory!!.connection.use { connection ->
                val dslContext = getContext(connection)
                onRunUpdate(dslContext)
            }
        } catch (e: SQLException) {
            logger.logSevereException(e)
        }
    }

    private fun getContext(connection: Connection): DSLContext {
        return if (settings == null) DSL.using(connection, getDialect(connectionFactory!!.type)) else DSL.using(
            connection,
            getDialect(connectionFactory!!.type),
            settings
        )
    }

    protected abstract fun onRunUpdate(dslContext: DSLContext?)
}