package com.github.sarhatabaot.kraken.core.extensions

import me.clip.placeholderapi.PlaceholderAPI
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import org.bukkit.ChatColor
import org.bukkit.entity.Player

/**
 *
 * @author sarhatabaot
 */

/**
 * Returns a colored string. From a legacy string.
 * For example: "&3Hello" -> ChatColor.RED + "Hello"
 */
fun String.color(): String  {
    if(this.contains("&")) {
        return ChatColor.translateAlternateColorCodes('&', this)
    }
    return this
}

fun String.miniMessage(): String {
    if (this.contains("<") || this.contains(">")) {
        val miniMessage = MiniMessage.miniMessage()
        return LegacyComponentSerializer.builder()
            .hexColors()
            .useUnusualXRepeatedCharacterHexFormat()
            .build().serialize(miniMessage.deserialize(this))
    }
    return this
}

fun String.papi(player: Player): String {
    if (PlaceholderAPI.containsPlaceholders(this)) {
        return PlaceholderAPI.setPlaceholders(player, this)
    }
    return this;
}
/**
 * Returns a formatted string, handles PlaceholderAPI placeholders,
 * minimessage formatting & color.
 */
fun String.format(player: Player): String = this.papi(player).miniMessage().color()

/**
 * Joins a list of components with a defined separator.
 */
fun Component.join(iterator: Iterator<Component>, separator: String): Component {
    if (!iterator.hasNext()) {
        return Component.text("")
    }

    var first: Component = iterator.next()
    if (!iterator.hasNext()) {
        return first
    }

    while (iterator.hasNext()) {
        first = first.append(Component.text(separator))
        val component: Component = iterator.next()
        first = first.append(component)
    }
    return first
}