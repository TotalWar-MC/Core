package com.steffbeard.totalwar.utils;

import java.util.logging.Level;

import com.steffbeard.totalwar.modules.ore.HiddenOre;
import com.steffbeard.totalwar.modules.ore.config.Config;

public class Debug {

	public static void debug(String message, Object...replace) {
		if (Config.isDebug)
			HiddenOre.getPlugin().getLogger().log(Level.INFO, message, replace);
	}
	
}
