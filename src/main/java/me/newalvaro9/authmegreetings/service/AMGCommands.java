package me.newalvaro9.authmegreetings.service;

import me.newalvaro9.authmegreetings.AuthMeGreetings;
import me.newalvaro9.authmegreetings.listener.AuthMeListener;
import me.newalvaro9.authmegreetings.listener.PlayerQuitListener;

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

                Boolean isPublicFirstJoinMessageEnabled = plugin.getConfig().getBoolean("first_join_welcome_message.enable_public_message");
                String publicFirstJoinMessage = plugin.getConfig().getString("first_join_welcome_message.public_message");
                Boolean isPrivateFirstJoinMessageEnabled = plugin.getConfig().getBoolean("first_join_welcome_message.enable_private_message");
                String privateFirstJoinMessage = plugin.getConfig().getString("first_join_welcome_message.private_message");

                Boolean isLeaveMessageEnabled = plugin.getConfig().getBoolean("leave_message.enable_leave_message");
                String leaveMessage = plugin.getConfig().getString("leave_message.message");

                Boolean isJoinTitleEnabled = plugin.getConfig().getBoolean("welcome_title.enable_title");
                String joinTitle = plugin.getConfig().getString("welcome_title.title");
                String joinTitleSubtitle = plugin.getConfig().getString("welcome_title.subtitle");
                Double joinTitleFadeIn = plugin.getConfig().getDouble("welcome_title.fadeIn");
                Double joinTitleStay = plugin.getConfig().getDouble("welcome_title.stay");
                Double joinTitleFadeOut = plugin.getConfig().getDouble("welcome_title.fadeOut");

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
                AuthMeListener newAuthMeListener = new AuthMeListener(
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

                // PlayerQuitListener
                PlayerQuitListener newPlayerQuitListener = new PlayerQuitListener(
                        isLeaveMessageEnabled,
                        leaveMessage
                );

                plugin.getServer().getPluginManager().registerEvents(newAuthMeListener, plugin);
                plugin.getServer().getPluginManager().registerEvents(newPlayerQuitListener, plugin);

                sender.sendMessage(ChatColor.GOLD + "Successfully reloaded AuthMeGreetings!");
                return true;
            }
        }
        if(args.length == 1 && args[0].equalsIgnoreCase("help")) {
            if(!sender.hasPermission("permission.admin")) {
                return false;
            }

            sender.sendMessage("\n" + "/amg reload - Reloads the plugin's configuration");

            return true;
        }
        return false;
    }
}