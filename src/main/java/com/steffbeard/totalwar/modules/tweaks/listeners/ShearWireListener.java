package com.steffbeard.totalwar.modules.tweaks.listeners;

import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Sheep;
import org.bukkit.entity.Vehicle;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.vehicle.VehicleDestroyEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Wool;

public class ShearWireListener implements Listener {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onWireTrip(EntityInteractEvent event) {
		Entity entity = event.getEntity();
		Block block = event.getBlock();
		if (entity.getType() == EntityType.SHEEP && block.getType() == Material.TRIPWIRE) {
			Sheep sheep = (Sheep) entity;
			Location loc = sheep.getLocation();
			DyeColor color = sheep.getColor();
			ItemStack item = new ItemStack(new Wool(color).getItemType());
			int numWool = (int)(Math.random() * 3) + 1;
			item.setAmount(numWool);
			loc.getWorld().dropItemNaturally(loc, item);
			sheep.setSheared(true);
		}
	}
}
