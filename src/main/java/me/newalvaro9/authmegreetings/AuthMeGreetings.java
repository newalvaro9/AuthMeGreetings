package me.newalvaro9.authmegreetings;
import me.newalvaro9.authmegreetings.listener.AuthMeListener;
import me.newalvaro9.authmegreetings.service.AuthMeHook;

import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;


public class AuthMeGreetings extends JavaPlugin {

    private Listener authMeListener;
    private AuthMeHook authMeHook;

    @Override
    public void onEnable() {
        final PluginManager pluginManager = getServer().getPluginManager();

        authMeHook = new AuthMeHook();

        if (pluginManager.isPluginEnabled("AuthMe")) {
            registerAuthMeComponents();
        }
        getLogger().info("Auth Me Greetings has been successfully enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("Unhooking from AuthMe");
        authMeHook.killAuthMeHook();
        getLogger().info("Disabling Auth Me Greetings");
    }

    /**
     * Activates the AuthMe hook and registers the AuthMe listener if not yet registered.
     * Call this method only when certain that AuthMe is enabled.
     */
    public void registerAuthMeComponents() {
        getLogger().info("Hooking into AuthMe");
        authMeHook.initializeAuthMeHook();
        if (authMeListener == null) {
            authMeListener = new AuthMeListener();
            getServer().getPluginManager().registerEvents(authMeListener, this);
        }
    }
}
