package com.github.sarhatabaot.kraken.core.db

import org.jooq.SQLDialect
import java.util.*

/**
 *
 * @author sarhatabaot
 */
object JooqUtil {
    fun getDialect(dialect: String): SQLDialect {
        return when (dialect.lowercase(Locale.getDefault())) {
            "mariadb" -> SQLDialect.MARIADB
            "sqlite" -> SQLDialect.SQLITE
            "mysql"-> SQLDialect.MYSQL
            else -> SQLDialect.DEFAULT
        }
    }
}