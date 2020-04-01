package com.steffbeard.totalwar.modules.tweaks;

import org.bukkit.entity.Animals;

public interface Animal {
    public boolean isInLoveMode(Animals animal);

    public void startLoveMode(Animals animal);
}
