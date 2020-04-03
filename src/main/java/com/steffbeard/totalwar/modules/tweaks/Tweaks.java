package com.steffbeard.totalwar.modules.tweaks;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import com.steffbeard.totalwar.modules.tweaks.handlers.BreedTask;
import com.steffbeard.totalwar.modules.tweaks.listeners.AnimalDropsListener;
import com.steffbeard.totalwar.modules.tweaks.listeners.ArrowListener;
import com.steffbeard.totalwar.modules.tweaks.listeners.BreedTaskListener;
import com.steffbeard.totalwar.modules.tweaks.listeners.RailsListener;
import com.steffbeard.totalwar.modules.tweaks.listeners.ShearWireListener;

import dev.siris.module.Module;

/**
 * 
 * Collection of tweaks to vanilla mechanics
 * 
 * @author Steffbeard
 */
public class Tweaks extends Module {
	
	private static Tweaks instance;
	public static FileConfiguration config;
	
	// Stuff for flame arrows
    public static List<String> worlds;
    public static boolean playerOnlyArrows;
	
    // Stuff for rails
    private static double speed_multiplier;
	
	// Stuff for animal breeding
	private Animal wildAnimalHandler;
    private int breedTask;
    private long startTime;
    private static int interval;
    private static boolean mateMode;
    private static double chance;
    private static double maxAnimalsPerBlock;
    private static double maxAnimalsCheckRadius;
    private static int maxMateDistance;
    public Set<Entity> lastMateAnimals;

    /**
     * 
     * Set the instance
     * 
     */
    @Override
    public void onLoad() {
    	instance = this;
    }
    
	@SuppressWarnings("deprecation")
	@Override
    public void onEnable() {
        this.lastMateAnimals = new HashSet<Entity>();
        
        this.updateConfig();
        this.loadListeners();
        
        getLogger().info("> Tweaks loaded.");

        this.breedTask = this.getServer().getScheduler().scheduleSyncRepeatingTask(this, new BreedTask(this), 0L, Tweaks.interval);
        this.startTime = System.currentTimeMillis();
    }

	@Override
    public void onDisable() {
       getLogger().info("> Tweaks unloaded");
       this.getServer().getScheduler().cancelTask(this.breedTask);
       HandlerList.unregisterAll(this);
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
    
    public void loadListeners() {
    	PluginManager pm = this.getServer().getPluginManager();
        pm.registerEvents(new RailsListener(), this);
        pm.registerEvents(new ArrowListener(), this);
        pm.registerEvents(new ShearWireListener(), this);
        pm.registerEvents(new BreedTaskListener(), this);
        pm.registerEvents(new AnimalDropsListener(), this);
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
    	Tweaks.interval = this.getConfig().getInt("interval") * 20 * 60;
        Tweaks.mateMode = this.getConfig().getBoolean("mateMode");
        Tweaks.chance = this.getConfig().getDouble("chance");
        Tweaks.maxAnimalsPerBlock = this.getConfig().getDouble("maxAnimalsPerBlock");
        Tweaks.maxAnimalsCheckRadius = this.getConfig().getDouble("maxAnimalsCheckRadius");
        Tweaks.maxMateDistance = this.getConfig().getInt("maxMateDistance");
        Tweaks.speed_multiplier = this.getConfig().getDouble("speedMultiplier");
        Tweaks.worlds = (List<String>)this.getConfig().getStringList("worlds");
        Tweaks.playerOnlyArrows = this.getConfig().getBoolean("playerOnlyArrows");
        for (int i = 0; i < Tweaks.worlds.size(); ++i) {
            Tweaks.worlds.set(i, Tweaks.worlds.get(i).toLowerCase());
        }
    	this.saveDefaultConfig();
        this.reloadConfig();
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
    
    /**
     * 
     * Clears the animals that just mated
     * 
     */
    public void clearMatedAnimals() {
        this.lastMateAnimals.clear();
    }

    /**
     * 
     * Added an entity that just mated to a list
     * 
     * @param e
     */
    public void addMatedAnimal(Entity e) {
        this.lastMateAnimals.add(e);
    }

    /**
     * 
     * Get the handler for mating
     * 
     * @return
     */
    public Animal getWildAnimalHandler() {
        return wildAnimalHandler;
    }

    /**
     * 
     * Get the mate mode
     * 
     * @return
     */
    public boolean getMateMode() {
        return mateMode;
    }

    /**
     * 
     * Get the chance of mating
     * 
     * @return
     */
    public double getChance() {
        return chance;
    }

    /**
     * 
     * Get the maximum allowed animals per block
     * 
     * @return
     */
    public double getMaxAnimalsPerBlock() {
        return maxAnimalsPerBlock;
    }

    /**
     * 
     * Get maximum allowed animals in the radius
     * 
     * @return
     */
    public double getMaxAnimalsCheckRadius() {
        return maxAnimalsCheckRadius;
    }

    /**
     * 
     * Get the maximum distance that animals can mate from
     * 
     * @return
     */
    public int getMaxMateDistance() {
        return maxMateDistance;
    }
    
    /**
     * 
     * Instance
     * 
     * @return instance
     */
    public static Tweaks getInstance() {
    	return instance;
    }
}
