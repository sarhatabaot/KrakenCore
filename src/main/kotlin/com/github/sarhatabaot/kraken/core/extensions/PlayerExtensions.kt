package com.github.sarhatabaot.kraken.core.extensions

import org.bukkit.entity.Player


/**
 * @author sarhatabaot
 */

/**
 * Sends a colored message to a player.
 */
fun Player.sendColoredMessage(message: String) {
    this.sendMessage(message.color())
}

/**
 * Sends colored messages to a player.
 */
fun Player.sendColoredMessages(vararg messages: String) {
    messages.forEach { this.sendColoredMessage(it) }
}

/**
 * Sends a formatted message to a player.
 */
fun Player.sendFormattedMessage(message: String) {
    this.sendMessage(message.format(this))
}

/**
 * Sends formatted messages to a player.
 */
fun Player.sendFormattedMessages(vararg messages: String) {
    messages.forEach { this.sendFormattedMessage(it) }
}