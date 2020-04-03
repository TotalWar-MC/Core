package com.steffbeard.totalwar.modules.carts.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Donkey;
import org.bukkit.entity.Horse;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Llama;
import org.bukkit.entity.Mule;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.vehicle.VehicleEnterEvent;
import org.bukkit.event.vehicle.VehicleExitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.steffbeard.totalwar.modules.carts.Cart;
import com.steffbeard.totalwar.modules.carts.utils.CartUtils;

/**
 * 
 * Listener for where the horse is and if the cart is tied up
 * 
 * @author Timelord#6631
 */
public class HorseListener implements Listener {
	
	/**
	 * 
	 * Listen for if a player mounts a horse.
	 * 
	 * @param event
	 */
	@EventHandler
	public void mountHorse(VehicleEnterEvent event) {

		if (Cart.config.getBoolean("debugMode")) {
			Bukkit.getServer().broadcastMessage("Mounted vehicle!");
		}
		
		if ((event.getVehicle() instanceof Horse) && (event.getEntered() instanceof Player)) {
			Horse horse = (Horse) event.getVehicle();
			
			if (Cart.config.getBoolean("debugMode")) {
				Bukkit.getServer().broadcastMessage("Mounted horse!");
			}
			
			if (horse.isLeashed()) {
				if (Cart.config.getBoolean("debugMode")) {
					Bukkit.getServer().broadcastMessage("Mounted leashed horse!");
				}
				
				if (CartUtils.isCart(horse) != null) {
					Cart.carts.add((Player) event.getEntered());
					
					if (Cart.config.getBoolean("debugMode")) {
						Bukkit.getServer().broadcastMessage("Mounted horse with cart!");
					}
					
					if (Cart.config.getBoolean("slowerCarts")) {
						((LivingEntity) event.getVehicle()).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 99999, 2));
					}
				}
			}
		}
	}
	
	/**
	 * 
	 * Handler for when a player logs off
	 * 
	 * @param event
	 */
	@EventHandler
	public void logOff(PlayerQuitEvent event) {
		Player p = event.getPlayer();
		
		if (Cart.carts.contains(p)) {
			Cart.carts.remove(p);
			if (Cart.config.getBoolean("slowerCarts")) {
				((LivingEntity) p.getVehicle()).removePotionEffect(PotionEffectType.SLOW);
			}
		}
		
		if (Cart.cartsCooldown.contains(p)) {
			Cart.cartsCooldown.remove(p);
			if (Cart.config.getBoolean("slowerCarts")) {
				((LivingEntity) p.getVehicle()).removePotionEffect(PotionEffectType.SLOW);
			}
		}
		
		if (Cart.chatCooldown.contains(p)) {
			Cart.chatCooldown.remove(p);
			if (Cart.config.getBoolean("slowerCarts")) {
				((LivingEntity) p.getVehicle()).removePotionEffect(PotionEffectType.SLOW);
			}
		}
	}
	
	/**
	 * 
	 * Handler for when a palyer is kicked
	 * 
	 * @param event
	 */
	@EventHandler
	public void kickOff(PlayerKickEvent event) {
		Player p = event.getPlayer();
		
		if (Cart.carts.contains(p)) {
			Cart.carts.remove(p);
			if (Cart.config.getBoolean("slowerCarts")) {
				((LivingEntity) p.getVehicle()).removePotionEffect(PotionEffectType.SLOW);
			}
		}
		
		if (Cart.cartsCooldown.contains(p)) {	
			Cart.cartsCooldown.remove(p);
			if (Cart.config.getBoolean("slowerCarts")) {
				((LivingEntity) p.getVehicle()).removePotionEffect(PotionEffectType.SLOW);
			}
		}
		
		if (Cart.chatCooldown.contains(p)) {
			Cart.chatCooldown.remove(p);
			if (Cart.config.getBoolean("slowerCarts")) {
				((LivingEntity) p.getVehicle()).removePotionEffect(PotionEffectType.SLOW);
			}
		}
	}
	
	/**
	 * 
	 * Handler for when a player gets off
	 * 
	 * @param event
	 */
	@EventHandler
	public void dismountHorse(VehicleExitEvent event) {
		if (((event.getVehicle() instanceof Horse) || 
				(event.getVehicle() instanceof Donkey) ||  
				(event.getVehicle() instanceof Mule) ||  
				(event.getVehicle() instanceof Llama)) 
				&& (event.getExited() instanceof Player)) {
			
			if (Cart.carts.contains((Player)event.getExited())) {
				Cart.carts.remove((Player)event.getExited());
				if (Cart.config.getBoolean("slowerCarts")) {
					((LivingEntity) event.getVehicle()).removePotionEffect(PotionEffectType.SLOW);
				}
			}
		}
	}
}