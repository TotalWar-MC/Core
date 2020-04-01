package com.steffbeard.totalwar.modules.tweaks.handlers;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Entity;
import org.bukkit.scheduler.BukkitRunnable;

import com.steffbeard.totalwar.modules.tweaks.Tweaks;

public class BreedTask extends BukkitRunnable {
    private final Tweaks plugin;
    private final Random random;

    public BreedTask(Tweaks plugin) {
        this.plugin = plugin;
        this.random = new Random();
    }

    public void run() {
        plugin.clearMatedAnimals();

        List<World> worlds = plugin.getServer().getWorlds();
        for (World world : worlds) {
            Chunk[] chunks = world.getLoadedChunks();

            for (Chunk chunk : chunks) {
                Entity[] entities = chunk.getEntities();

                for (Entity e: entities) {
                    if (e instanceof Animals) {
                        Animals animal = (Animals) e;

                        if (animal.isAdult() && animal.canBreed() && !plugin.getWildAnimalHandler().isInLoveMode(animal)) {
                            double outcome = random.nextDouble();
                            if (outcome <= getReproductionProbability(animal)) {
                                if (plugin.getMateMode()) {
                                    int md = plugin.getMaxMateDistance() / 2;
                                    List<Entity> others = animal.getNearbyEntities(md, md, md);
                                    List<Animals> eligibleMates = new ArrayList<Animals>();

                                    for (Entity mate : others) {
                                        if (mate.getClass() == animal.getClass()) {
                                            Animals mateAnimal = (Animals) mate;

                                            if (mateAnimal.isAdult() && mateAnimal.canBreed() && !plugin.getWildAnimalHandler().isInLoveMode(mateAnimal)) {
                                                eligibleMates.add(mateAnimal);
                                            }
                                        }
                                    }

                                    if (!eligibleMates.isEmpty()) {
                                        Animals mateAnimal = eligibleMates.get(random.nextInt(eligibleMates.size()));
                                        plugin.getWildAnimalHandler().startLoveMode(animal);
                                        plugin.addMatedAnimal(animal);
                                        plugin.getWildAnimalHandler().startLoveMode(mateAnimal);
                                        plugin.addMatedAnimal(mateAnimal);
                                    }
                                } else {
                                    plugin.getWildAnimalHandler().startLoveMode(animal);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public double getReproductionProbability(Animals animal) {
        double scale = plugin.getMaxAnimalsCheckRadius() / 2.0;
        List<Entity> neighbors = animal.getNearbyEntities(scale, scale, scale);

        int count = 0;

        for (Entity neighbor : neighbors) {
            if (neighbor instanceof Animals)
                count++;
        }

        // If the count stays the same with a smaller area, then we should ideally be doing this with that smaller area.
        double radius = plugin.getMaxAnimalsCheckRadius() / 2.0;
        /*if (count > 0) {
            while (true) {
                double r = radius - 0.5;
                List<Entity> ns = animal.getNearbyEntities(r, r, r);

                Iterator<Entity> nI = ns.iterator();
                int c = 0;

                while (nI.hasNext()) {
                    Entity n = nI.next();

                    if (n instanceof Animals)
                        c++;
                }

                if (c == count) {
                    radius = r;
                } else {
                    break;
                }
            }
        }*/

        // I'm really not sure about this formula.
        double targetPopulationForCheckRadius = plugin.getMaxAnimalsPerBlock() * radius * radius * 4; // Check for the square area, but apply it to the cube.
        double currentPopulationRatio = count / targetPopulationForCheckRadius;
        double probability = plugin.getChance() * (1 - currentPopulationRatio);

        //plugin.getServer().broadcastMessage("Found " + count + " animals in a radius of " + ((int) (radius*2)) + ", so the probability is " + String.format("%%%.2f", probability * 100) + ".");

        return (probability > 0) ? probability : 0.0;
    }
}
