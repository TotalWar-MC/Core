package com.steffbeard.totalwar.modules.tweaks.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.block.Block;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Arrow;
import org.bukkit.event.entity.ProjectileHitEvent;

import com.steffbeard.totalwar.modules.tweaks.Tweaks;

import org.bukkit.event.Listener;

/**
 * 
 * Listens for if an arrow is on fire and handles spawning fire when it lands.
 * 
 * @author Steffbeard
 *
 */
public class ArrowListener implements Listener {
	
    @EventHandler
    public void onBlockHit(final ProjectileHitEvent event) {
        if (event.getEntity() instanceof Arrow && event.getEntity().getFireTicks() > 0 && Tweaks.canUseInWorld(event.getEntity().getWorld())) {
            if (event.getEntity().getShooter() instanceof Player) {
            	final Block block = event.getEntity().getLocation().getBlock();
                if (block.getType().equals((Object)Material.AIR)) {
                    block.setType(Material.FIRE);
                }
            }
            
            else if (!Tweaks.playerOnlyArrows) {
                final Block block2 = event.getEntity().getLocation().getBlock();
                if (block2.getType().equals((Object)Material.AIR)) {
                    block2.setType(Material.FIRE);
                }
            }
        }
    }
}
