package com.github.sarhatabaot.kraken.core.db;

import org.jooq.SQLDialect;

/**
 * @author sarhatabaot
 */
public enum StorageType {
    YAML(null,"yaml"),
    HOCON(null,"hocon"),
    JSON(null,"json"),
    MARAIADB(SQLDialect.MARIADB, "mariadb"),
    MYSQL(SQLDialect.MYSQL,"mysql"),
    SQLITE(SQLDialect.SQLITE, "sqlite")
    ;
    private final SQLDialect dialect;
    private final String name;

    StorageType(final SQLDialect dialect, final String name) {
        this.dialect = dialect;
        this.name = name;
    }


    public SQLDialect getDialect() {
        return dialect;
    }

    public String getName() {
        return name;
    }
}
