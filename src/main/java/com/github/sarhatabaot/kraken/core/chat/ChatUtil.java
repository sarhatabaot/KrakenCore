package com.github.sarhatabaot.kraken.core.chat;

import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;

/**
 * @author sarhatabaot
 */
public class ChatUtil {
    public static void sendMessage(final @NotNull CommandSender sender, final String @NotNull ... messages) {
        for(String message: messages) {
            sendMessage(sender,message);
        }
    }

    public static void sendMessage(final @NotNull CommandSender sender, final String message) {
        sender.sendMessage(color(message));
    }

    @Contract("_ -> new")
    public static @NotNull String color(final String string) {
        return ChatColor.translateAlternateColorCodes('&',string);
    }

    public static @NotNull String format(final Player player, final String text) {
        String finalText = text;
        if(PlaceholderAPI.containsPlaceholders(text)) {
            finalText = PlaceholderAPI.setPlaceholders(player, text);
        }

        //MiniMessage
        if(finalText.contains("<") || finalText.contains(">")) {
            MiniMessage miniMessage = MiniMessage.miniMessage();
            finalText = LegacyComponentSerializer.builder()
                    .hexColors()
                    .useUnusualXRepeatedCharacterHexFormat()
                    .build().serialize(miniMessage.deserialize(finalText));
        }

        //LegacyFormatting
        if(finalText.contains("&")) {
            finalText = ChatUtil.color(finalText);
        }
        return finalText;
    }


    public static Component join(Iterator<Component> iterator, String separator) {
        if (iterator == null) {
            return null;
        }

        if (!iterator.hasNext()) {
            return Component.text("");
        }

        Component first = iterator.next();
        if (!iterator.hasNext()) {
            return first;
        }

        while (iterator.hasNext()) {
            if (separator != null) {
                first = first.append(Component.text(separator));
            }

            Component component = iterator.next();
            if (component != null) {
                first = first.append(component);
            }
        }
        return first;
    }
}
