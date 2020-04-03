package me.thetimelord.horsecart;


import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class HorseCart extends JavaPlugin {
	public static ArrayList<Player> carts = new ArrayList<Player>();
	public static ArrayList<Player> cartsCooldown = new ArrayList<Player>();
	public static ArrayList<Player> chatCooldown = new ArrayList<Player>();
	@Override
	public void onEnable() {
		new HorseListener(this);
		Plugin pluginMain = this;
		
		for (Player p : Bukkit.getServer().getOnlinePlayers())
		{
			if (p.isInsideVehicle())
			{
				if (p.getVehicle() instanceof Horse)
				{
					if (Utils.isCart((Horse) p.getVehicle()) != null)
					{
						carts.add(p);
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
		    			if (p.getVehicle() instanceof Horse)
		    			{
		    				Horse h = (Horse) p.getVehicle();
		    				if (!h.isLeashed())
		    				{
		    					carts.remove(p);
		    				}
		    				if (h.isLeashed())
		    				{
		    					BlockFace cartDirection = Utils.isCart(h);
		    					//Bukkit.getServer().broadcastMessage(cartDirection.toString());
		    					if (cartDirection != null)
		    					{
		    						Location playerLoc = p.getLocation();
		    						Location cartLoc = h.getLeashHolder().getLocation();
		    						
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
		    								if (!Utils.cartMove(h.getLeashHolder().getLocation().getBlock(), xDif, zDif, cartDirection, h))
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
		}, 10, 3);
	}
	
	@Override
	public void onDisable() {
		
		
	}
}


