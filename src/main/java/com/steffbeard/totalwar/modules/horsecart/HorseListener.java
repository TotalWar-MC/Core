package com.steffbeard.totalwar.modules.horsecart;

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

/**
 * 
 * Listener for where the horse is and if the cart is tied up
 * 
 * @author Timelord#6631
 */
public class HorseListener implements Listener{
	
	public HorseListener(HorseCart plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void mountHorse(VehicleEnterEvent event) {
		//Bukkit.getServer().broadcastMessage("Mounted vehicle!");
		if ((event.getVehicle() instanceof Horse) && (event.getEntered() instanceof Player))
		{
			//Bukkit.getServer().broadcastMessage("Mounted horse!");
			Horse horse = (Horse) event.getVehicle();
			if (horse.isLeashed())
			{
				//Bukkit.getServer().broadcastMessage("Mounted leashed horse!");
				if (CartUtils.isCart(horse) != null)
				{
					//Bukkit.getServer().broadcastMessage("Mounted horse with cart!");
					HorseCart.carts.add((Player) event.getEntered());
					
					if (HorseCart.config.getBoolean("slowerCarts"))
					{
						((LivingEntity) event.getVehicle()).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 99999, 2));
					}
				}
			}
		}
	}
	
	public void logOff(PlayerQuitEvent event) {
		Player p = event.getPlayer();
		if (HorseCart.carts.contains(p))
		{
			HorseCart.carts.remove(p);
			if (HorseCart.config.getBoolean("slowerCarts"))
			{
				((LivingEntity) p.getVehicle()).removePotionEffect(PotionEffectType.SLOW);
			}
		}
		if (HorseCart.cartsCooldown.contains(p))
		{
			HorseCart.cartsCooldown.remove(p);
			if (HorseCart.config.getBoolean("slowerCarts"))
			{
				((LivingEntity) p.getVehicle()).removePotionEffect(PotionEffectType.SLOW);
			}
		}
		if (HorseCart.chatCooldown.contains(p))
		{
			HorseCart.chatCooldown.remove(p);
			if (HorseCart.config.getBoolean("slowerCarts"))
			{
				((LivingEntity) p.getVehicle()).removePotionEffect(PotionEffectType.SLOW);
			}
		}
		

	}
	
	public void kickOff(PlayerKickEvent event) {
		Player p = event.getPlayer();
		if (HorseCart.carts.contains(p))
		{
			HorseCart.carts.remove(p);
			if (HorseCart.config.getBoolean("slowerCarts"))
			{
				((LivingEntity) p.getVehicle()).removePotionEffect(PotionEffectType.SLOW);
			}
		}
		if (HorseCart.cartsCooldown.contains(p))
		{
			HorseCart.cartsCooldown.remove(p);
			if (HorseCart.config.getBoolean("slowerCarts"))
			{
				((LivingEntity) p.getVehicle()).removePotionEffect(PotionEffectType.SLOW);
			}
		}
		if (HorseCart.chatCooldown.contains(p))
		{
			HorseCart.chatCooldown.remove(p);
			if (HorseCart.config.getBoolean("slowerCarts"))
			{
				((LivingEntity) p.getVehicle()).removePotionEffect(PotionEffectType.SLOW);
			}
		}
	}
	@EventHandler
	public void dismountHorse(VehicleExitEvent event) {
		if (((event.getVehicle() instanceof Horse) || (event.getVehicle() instanceof Donkey) ||  (event.getVehicle() instanceof Mule) ||  (event.getVehicle() instanceof Llama)) && (event.getExited() instanceof Player))
		{
			if (HorseCart.carts.contains((Player)event.getExited()))
			{
				HorseCart.carts.remove((Player)event.getExited());
				if (HorseCart.config.getBoolean("slowerCarts"))
				{
					((LivingEntity) event.getVehicle()).removePotionEffect(PotionEffectType.SLOW);
				}
				
			}
		}
	}
}