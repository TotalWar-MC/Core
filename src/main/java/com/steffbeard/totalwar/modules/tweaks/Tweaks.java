package com.steffbeard.totalwar.modules.tweaks;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;

import com.steffbeard.totalwar.modules.tweaks.listeners.ArrowListener;
import com.steffbeard.totalwar.modules.tweaks.listeners.RailsListener;

import dev.siris.module.Module;

/**
 * 
 * Collection of tweaks to vanilla mechanics
 * 
 * @author Steffbeard
 */
public class Tweaks extends Module {
	
    public static List<String> worlds;
    public static boolean playerOnlyArrows;
	private static double speed_multiplier;

	@Override
    public void onEnable() {
        this.updateConfig();
        speed_multiplier = getConfig().getDouble("speedMultiplier");
        PluginManager pm = this.getServer().getPluginManager();
        pm.registerEvents(new RailsListener(), this);
        pm.registerEvents(new ArrowListener(), this);
        getLogger().info("> Tweaks loaded.");
    }

	@Override
    public void onDisable() {
       getLogger().info("> Tweaks unloaded");
    }

	/**
	 * 
	 * Need to make plugin.yml later
	 * 
	 * So far:
	 * 
	 * doing /tweaks reload 
	 * will reload
	 * 
	 * doing /rails (number)
	 * will change speed multiplier
	 * 
	 */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (command.getName().equalsIgnoreCase("tweaks") && args.length > 0 && args[0].equalsIgnoreCase("reload") && sender.hasPermission("tweaks.reload")) {
            this.updateConfig();
            sender.sendMessage("Reloaded config");
                return true;
            }
        
        	 
        if (command.getName().equalsIgnoreCase("rails") && sender instanceof Player) {
            Player player = (Player) sender;
            if (!player.hasPermission("tweaks.railscmd")) {
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
        	} else {
        		sender.sendMessage(ChatColor.RED + "Multiplier must be greater than 0 and max 50");
                return true;
        	}
    }
    
    /**
     * 
     * Get speed multiplier for rails
     * 
     * @return
     */
    public static double getMultiplier() {
        return speed_multiplier;
    }
    
    /**
     * 
     * Method for reloading config
     * 
     */
    public void updateConfig() {
        this.saveDefaultConfig();
        this.reloadConfig();
        Tweaks.worlds = (List<String>)this.getConfig().getStringList("worlds");
        Tweaks.playerOnlyArrows = this.getConfig().getBoolean("playerOnlyArrows");
        for (int i = 0; i < Tweaks.worlds.size(); ++i) {
            Tweaks.worlds.set(i, Tweaks.worlds.get(i).toLowerCase());
        }
    }
    
    /**
     * 
     * Checks to see if flame arrows are enabled in this world
     * 
     * @param world
     * @return
     */
    public static boolean canUseInWorld(final World world) {
        return Tweaks.worlds.size() == 0 || Tweaks.worlds.contains(world.getName().toLowerCase());
    }
}
