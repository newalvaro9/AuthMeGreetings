package me.newalvaro9.authmegreetings;
import me.newalvaro9.authmegreetings.listener.AuthMeListener;
import me.newalvaro9.authmegreetings.service.AuthMeHook;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class AuthMeGreetings extends JavaPlugin {

    private static AuthMeGreetings plugin;

    public Boolean isPublicJoinMessageEnabled;
    public String publicJoinMessage;

    public Boolean isPrivateJoinMessageEnabled;
    public String privateJoinMessage;

    public Listener authMeListener;
    public AuthMeHook authMeHook;

    @Override
    public void onEnable() {
        plugin = this;

        final PluginManager pluginManager = getServer().getPluginManager();

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
            authMeListener = new AuthMeListener(isPublicJoinMessageEnabled, publicJoinMessage, isPrivateJoinMessageEnabled, privateJoinMessage);
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
        } else {
            throw new IllegalArgumentException("Missing welcome_message properties [AuthMeGreetings/config.yml].");
        }
    }
}