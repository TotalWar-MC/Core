package me.thetimelord.horsecart;

import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.vehicle.VehicleEnterEvent;
import org.bukkit.event.vehicle.VehicleExitEvent;

public class HorseListener implements Listener{
	public HorseListener(HorseCart plugin){
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void mountHorse(VehicleEnterEvent event)
	{
		//Bukkit.getServer().broadcastMessage("Mounted vehicle!");
		if ((event.getVehicle() instanceof Horse) && (event.getEntered() instanceof Player))
		{
			//Bukkit.getServer().broadcastMessage("Mounted horse!");
			Horse horse = (Horse) event.getVehicle();
			if (horse.isLeashed())
			{
				//Bukkit.getServer().broadcastMessage("Mounted leashed horse!");
				if (Utils.isCart(horse) != null)
				{
					//Bukkit.getServer().broadcastMessage("Mounted horse with cart!");
					HorseCart.carts.add((Player) event.getEntered());
				}
			}
		}
	}
	
	public void logOff(PlayerQuitEvent event)
	{
		Player p = event.getPlayer();
		if (HorseCart.carts.contains(p))
		{
			HorseCart.carts.remove(p);
		}
		if (HorseCart.cartsCooldown.contains(p))
		{
			HorseCart.cartsCooldown.remove(p);
		}
		if (HorseCart.chatCooldown.contains(p))
		{
			HorseCart.chatCooldown.remove(p);
		}
	}
	
	public void kickOff(PlayerKickEvent event)
	{
		Player p = event.getPlayer();
		if (HorseCart.carts.contains(p))
		{
			HorseCart.carts.remove(p);
		}
		if (HorseCart.cartsCooldown.contains(p))
		{
			HorseCart.cartsCooldown.remove(p);
		}
		if (HorseCart.chatCooldown.contains(p))
		{
			HorseCart.chatCooldown.remove(p);
		}
	}
	@SuppressWarnings("deprecation")
	@EventHandler
	public void dismountHorse(VehicleExitEvent event)
	{
		if ((event.getVehicle() instanceof Horse) && (event.getExited() instanceof Player))
		{
			if (HorseCart.carts.contains((Player)event.getExited()))
			{
				HorseCart.carts.remove((Player)event.getExited());
			}
		}
	}
}