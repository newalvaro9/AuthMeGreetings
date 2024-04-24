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
        getLogger().info("\n"+
                "    ___     __  ___ ______\n" +
                "   /   |   /  |/  // ____/\n" +
                "  / /| |  / /|_/ // / __  \n" +
                " / ___ | / /  / // /_/ /  \n" +
                "/_/  |_|/_/  /_/ \\____/");

        getLogger().info("AuthMe Greetings has been successfully enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("Disabling AuthMe Greetings...");
        getLogger().info("Unhooking from AuthMe");
        authMeHook.killAuthMeHook();
        getLogger().info("AuthMe Greetings has been successfully disabled!");
    }

    /**
     * Activates the AuthMe hook and registers the AuthMe listener if not yet registered.
     * Call this method only when certain that AuthMe is enabled.
     */
    public void registerAuthMeComponents() {
        getLogger().info("Hooking into AuthMe...");
        try {
            authMeHook.initializeAuthMeHook();
            if (authMeListener == null) {
                authMeListener = new AuthMeListener(
                        isPublicJoinMessageEnabled,
                        publicJoinMessage,
                        isPrivateJoinMessageEnabled,
                        privateJoinMessage,
                        isPublicFirstJoinMessageEnabled,
                        publicFirstJoinMessage,
                        isPrivateFirstJoinMessageEnabled,
                        privateFirstJoinMessage,
                        isJoinTitleEnabled,
                        joinTitle,
                        joinTitleSubtitle,
                        joinTitleFadeIn,
                        joinTitleStay,
                        joinTitleFadeOut,
                        isJoinSoundEnabled,
                        joinSound,
                        joinSoundVolume,
                        joinSoundPitch,
                        isJoinActionBarEnabled,
                        joinActionBarMessage,
                        joinActionBarDuration
                );
                getServer().getPluginManager().registerEvents(authMeListener, this);
                getLogger().info("Successfully hooked into AuthMe!");
            }
        } catch (Exception e) {
            getLogger().severe("An error occurred while hooking into AuthMe: " + e.getMessage());
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

        if (getConfig().contains("first_join_welcome_message")) {
            isPublicFirstJoinMessageEnabled = getConfig().getBoolean("first_join_welcome_message.enable_public_message");
            isPrivateFirstJoinMessageEnabled = getConfig().getBoolean("first_join_welcome_message.enable_private_message");
            publicFirstJoinMessage = getConfig().getString("first_join_welcome_message.public_message");
            privateFirstJoinMessage = getConfig().getString("first_join_welcome_message.private_message");
        }

        if (getConfig().contains("welcome_title")) {
            isJoinTitleEnabled = getConfig().getBoolean("welcome_title.enable_title");
            joinTitle = getConfig().getString("welcome_title.title");
            joinTitleSubtitle = getConfig().getString("welcome_title.subtitle");
            joinTitleFadeIn = getConfig().getDouble("welcome_title.fadeIn");
            joinTitleStay = getConfig().getDouble("welcome_title.stay");
            joinTitleFadeOut = getConfig().getDouble("welcome_title.fadeOut");
        }

        if(getConfig().contains("welcome_sound")) {
            isJoinSoundEnabled = getConfig().getBoolean("welcome_sound.enabled_sound");
            joinSound = getConfig().getString("welcome_sound.sound");
            joinSoundVolume = (float) getConfig().getDouble("welcome_sound.volume");
            joinSoundPitch = (float) getConfig().getDouble("welcome_sound.pitch");
        }

        if(getConfig().contains("welcome_actionbar")) {
            isJoinActionBarEnabled = getConfig().getBoolean("welcome_actionbar.enabled_actionbar");
            joinActionBarMessage = getConfig().getString("welcome_actionbar.message");
            joinActionBarDuration = getConfig().getDouble("welcome_actionbar.duration");
        }
    }

    public static AuthMeGreetings getPlugin() {
        return plugin;
    }
}