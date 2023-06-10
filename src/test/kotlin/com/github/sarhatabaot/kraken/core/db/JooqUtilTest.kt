package com.github.sarhatabaot.kraken.core.db

import com.github.sarhatabaot.kraken.core.db.JooqUtil.getDialect
import org.jooq.SQLDialect
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

/**
 *
 * @author sarhatabaot
 */
class JooqUtilTest {

    @Test
    fun getDialect() {
        assertEquals(SQLDialect.DEFAULT, getDialect("defaultTest"))
        assertEquals(SQLDialect.MYSQL, getDialect("mysql"))
        assertEquals(SQLDialect.MYSQL, getDialect("mYSql"))

        assertEquals(SQLDialect.SQLITE, getDialect("sqlite"))
        assertEquals(SQLDialect.MARIADB, getDialect("mariadb"))
    }
}