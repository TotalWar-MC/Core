package com.steffbeard.totalwar.modules.tweaks.listeners;

import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityBreedEvent;

import com.steffbeard.totalwar.modules.tweaks.Tweaks;

import java.util.Set;

/**
 * 
 * Listener to know if two animals did the deed or not.
 * Also gets rid of the XP dropped so we don't lag the server by spawning more entities then we already are.
 * Plus players don't need XP anyways.
 * 
 * @author Steffbeard
 *
 */
public class BreedTaskListener implements Listener {
    
	private Tweaks plugin;

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntityBreedEvent(EntityBreedEvent e) {
        Set<Entity> matedAnimals = plugin.lastMateAnimals;
        if (matedAnimals.contains(e.getFather()) || matedAnimals.contains(e.getMother())) {
            e.setExperience(0);
            matedAnimals.remove(e.getFather());
            matedAnimals.remove(e.getMother());
        }
    }
}