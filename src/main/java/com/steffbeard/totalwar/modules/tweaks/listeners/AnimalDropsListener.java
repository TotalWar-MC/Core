package com.steffbeard.totalwar.modules.tweaks.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Cow;
import org.bukkit.entity.Horse;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Llama;
import org.bukkit.entity.Pig;
import org.bukkit.entity.Sheep;
import org.bukkit.entity.Wolf;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

/**
 * 
 * Handles custom mob drops from killing animals
 * Makes getting bones easier and chance for other stuff like spider eyes
 * 
 * @author Steffbeard
 *
 */
public class AnimalDropsListener implements Listener {
	
	/**
	 * 
	 * Here's where we actually define the drops.
	 * Also changing dropped XP to 0 for all mobs.
	 * No XP-enchanting so lets cut down unneccessary entities if we can
	 * 
	 * @param event
	 */
	@EventHandler
	public void animalDrops(EntityDeathEvent event) {
		LivingEntity e = event.getEntity();
		event.setDroppedExp(0);
		
		if(e instanceof Pig) {
			e.getLocation().getWorld().dropItem(e.getLocation(), new ItemStack(Material.BONE));
		}
		
		if(e instanceof Cow) {
			e.getLocation().getWorld().dropItem(e.getLocation(), new ItemStack(Material.BONE));
		}
		
		if(e instanceof Sheep) {
			e.getLocation().getWorld().dropItem(e.getLocation(), new ItemStack(Material.BONE));
		}
		
		if(e instanceof Horse) {
			e.getLocation().getWorld().dropItem(e.getLocation(), new ItemStack(Material.BONE));
		}
		
		if(e instanceof Wolf) {
			e.getLocation().getWorld().dropItem(e.getLocation(), new ItemStack(Material.BONE));
		}
		
		if(e instanceof Llama) {
			e.getLocation().getWorld().dropItem(e.getLocation(), new ItemStack(Material.BONE));
		}
		
		if(e instanceof Chicken) {
			e.getLocation().getWorld().dropItem(e.getLocation(), new ItemStack(Material.BONE));
		}
	}
}
