package me.newalvaro9.authmegreetings;

import me.newalvaro9.authmegreetings.listener.AuthMeListener;
import me.newalvaro9.authmegreetings.service.AuthMeHook;
import me.newalvaro9.authmegreetings.service.AMGCommands;

import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class AuthMeGreetings extends JavaPlugin {

    private static AuthMeGreetings plugin;

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

    public Listener authMeListener;
    public AuthMeHook authMeHook;

    @Override
    public void onEnable() {
        plugin = this;

        final PluginManager pluginManager = getServer().getPluginManager();

        // Commands
        Objects.requireNonNull(getCommand("amg")).setExecutor(new AMGCommands(this));
        Objects.requireNonNull(getCommand("authmegreetings")).setExecutor(new AMGCommands(this));

        // BEFORE REGISTERING THE AUTHME COMPONENTS
        saveDefaultConfig();
        loadConfiguration();

        authMeHook = new AuthMeHook();

        if (pluginManager.isPluginEnabled("AuthMe")) {
            registerAuthMeComponents();
        }

        getLogger().info("AuthMe Greetings has been successfully enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("Unhooking from AuthMe");
        authMeHook.killAuthMeHook();
        getLogger().info("Disabling AuthMe Greetings");
    }

    /**
     * Activates the AuthMe hook and registers the AuthMe listener if not yet registered.
     * Call this method only when certain that AuthMe is enabled.
     */
    public void registerAuthMeComponents() {
        getLogger().info("Hooking into AuthMe");
        authMeHook.initializeAuthMeHook();
        if (authMeListener == null) {
            authMeListener = new AuthMeListener(
                    isPublicJoinMessageEnabled,
                    publicJoinMessage,
                    isPrivateJoinMessageEnabled,
                    privateJoinMessage,
                    isJoinTitleEnabled,
                    joinTitle,
                    joinTitleSubtitle,
                    joinTitleFadeIn,
                    joinTitleStay,
                    joinTitleFadeOut,
                    isJoinSoundEnabled,
                    joinSound,
                    joinSoundVolume,
                    joinSoundPitch
            );
            getServer().getPluginManager().registerEvents(authMeListener, this);
        }
    }

    public void loadConfiguration() {
        reloadConfig();

        if (getConfig().contains("welcome_message")) {
            isPublicJoinMessageEnabled = getConfig().getBoolean("welcome_message.enable_public_message");
            isPrivateJoinMessageEnabled = getConfig().getBoolean("welcome_message.enable_private_message");
            publicJoinMessage = getConfig().getString("welcome_message.public_message");
            privateJoinMessage = getConfig().getString("welcome_message.private_message");
        }

        if (getConfig().contains("welcome_title")) {
            isJoinTitleEnabled = getConfig().getBoolean("welcome_title.enable_title");
            joinTitle = getConfig().getString("welcome_title.title");
            joinTitleSubtitle = getConfig().getString("welcome_title.subtitle");
            joinTitleFadeIn = getConfig().getInt("welcome_title.fadeIn");
            joinTitleStay = getConfig().getInt("welcome_title.stay");
            joinTitleFadeOut = getConfig().getInt("welcome_title.fadeOut");
        }

        if(getConfig().contains("welcome_sound")) {
            isJoinSoundEnabled = getConfig().getBoolean("welcome_sound.enabled_sound");
            joinSound = getConfig().getString("welcome_sound.sound");
            joinSoundVolume = (float) getConfig().getDouble("welcome_sound.volume");
            joinSoundPitch = (float) getConfig().getDouble("welcome_sound.pitch");
        }
    }
}