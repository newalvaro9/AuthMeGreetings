package me.newalvaro9.authmegreetings.listener;

import fr.xephi.authme.events.LoginEvent;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class AuthMeListener implements Listener {

    public Boolean isPublicJoinMessageEnabled;
    public String publicJoinMessage;

    public Boolean isPrivateJoinMessageEnabled;
    public String privateJoinMessage;

    public AuthMeListener(Boolean isPublicJoinMessageEnabled, String publicJoinMessage, Boolean isPrivateJoinMessageEnabled, String privateJoinMessage) {
        this.isPublicJoinMessageEnabled = isPublicJoinMessageEnabled;
        this.publicJoinMessage = ChatColor.translateAlternateColorCodes('&', publicJoinMessage);

        this.isPrivateJoinMessageEnabled = isPrivateJoinMessageEnabled;
        this.privateJoinMessage = ChatColor.translateAlternateColorCodes('&', privateJoinMessage);
    }

    @EventHandler
    public void onLogin(LoginEvent event) {
        if(isPublicJoinMessageEnabled) {
            Player player = event.getPlayer();
            Bukkit.broadcastMessage(publicJoinMessage.replace("%name%", player.getName()));
        }
        if(isPrivateJoinMessageEnabled) {
            Player player = event.getPlayer();
            player.sendMessage(privateJoinMessage.replace("%name%", player.getName()));
        }
    }
}