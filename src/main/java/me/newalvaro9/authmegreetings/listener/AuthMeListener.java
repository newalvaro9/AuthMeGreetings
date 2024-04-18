package me.newalvaro9.authmegreetings.listener;

import fr.xephi.authme.events.LoginEvent;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.Objects;

public class AuthMeListener implements Listener {

    // welcome_message
    public Boolean isPublicJoinMessageEnabled;
    public String publicJoinMessage;
    public Boolean isPrivateJoinMessageEnabled;
    public String privateJoinMessage;

    // welcome_title
    public Boolean isJoinTitleEnabled;
    public String joinTitle;
    public String joinTitleSubtitle;
    public Integer joinTitleFadeIn;
    public Integer joinTitleStay;
    public Integer joinTitleFadeOut;

    // welcome_sound
    public Boolean isJoinSoundEnabled;
    public String joinSound;
    public Float joinSoundVolume;
    public Float joinSoundPitch;

    public AuthMeListener(
            Boolean isPublicJoinMessageEnabled,
            String publicJoinMessage,
            Boolean isPrivateJoinMessageEnabled,
            String privateJoinMessage,
            Boolean isJoinTitleEnabled,
            String joinTitle,
            String joinTitleSubtitle,
            Integer joinTitleFadeIn,
            Integer joinTitleStay,
            Integer joinTitleFadeOut,
            Boolean isJoinSoundEnabled,
            String joinSound,
            Float joinSoundVolume,
            Float joinSoundPitch
    ) {
        // welcome_message
        this.isPublicJoinMessageEnabled = isPublicJoinMessageEnabled;
        this.publicJoinMessage = ChatColor.translateAlternateColorCodes('&', publicJoinMessage);
        this.isPrivateJoinMessageEnabled = isPrivateJoinMessageEnabled;
        this.privateJoinMessage = ChatColor.translateAlternateColorCodes('&', privateJoinMessage);

        // welcome_title
        this.isJoinTitleEnabled = isJoinTitleEnabled;
        this.joinTitle = ChatColor.translateAlternateColorCodes('&', joinTitle);
        this.joinTitleSubtitle = ChatColor.translateAlternateColorCodes('&', joinTitleSubtitle);
        this.joinTitleFadeIn = joinTitleFadeIn;
        this.joinTitleStay = joinTitleStay;
        this.joinTitleFadeOut = joinTitleFadeOut;

        // welcome_sound
        this.isJoinSoundEnabled = isJoinSoundEnabled;
        this.joinSound = joinSound;
        this.joinSoundVolume = joinSoundVolume;
        this.joinSoundPitch = joinSoundPitch;
    }



    @EventHandler
    public void onLogin(LoginEvent event) {
        Player player = event.getPlayer();

        if(isPublicJoinMessageEnabled) {
            Bukkit.broadcastMessage(publicJoinMessage.replace("%name%", player.getName()));
        }
        if(isPrivateJoinMessageEnabled) {
            player.sendMessage(privateJoinMessage.replace("%name%", player.getName()));
        }
        if(isJoinTitleEnabled) {
            player.sendTitle(
                    joinTitle.replace("%name%", player.getName()),
                    Objects.equals(joinTitleSubtitle, "") ? null : joinTitleSubtitle.replace("%name%", player.getName()),
                    joinTitleFadeIn,
                    joinTitleStay,
                    joinTitleFadeOut
            );
        }
        if(isJoinSoundEnabled) {
            player.playSound(
                    player.getLocation(),
                    Sound.valueOf(joinSound.toUpperCase()),
                    joinSoundVolume,
                    joinSoundPitch
            );
        }

    }
}