package me.newalvaro9.authmegreetings.listener;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    public Boolean isLeaveMessageEnabled;
    public String leaveMessage;

    public PlayerQuitListener (
            Boolean isLeaveMessageEnabled,
            String leaveMessage
    ) {
        this.isLeaveMessageEnabled = isLeaveMessageEnabled;
        this.leaveMessage = ChatColor.translateAlternateColorCodes('&', leaveMessage);;
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        if(isLeaveMessageEnabled) {
            Player player = event.getPlayer();
            Bukkit.broadcastMessage(leaveMessage.replace("%name%", player.getName()));
        }
    }
}