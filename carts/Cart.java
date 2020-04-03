package com.steffbeard.totalwar.modules.carts;

import java.util.ArrayList;
import java.util.List;

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
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.PluginManager;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.steffbeard.totalwar.modules.carts.listener.HorseListener;
import com.steffbeard.totalwar.modules.horsecart.CartUtils;

import dev.siris.module.Module;

public class Cart extends Module {
	
	public static FileConfiguration config;
	
	public Cart plugin;
	
	public static ArrayList<Player> carts = new ArrayList<Player>();
	public static ArrayList<Player> cartsCooldown = new ArrayList<Player>();
	public static ArrayList<Player> chatCooldown = new ArrayList<Player>();
	
	// Config options
	public static boolean debugMode = false;
	public static boolean slowerCarts = true; 

	@Override
	public void onEnable() {
		this.loadListeners();
		this.updateConfig();
		this.slowerCarts();
		
		getLogger().info("> Carts has been loaded");
		
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
		    										Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() 
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
		    									Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() 
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
		HandlerList.unregisterAll();
	}
	
	public void onReload() {
		
	}
	
	public void loadListeners() {
    	PluginManager pm = this.getServer().getPluginManager();
        pm.registerEvents(new HorseListener(), this);
    }
	
	public void slowerCarts() {
		for (Player p : Bukkit.getServer().getOnlinePlayers()) {
			if (p.isInsideVehicle()) {
				if (p.getVehicle() instanceof Horse || p.getVehicle() instanceof Donkey || p.getVehicle() instanceof Mule || p.getVehicle() instanceof Llama) {
					if (((LivingEntity) p.getVehicle()).isLeashed()) {
						if (CartUtils.isCart((LivingEntity) p.getVehicle()) != null) {
							carts.add(p);
							if (getConfig().getBoolean("slowerCarts")) {
								((LivingEntity) p.getVehicle()).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 99999, 2));
							}
						}
					}
				}
			}
		}
	}
	
	/**
     * 
     * Method for reloading config
     * 
     */
    public void updateConfig() {
    	Cart.debugMode = this.getConfig().getBoolean("debugMode");
    	Cart.slowerCarts = this.getConfig().getBoolean("slowerCarts");
    	config = getConfig();
        this.saveDefaultConfig();
        this.reloadConfig();
    }
}
