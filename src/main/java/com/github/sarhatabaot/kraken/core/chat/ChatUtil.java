package com.github.sarhatabaot.kraken.core.chat;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

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
}
