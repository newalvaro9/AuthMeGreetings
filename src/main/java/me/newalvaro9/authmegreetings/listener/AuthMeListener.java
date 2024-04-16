package me.newalvaro9.authmegreetings.listener;

import fr.xephi.authme.events.AuthMeAsyncPreLoginEvent;
import fr.xephi.authme.events.LoginEvent;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class AuthMeListener implements Listener {

    String joinMessage = ChatColor.translateAlternateColorCodes('&',
            "Welcome %name%");

    @EventHandler
    public void onLogin(LoginEvent event) {
        String message = joinMessage.replace("%name%", event.getPlayer().getName());
        event.getPlayer().sendMessage(message);
    }
}