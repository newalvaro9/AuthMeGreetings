package me.newalvaro9.authmegreetings.listener;

import me.newalvaro9.authmegreetings.utils.ActionBar;
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

    // first_join_welcome_message
    public Boolean isPublicFirstJoinMessageEnabled;
    public String publicFirstJoinMessage;
    public Boolean isPrivateFirstJoinMessageEnabled;
    public String privateFirstJoinMessage;

    // welcome_title
    public Boolean isJoinTitleEnabled;
    public String joinTitle;
    public String joinTitleSubtitle;
    public Double joinTitleFadeIn;
    public Double joinTitleStay;
    public Double joinTitleFadeOut;

    // welcome_sound
    public Boolean isJoinSoundEnabled;
    public String joinSound;
    public Float joinSoundVolume;
    public Float joinSoundPitch;

    // welcome_actionbar
    public Boolean isJoinActionBarEnabled;
    public String joinActionBarMessage;
    public Double joinActionBarDuration;

    public AuthMeListener(
            Boolean isPublicJoinMessageEnabled,
            String publicJoinMessage,
            Boolean isPrivateJoinMessageEnabled,
            String privateJoinMessage,
            Boolean isPublicFirstJoinMessageEnabled,
            String publicFirstJoinMessage,
            Boolean isPrivateFirstJoinMessageEnabled,
            String privateFirstJoinMessage,
            Boolean isJoinTitleEnabled,
            String joinTitle,
            String joinTitleSubtitle,
            Double joinTitleFadeIn,
            Double joinTitleStay,
            Double joinTitleFadeOut,
            Boolean isJoinSoundEnabled,
            String joinSound,
            Float joinSoundVolume,
            Float joinSoundPitch,
            Boolean isJoinActionBarEnabled,
            String joinActionBarMessage,
            Double joinActionBarDuration
    ) {
        // welcome_message
        this.isPublicJoinMessageEnabled = isPublicJoinMessageEnabled;
        this.publicJoinMessage = ChatColor.translateAlternateColorCodes('&', publicJoinMessage);
        this.isPrivateJoinMessageEnabled = isPrivateJoinMessageEnabled;
        this.privateJoinMessage = ChatColor.translateAlternateColorCodes('&', privateJoinMessage);

        // first_join_welcome_message
        this.isPublicFirstJoinMessageEnabled = isPublicFirstJoinMessageEnabled;
        this.publicFirstJoinMessage = publicFirstJoinMessage;
        this.isPrivateFirstJoinMessageEnabled = isPrivateFirstJoinMessageEnabled;
        this.privateFirstJoinMessage = privateFirstJoinMessage;

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

        // welcome_actionbar
        this.isJoinActionBarEnabled = isJoinActionBarEnabled;
        this.joinActionBarMessage = ChatColor.translateAlternateColorCodes('&', joinActionBarMessage);
        this.joinActionBarDuration = joinActionBarDuration;
    }

    @EventHandler
    public void onLogin(LoginEvent event) {
        Player player = event.getPlayer();

        if(player.hasPlayedBefore()) {
            if (isPublicJoinMessageEnabled) {
                Bukkit.broadcastMessage(publicJoinMessage.replace("%name%", player.getName()));
            }
            if (isPrivateJoinMessageEnabled) {
                player.sendMessage(privateJoinMessage.replace("%name%", player.getName()));
            }
        } else {
            if(isPublicFirstJoinMessageEnabled) {
                Bukkit.broadcastMessage(publicFirstJoinMessage.replace("%name%", player.getName()));
            }
            if(isPrivateFirstJoinMessageEnabled) {
                player.sendMessage(privateFirstJoinMessage.replace("%name%", player.getName()));
            }
        }

        if (isJoinTitleEnabled) {
            player.sendTitle(
                    joinTitle.replace("%name%", player.getName()),
                    Objects.equals(joinTitleSubtitle, "") ? null : joinTitleSubtitle.replace("%name%", player.getName()),
                    (int) (joinTitleFadeIn * 20),
                    (int) (joinTitleStay * 20),
                    (int) (joinTitleFadeOut * 20)
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
        if(isJoinActionBarEnabled) {
            ActionBar.sendActionBar(
                    player,
                    joinActionBarMessage.replace("%name%", player.getName()),
                    (int) (joinActionBarDuration * 20)
            );
        }
    }
}