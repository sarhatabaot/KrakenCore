package com.github.sarhatabaot.kraken.core.db

import com.github.sarhatabaot.kraken.core.db.JooqUtil.getDialect
import com.github.sarhatabaot.kraken.core.extensions.logSevereException
import com.lapzupi.dev.connection.ConnectionFactory
import org.jooq.DSLContext
import org.jooq.conf.Settings
import org.jooq.impl.DSL
import org.slf4j.LoggerFactory
import java.sql.Connection

/**
 *
 * @author sarhatabaot
 */
abstract class ExecuteQuery<T, R>(
    private var connectionFactory: ConnectionFactory,
    private var settings: Settings
) {
    private val logger = LoggerFactory.getLogger(ExecuteQuery::class.java)

    open fun prepareAndRunQuery(): T {
        try {
            connectionFactory.connection.use { connection ->
                val dslContext = getContext(connection)
                return onRunQuery(dslContext)
            }
        } catch (e: Exception) {
            logger.logSevereException(e)
        }
        return empty()
    }

    private fun getContext(connection: Connection): DSLContext {
        return DSL.using(
            connection,
            getDialect(connectionFactory.type),
            settings
        )
    }

    @Throws(Exception::class)
    abstract fun onRunQuery(dslContext: DSLContext?): T

    @Throws(Exception::class)
    abstract fun getQuery(result: R): T

    abstract fun empty(): T
}