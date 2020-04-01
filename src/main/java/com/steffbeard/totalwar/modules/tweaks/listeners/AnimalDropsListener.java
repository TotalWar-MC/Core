package com.steffbeard.totalwar.modules.tweaks.listeners;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Cow;
import org.bukkit.entity.Horse;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Llama;
import org.bukkit.entity.Pig;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;
import org.bukkit.entity.Wolf;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

/**
 * 
 * Handles custom mob drops from killing animals.
 * Makes getting bones easier and chance for other stuff like spider eyes.
 * 
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
	@SuppressWarnings("deprecation")
	@EventHandler
	public void animalDrops(EntityDeathEvent event) {
		LivingEntity e = event.getEntity();
		event.setDroppedExp(0);
		
		if(e instanceof Pig) {
			e.getLocation().getWorld().dropItem(e.getLocation(), new ItemStack(Material.BONE));
		} else if(e instanceof Cow) {
			e.getLocation().getWorld().dropItem(e.getLocation(), new ItemStack(Material.BONE));
		} else if(e instanceof Sheep) {
			e.getLocation().getWorld().dropItem(e.getLocation(), new ItemStack(Material.BONE));
		} else if(e instanceof Horse) {
			e.getLocation().getWorld().dropItem(e.getLocation(), new ItemStack(Material.BONE));
		} else if(e instanceof Wolf) {
			e.getLocation().getWorld().dropItem(e.getLocation(), new ItemStack(Material.BONE));
		} else if(e instanceof Llama) {
			e.getLocation().getWorld().dropItem(e.getLocation(), new ItemStack(Material.BONE));
		} else if(e instanceof Chicken) {
			e.getLocation().getWorld().dropItem(e.getLocation(), new ItemStack(Material.BONE));
		} else if(e instanceof Player) {
			// Got to define what a skull is
			ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
			SkullMeta meta = (SkullMeta) skull.getItemMeta();
			meta.setOwner(e.getName());
			meta.setDisplayName(ChatColor.YELLOW + e.getName() + "'s Head");
			skull.setItemMeta(meta);
			
			e.getLocation().getWorld().dropItem(e.getLocation(), new ItemStack(Material.BONE));
			e.getLocation().getWorld().dropItem(e.getLocation(), new ItemStack(Material.PORK));
			e.getLocation().getWorld().dropItem(e.getLocation(), skull);
		}
	}
}
