package com.github.sarhatabaot.kraken.core.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author sarhatabaot
 */
public class LoggerUtil {
    private static Logger logger = LoggerFactory.getLogger(LoggerUtil.class);

    static {
        if (logger.getName().equalsIgnoreCase("com.github.sarhatabaot.kraken.core.logging.LoggerUtil")) {
            logger.warn("Logger init method not called.");
        }
    }

    public static void init(Class<?> clazz) {
        LoggerUtil.logger = LoggerFactory.getLogger(clazz);
    }

    public static void logSevereException(Exception e) {
        logger.error(e.getMessage(),e);
    }

    public static void logWarningException(Exception e) {
        logger.warn(e.getMessage(),e);
    }

}
