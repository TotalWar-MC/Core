package com.steffbeard.totalwar.configs;

/**
 * 
 * Configuration enums to ensure comments stay when saving and reloading configs
 * 
 * Also used to reorganize the config instead of having to write in YML.
 * 
 * Make sure the last enum has a ; at the end beforethe "private final" line.
 * 
 * @author Steffbeard
 */
public enum ConfigNodes {
	
	VERSION_HEADER("version", "", ""),
	VERSION(
			"version.version",
			"",
			"# This is the current version of Towny.  Please do not edit."),
	LAST_RUN_VERSION(
			"version.last_run_version",
			"",
			"# This is for showing the changelog on updates.  Please do not edit."),
	NPCS(
			"npcs",
			"",
			"",
			"############################################################",
			"# +------------------------------------------------------+ #",
			"# |                    NPC Type Config                   | #",
			"# +------------------------------------------------------+ #",
			"############################################################",
			""),
	ORES(
			"ores",
			"",
			"",
			"############################################################",
			"# +------------------------------------------------------+ #",
			"# |                 Ore Generation Config                | #",
			"# +------------------------------------------------------+ #",
			"############################################################",
			""),
	CROPS(
			"crops",
			"",
			"",
			"############################################################",
			"# +------------------------------------------------------+ #",
			"# |                 Crop Generation Config               | #",
			"# +------------------------------------------------------+ #",
			"############################################################",
			"");

	private final String Root;
	private final String Default;
	private String[] comments;

	ConfigNodes(String root, String def, String... comments) {

		this.Root = root;
		this.Default = def;
		this.comments = comments;
	}

	/**
	 * Retrieves the root for a config option
	 *
	 * @return The root for a config option
	 */
	public String getRoot() {

		return Root;
	}

	/**
	 * Retrieves the default value for a config path
	 *
	 * @return The default value for a config path
	 */
	public String getDefault() {

		return Default;
	}

	/**
	 * Retrieves the comment for a config path
	 *
	 * @return The comments for a config path
	 */
	public String[] getComments() {

		if (comments != null) {
			return comments;
		}

		String[] comments = new String[1];
		comments[0] = "";
		return comments;
	}
}
