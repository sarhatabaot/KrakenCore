package com.github.sarhatabaot.kraken.core.extensions

import org.slf4j.Logger
import java.util.function.Supplier

/**
 *
 * @author sarhatabaot
 */

/**
 * Logs a severe exception and its message
 */
fun Logger.logSevereException(exception: Exception) = this.error(exception.message, exception)

/**
 * Logs a warning exception and its message
 */
fun Logger.logWarningException(exception: Exception) = this.warn(exception.message, exception)

fun Logger.info(supplier: Supplier<String>) = this.info(supplier.get())

fun Logger.debug(message: String, prefix: String = "DEBUG", isDebug : Boolean = false) {
    if(isDebug)
        return this.info("$prefix $message")
}
