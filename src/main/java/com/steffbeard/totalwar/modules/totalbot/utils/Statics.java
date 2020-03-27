package com.steffbeard.totalwar.modules.totalbot.utils;

/*
 * Remember to not commit this file because contains sensitive information
 */

public class Statics {
	
	// MySQL stuff
	public static String PORT = "3306";
	public static String HOST = "totalwaradmin.tk";
	public static String DATABASE = "totalbot";
	public static String USERNAME = "totalbot";
	public static String PASSWORD = "";
	
	// Set the command prefix 
	public static String PREFIX = ".";
	
	// Channel IDs for logging stuff
	public static String LOG_CHANNEL = "604739405143343114";
	public static String MOD_CHANNEL = "";
	
	// Permission utils
	public static final String[] FULLPERMS = {"Owner", "Admin", "Moderator", "Developer", "Jr. Dev"};
    public static final String[] PERMS = {"Bot Owner"};

	// Command utils
	public class CMDTYPE {
        public static final String moderation = "Moderation";
        public static final String information = "Information";
        public static final String etc = "Misc";
        public static final String music = "Music";
    }

}
