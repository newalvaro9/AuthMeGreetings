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

                if(!sender.hasPermission("permission.admin")) {
                    return false;
                }

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

                Boolean isJoinSoundEnabled = plugin.getConfig().getBoolean("welcome_sound.enabled_sound");
                String joinSound = plugin.getConfig().getString("welcome_sound.sound");
                Float joinSoundVolume = (float) plugin.getConfig().getDouble("welcome_sound.volume");
                Float joinSoundPitch = (float) plugin.getConfig().getDouble("welcome_sound.pitch");

                Boolean isJoinActionBarEnabled = plugin.getConfig().getBoolean("welcome_actionbar.enabled_actionbar");
                String joinActionBarMessage = plugin.getConfig().getString("welcome_actionbar.message");
                Double joinActionBarDuration = plugin.getConfig().getDouble("welcome_actionbar.duration");

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
                        joinTitleFadeOut,
                        isJoinSoundEnabled,
                        joinSound,
                        joinSoundVolume,
                        joinSoundPitch,
                        isJoinActionBarEnabled,
                        joinActionBarMessage,
                        joinActionBarDuration
                );
                plugin.getServer().getPluginManager().registerEvents(newListener, plugin);

                sender.sendMessage(ChatColor.GOLD + "Successfully reloaded AuthMeGreetings!");
                return true;
            }
        }
        return false;
    }
}