package com.steffbeard.totalwar;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.block.Biome;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.steffbeard.totalwar.modules.crops.GrowthMap;
import com.steffbeard.totalwar.modules.crops.Main;
import com.steffbeard.totalwar.modules.crops.PersistConfig;
import com.steffbeard.totalwar.modules.crops.persist.BlockGrower;
import com.steffbeard.totalwar.modules.crops.persist.PlantManager;
import com.steffbeard.totalwar.modules.npc.cargo.CargoTrait;

import net.countercraft.movecraft.craft.CraftManager;

/**
 * 
 * Main plugin for TW:R
 * 
 * @version 2.0.0
 * @author Steffbeard, Konsyr, LeoDog, ProgrammerDan
 */

public class Core extends JavaPlugin {

	// Main
	public static Core plugin;
	public  config;
	public static Logger LOG = null;
	public static Level minLogLevel = Level.INFO;
	private String version = "2.0.0";
	
	// Movecraft
	private CraftManager craftManager;
	
	// Crops
	public HashMap<String, List<Biome>> biomeAliases;
	public GrowthMap materialGrowth;
	public GrowthMap fishSpawn;
	public boolean replaceFish;
	public double fishXPChance;
	public boolean allowTallPlantReplication;
	public BlockGrower blockGrower;
	public PersistConfig persistConfig;
	private PlantManager plantManager;
	
	/**
	 * Instance
	 */
	public Core() {
		plugin = this;
	}
	
	@Override
	public void onEnable() {
		System.out.println("====================      TW-CORE      ========================");

		version = this.getDescription().getVersion();
		

		// ************************
		// * Load Movecraft *
		// ************************
		if (getServer().getPluginManager().getPlugin("Movecraft") == null
				|| !getServer().getPluginManager().getPlugin("Movecraft").isEnabled()) {
			LOG.log(Level.SEVERE, "Movecraft not found or not enabled");
			getServer().getPluginManager().disablePlugin(this);
			return;
		}
		craftManager = CraftManager.getInstance();
		
		// ************************
		// * Load Citizens *
		// ************************
		if (getServer().getPluginManager().getPlugin("Citizens") == null
				|| !getServer().getPluginManager().getPlugin("Citizens").isEnabled()) {
			LOG.log(Level.SEVERE, "Citizens 2.0 not found or not enabled");
			getServer().getPluginManager().disablePlugin(this);
			return;
		}
		if (net.citizensnpcs.api.CitizensAPI.getTraitFactory().getTrait(CargoTrait.class) == null)
			net.citizensnpcs.api.CitizensAPI.getTraitFactory()
					.registerTrait(net.citizensnpcs.api.trait.TraitInfo.create(CargoTrait.class));
		}
	
	@Override
	public void onDisable() {
		
	}
	
	public void onReload() {
		this.onDisable();
		this.onEnable();
	}
	
	/**
	 * Hack to get around the shitty java logging api, where -Djava.util.logging.config=logging.properties
	 * doesn't seem to work, or i'm not specifying the right levels for the right Logger namespaces or some 
	 * stuff. You specify the min logging level allowed in the realistic biomes config.yml, with the 'minLogLevel' 
	 * key, specifed to a string. Either a Level name like INFO, or an integer
	 * 
	 * This requires that the static Logger variable in RealisticBiomes has already been created (aka onEnable has been called)
	 * @param level
	 * @param message
	 */
	public static void doLog(Level level, String message) {
		
		
		if (Main.LOG != null) {
			
			// here we make sure that we only log messages that are loggable with the given Level
			// so if its set to INFO (800) and we try to log a FINER message (400), then it wont work
			// However if its ALL, then its set to Integer.MIN_VALUE, so everything will get logged. etc etc
			if (level.intValue() >= Main.minLogLevel.intValue() ) {
				Main.LOG.info("[" + level.toString() + "] " + message);
				
			}	
		}
	}
	

	public static void doLog(Level level, String message, Throwable wrong) {
		
		if (Main.LOG != null) {
			
			// here we make sure that we only log messages that are loggable with the given Level
			// so if its set to INFO (800) and we try to log a FINER message (400), then it wont work
			// However if its ALL, then its set to Integer.MIN_VALUE, so everything will get logged. etc etc
			if (level.intValue() >= Main.minLogLevel.intValue() ) {
				Main.LOG.log(level, "[" + level.toString() + "] " + message, wrong);
				
			}	
		}
	}
	
	/**
	 * @return the instance
	 */
	public static Core getInstance() {
		return plugin;
	}
}
