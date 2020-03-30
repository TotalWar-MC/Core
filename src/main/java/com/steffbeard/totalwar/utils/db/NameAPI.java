package com.steffbeard.totalwar.utils.db;

import java.util.Map;
import java.util.HashMap;
import java.util.UUID;

import com.steffbeard.totalwar.Core;

public class NameAPI {
	private static AssociationList associations;
	
	private static Map<UUID, String> uuidsToName = new HashMap<UUID, String>();
	private static Map<String, UUID> nameToUUIDS = new HashMap<String, UUID>();
	
	public NameAPI(AssociationList ass){
		associations =  ass;
		loadAllPlayerInfo();
	}
	
	public void loadAllPlayerInfo() {
		uuidsToName.clear();
		nameToUUIDS.clear();
		
		boolean load = Core.getInstance().getConfig().getBoolean("persistance.forceloadnamecaching", false);
		if (!load)
			return;
		AssociationList.PlayerMappingInfo pmi = associations.getAllPlayerInfo();
		nameToUUIDS = pmi.nameMapping;
		uuidsToName = pmi.uuidMapping;
		
	}
	
	public static void resetCache(UUID uuid) {
		String name = getCurrentName(uuid);
		uuidsToName.remove(uuid);
		nameToUUIDS.remove(name);
	}
	/**
	 * Returns the UUID of the player on the given server.
	 * @param playerName The playername.
	 * @return Returns the UUID of the player.
	 */
	public static UUID getUUID(String playerName) {
		UUID uuid = nameToUUIDS.get(playerName);
		if (uuid == null){
			uuid = associations.getUUID(playerName);
			nameToUUIDS.put(playerName, uuid);
		}
		return uuid;
	}
	/**
	 * Gets the playername from a given server from their uuid.
	 * @param uuid.
	 * @return Returns the PlayerName from the UUID.
	 */
	public static String getCurrentName(UUID uuid) {
		String name = uuidsToName.get(uuid);
		if (name == null){
			name = associations.getCurrentName(uuid);
			uuidsToName.put(uuid, name);
		}
		return name;
	}
	/**
	 * @return Returns an instance of the AssociationList.
	 */
	public static AssociationList getAssociationList() {
		return associations;
	}
}