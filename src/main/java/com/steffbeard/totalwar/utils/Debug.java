package com.steffbeard.totalwar.utils;

import java.util.logging.Level;

import com.steffbeard.totalwar.Core;
import com.steffbeard.totalwar.modules.ore.config.Config;

/**
 * 
 * Used for debugging
 * 
 * @author Steffbeard
 */
public class Debug {

	public static void debug(String message, Object...replace) {
		if (Config.isDebug)
			Core.getPlugin().getLogger().log(Level.INFO, message, replace);
	}
}
