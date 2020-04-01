package com.steffbeard.totalwar.modules.tweaks.listeners;

import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityBreedEvent;

import com.steffbeard.totalwar.modules.tweaks.Tweaks;

import java.util.Set;

public class BreedTaskListener implements Listener {
    private Tweaks plugin;

    public BreedTaskListener(Tweaks plugin) {
        this.plugin = plugin;
    }

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