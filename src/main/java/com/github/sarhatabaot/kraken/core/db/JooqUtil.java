package com.github.sarhatabaot.kraken.core.db;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jooq.SQLDialect;

/**
 * @author sarhatabaot
 */
public class JooqUtil {
    private JooqUtil() {
        throw new UnsupportedOperationException();
    }
    
    /**
     * @param dialect Dialect
     * @return Return the sql dialect from a string.
     */
    @Contract(pure = true)
    public static SQLDialect getDialect(final @NotNull String dialect) {
        return switch (dialect.toLowerCase()) {
            case "mariadb" -> SQLDialect.MARIADB;
            case "sqlite" -> SQLDialect.SQLITE;
            case "mysql"-> SQLDialect.MYSQL;
            default -> SQLDialect.DEFAULT;
        };
    }
}
