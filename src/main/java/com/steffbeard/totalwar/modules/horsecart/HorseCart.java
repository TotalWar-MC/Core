package com.steffbeard.totalwar.modules.horsecart;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Donkey;
import org.bukkit.entity.Horse;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Llama;
import org.bukkit.entity.Mule;
import org.bukkit.entity.Player;
import org.bukkit.entity.Vehicle;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * 
 * 
 * 
 * @author Timelord#6631
 */
public class HorseCart extends JavaPlugin {
	
	public static ArrayList<Player> carts = new ArrayList<Player>();
	public static ArrayList<Player> cartsCooldown = new ArrayList<Player>();
	public static ArrayList<Player> chatCooldown = new ArrayList<Player>();
	
	static FileConfiguration config;
	
	@Override
	public void onEnable() {
		new HorseListener(this);
		Plugin pluginMain = this;
		saveDefaultConfig();
		config = getConfig();
		
		getLogger().info("HorseCarts has been enabled");
		
		for (Player p : Bukkit.getServer().getOnlinePlayers())
		{
			if (p.isInsideVehicle())
			{
				if (p.getVehicle() instanceof Horse || p.getVehicle() instanceof Donkey || p.getVehicle() instanceof Mule || p.getVehicle() instanceof Llama)
				{
					if (((LivingEntity) p.getVehicle()).isLeashed())
					{
						if (CartUtils.isCart((LivingEntity) p.getVehicle()) != null)
						{
							carts.add(p);
							
							if (getConfig().getBoolean("slowerCarts"))
							{
								((LivingEntity) p.getVehicle()).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 99999, 2));
							}
						}
					}
				}
			}
		}
		
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
		    public void run() {
		    	for (int i = 0; i < carts.size(); ++i)
		    	{
		    		Player p = carts.get(i);
		    		if (p.getVehicle() != null)
		    		{
		    			if (p.getVehicle() instanceof Horse || p.getVehicle() instanceof Donkey || p.getVehicle() instanceof Mule || p.getVehicle() instanceof Llama)
		    			{
		    				Vehicle h = (Vehicle) p.getVehicle();
		    				if (!((LivingEntity) h).isLeashed())
		    				{
		    					carts.remove(p);
		    				}
		    				if (((LivingEntity) h).isLeashed())
		    				{
		    					BlockFace cartDirection = CartUtils.isCart((LivingEntity) h);
		    					//Bukkit.getServer().broadcastMessage(cartDirection.toString());
		    					if (cartDirection != null)
		    					{
		    						Location playerLoc = p.getLocation();
		    						Location cartLoc = ((LivingEntity) h).getLeashHolder().getLocation();
		    						
		    						double xDif = playerLoc.getX() - cartLoc.getX();
		    						double zDif = playerLoc.getZ() - cartLoc.getZ();
		    						double xDist = playerLoc.getX() - cartLoc.getX();
		    						double zDist = playerLoc.getZ() - cartLoc.getZ();
		    						
		    						/*if (xDif >= zDif)
		    						{
		    							zDif = 0;
		    						}
		    						
		    						if (zDif > xDif)
		    						{
		    							xDif = 0;
		    						}*/
		    						
		    						if (xDist < 0)
		    						{
		    							xDist = xDist * -1;
		    						}
		    						if (zDist < 0)
		    						{
		    							zDist = zDist * -1;
		    						}
		    						
		    						if (xDist + zDist >= 4)
		    						{
		    							//Bukkit.getServer().broadcastMessage("Cart dist long");
		    							if (p.hasPermission("horsecarts.use"))
		    							{
		    								if (!CartUtils.cartMove(((LivingEntity) h).getLeashHolder().getLocation().getBlock(), xDif, zDif, cartDirection, (LivingEntity) h))
		    								{
		    									if (!chatCooldown.contains(p))
		    									{
		    										p.sendMessage(ChatColor.RED + "Your cart is stuck!");
		    										chatCooldown.add(p);
		    										Bukkit.getScheduler().scheduleSyncDelayedTask(pluginMain, new Runnable() 
		    							    		{
		    							    			public void run() {
		    							    			chatCooldown.remove(p);
		    							    			}
		    							    		},60 );
		    									}
		    								}
		    							}
		    							if (!p.hasPermission("horsecarts.use"))
		    							{
		    								if (!chatCooldown.contains(p))
	    									{
		    									p.sendMessage(ChatColor.RED + "You don't have permissions to do this!");
		    									chatCooldown.add(p);
		    									Bukkit.getScheduler().scheduleSyncDelayedTask(pluginMain, new Runnable() 
	    							    		{
	    							    			public void run() {
	    							    			chatCooldown.remove(p);
	    							    			}
	    							    		},60 );
	    									}
		    							}
		    						}	
		    					}
		    				}
		    			}
		    		}
		    	}
		    }
		}, 10, 2);
	}
	
	@Override
	public void onDisable() {
		
		getLogger().info("HorseCarts has been disabled");
	}
}