package com.steffbeard.totalwar.modules.tweaks;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;

import com.steffbeard.totalwar.modules.tweaks.listeners.MinecartListener;

import dev.siris.module.Module;

/**
 * 
 * Collection of tweaks to vanilla mechanics
 * 
 * @author Steffbeard
 */
public class Tweaks extends Module {
	
	private static double speed_multiplier;

    public static double getMultiplier() {
        return speed_multiplier;
    }

	@Override
    public void onEnable() {
        saveDefaultConfig(); // copies default file to data folder, will not override existing file
        speed_multiplier = getConfig().getDouble("speedMultiplier");
        PluginManager pm = this.getServer().getPluginManager();
        pm.registerEvents(new MinecartListener(), this);
        getLogger().info("> Tweaks loaded.");
    }

	@Override
    public void onDisable() {
       getLogger().info("> Tweaks unloaded");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (command.getName().equalsIgnoreCase("rails")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (!player.hasPermission("rails.cmd")) {
                    player.sendMessage(ChatColor.RED + "You don't have permission to use this command");
                    return true;
                }
            }

            try {
                speed_multiplier = Double.parseDouble(args[0]);
            }
            catch (Exception ignore) {
                sender.sendMessage(ChatColor.RED + "Multiplier should be a number");
                return false;
            }

            if (speed_multiplier > 0 && speed_multiplier <= 50) {
                sender.sendMessage(ChatColor.AQUA + "Speed multiplier set to: " + speed_multiplier);
                return true;
            }

            sender.sendMessage(ChatColor.RED + "Multiplier must be greater than 0 and max 50");
            return true;
        }

        return false;
    }
}
