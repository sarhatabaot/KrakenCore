package com.github.sarhatabaot.kraken.core.db;

import org.jooq.SQLDialect;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author sarhatabaot
 */
class JooqUtilTest {
    
    @Test
    void getDialect() {
        assertEquals(SQLDialect.DEFAULT, JooqUtil.getDialect("defaultTest"));
        assertEquals(SQLDialect.MYSQL, JooqUtil.getDialect("mysql"));
        assertEquals(SQLDialect.MYSQL, JooqUtil.getDialect("mYSql"));
        
        assertEquals(SQLDialect.SQLITE,JooqUtil.getDialect("sqlite"));
        assertEquals(SQLDialect.MARIADB,JooqUtil.getDialect("mariadb"));
    }
}