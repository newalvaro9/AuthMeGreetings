package me.newalvaro9.authmegreetings.service;

import me.newalvaro9.authmegreetings.AuthMeGreetings;
import me.newalvaro9.authmegreetings.listener.AuthMeListener;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.event.HandlerList;

public class AMGCommands implements CommandExecutor {

    private final AuthMeGreetings plugin;

    public AMGCommands(AuthMeGreetings plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("authmegreetings") || cmd.getName().equalsIgnoreCase("amg")) {
            /* RELOAD COMMAND */
            if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {

                // PERMISSION CHECKS

                plugin.reloadConfig();

                // AuthMeHook
                plugin.authMeHook.killAuthMeHook();
                plugin.authMeHook = new AuthMeHook();


                // Get new config
                Boolean isPublicJoinMessageEnabled = plugin.getConfig().getBoolean("welcome_message.enable_public_message");
                Boolean isPrivateJoinMessageEnabled = plugin.getConfig().getBoolean("welcome_message.enable_private_message");
                String publicJoinMessage = plugin.getConfig().getString("welcome_message.public_message");
                String privateJoinMessage = plugin.getConfig().getString("welcome_message.private_message");

                Boolean isJoinTitleEnabled = plugin.getConfig().getBoolean("welcome_title.enable_title");
                String joinTitle = plugin.getConfig().getString("welcome_title.title");
                String joinTitleSubtitle = plugin.getConfig().getString("welcome_title.subtitle");
                Integer joinTitleFadeIn = plugin.getConfig().getInt("welcome_title.fadeIn");
                Integer joinTitleStay = plugin.getConfig().getInt("welcome_title.stay");
                Integer joinTitleFadeOut = plugin.getConfig().getInt("welcome_title.fadeOut");

                // Unregister all the listeners and set new ones with new config
                HandlerList.unregisterAll(plugin);

                // AuthMeListener
                AuthMeListener newListener = new AuthMeListener(
                        isPublicJoinMessageEnabled,
                        publicJoinMessage,
                        isPrivateJoinMessageEnabled,
                        privateJoinMessage,
                        isJoinTitleEnabled,
                        joinTitle,
                        joinTitleSubtitle,
                        joinTitleFadeIn,
                        joinTitleStay,
                        joinTitleFadeOut
                );
                plugin.getServer().getPluginManager().registerEvents(newListener, plugin);

                sender.sendMessage(ChatColor.GOLD + "Successfully reloaded AuthMeGreetings!");
                return true;
            }
        }
        return false;
    }
}