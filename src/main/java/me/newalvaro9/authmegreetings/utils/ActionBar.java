package me.newalvaro9.authmegreetings.utils;
import me.newalvaro9.authmegreetings.AuthMeGreetings;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

// Adapted version of https://github.com/ConnorLinfoot/ActionBarAPI
public class ActionBar {

    private static Plugin plugin;

    public static void setPlugin(Plugin plugin) {
        ActionBar.plugin = plugin;
    }

    public static void sendActionBar(Player player, String message) {
        if (!player.isOnline()) {
            return;
        }

        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(message));
    }

    public static void sendActionBar(final Player player, final String message, int duration) {
        setPlugin(AuthMeGreetings.getPlugin());
        sendActionBar(player, message);

        if (duration >= 0) {
            // Sends empty message at the end of the duration. Allows messages shorter than 3 seconds, ensures precision.
            new BukkitRunnable() {
                @Override
                public void run() {
                    sendActionBar(player, "");
                }
            }.runTaskLater(plugin, duration + 1);
        }

        // Re-sends the messages every 3 seconds so it doesn't go away from the player's screen.
        int delay = 40;
        while (duration > delay) {
            duration -= delay;
            new BukkitRunnable() {
                @Override
                public void run() {
                    sendActionBar(player, message);
                }
            }.runTaskLater(plugin, duration);
        }
    }
}
