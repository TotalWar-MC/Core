package com.steffbeard.totalwar;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;

import com.steffbeard.totalwar.modules.crops.CropsMain;
import com.steffbeard.totalwar.modules.masslist.MassWhitelist;
import com.steffbeard.totalwar.modules.npc.NpcMain;
import com.steffbeard.totalwar.modules.tweaks.Tweaks;

import dev.siris.module.ModuleManager;

/**
 * 
 * Main plugin for TW:R
 * 
 * @version 1.0.0
 * @author Steffbeard, Konsyr, LeoDog, ProgrammerDan
 */

public class Core extends JavaPlugin {

	// Main
	public static Core plugin;
	public static Logger logger = Logger.getLogger("Core");
	public static Level minLogLevel = Level.INFO;
	private static ModuleManager moduleManager;
	
	/**
	 * Instance
	 */
	public Core() {
		plugin = this;
	}
	
	
	@Override
	public void onEnable() {
		System.out.println("====================      TW-CORE      ========================");
		
		// Create a module manager instance.
		moduleManager = new ModuleManager(this);
		
		// Tell the manager to load the class.
		moduleManager.loadModuleFromClass("MassWhitelist", MassWhitelist.class);
		moduleManager.loadModuleFromClass("Tweaks", Tweaks.class);
		moduleManager.loadModuleFromClass("Crops", CropsMain.class);
		moduleManager.loadModuleFromClass("NPCs", NpcMain.class);
		
		// Enables the modules
		moduleManager.enableModules();
	}
	
	@Override
	public void onDisable() {
		moduleManager.disableModules();
	}
	
	public void onReload() {
		this.onDisable();
		this.onEnable();
	}
	
	/**
	 * @return the instance
	 */
	public static Core getInstance() {
		return plugin;
	}
	
	/**
	 * 
	 * A good method to have when other classes need access to the module manager
	 * 
	 * @return ModuleManager
	 */
	public static ModuleManager getModuleManager() { 
		return moduleManager; 
	}
	
	/**********************************
	 *                                *
	 * This is all just logging stuff *
	 *                                *
	 **********************************/
	
	public static void severe(String message) {
	    logger.severe("[Core] " + message);
	}

	public static void warning(String message) {
	    logger.warning("[Core] " + message);
	}

	public static void info(String message) {
	    logger.info("[Core] " + message);
	}

//	public static void debug(String message) {
//	    if (config.getDebug()) {
//	      logger.info("[Core] " + message);
//	    }   
//	}
}
