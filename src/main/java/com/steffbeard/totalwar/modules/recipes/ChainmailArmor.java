package com.steffbeard.totalwar.modules.recipes;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import com.steffbeard.totalwar.Core;

public class ChainmailArmor {

	private Core plugin;
	private ItemStack helmet, chestplate, leggings, boots;

	public ChainmailArmor(Core plugin) {
        this.plugin = plugin;
        helmetRecipe();
        leggingsRecipe();
    }
	
	private ShapedRecipe helmetRecipe() {
		ItemStack helmet = new ItemStack(Material.CHAINMAIL_HELMET);
		ItemMeta hmeta = helmet.getItemMeta();
		hmeta.setDisplayName(ChatColor.DARK_GRAY + "Mail Coif");
		helmet.setItemMeta(hmeta);
		helmet.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        helmet.addEnchantment(Enchantment.PROTECTION_PROJECTILE, 4);
        NamespacedKey hkey = new NamespacedKey(plugin, "chainmail_helmet");
        ShapedRecipe helmetrecipe = new ShapedRecipe(hkey, helmet);
        helmetrecipe.shape(new String[] { "@@@", 
        								  "@#@" });
        helmetrecipe.setIngredient('@', Material.IRON_NUGGET);
        helmetrecipe.setIngredient('#', Material.AIR);
        plugin.getServer().addRecipe(helmetrecipe);
		return helmetrecipe;
	}
	
	private ShapedRecipe leggingsRecipe() {
		ItemStack leggings = new ItemStack(Material.CHAINMAIL_LEGGINGS);
		ItemMeta lmeta = leggings.getItemMeta();
		lmeta.setDisplayName(ChatColor.DARK_GRAY + "Mail Leggings");
		leggings.setItemMeta(lmeta);
		leggings.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        leggings.addEnchantment(Enchantment.PROTECTION_PROJECTILE, 4);
        NamespacedKey lkey = new NamespacedKey(plugin, "chainmail_leggings");
        ShapedRecipe leggingsrecipe = new ShapedRecipe(lkey, leggings);
        leggingsrecipe.shape(new String[] { "@@@", 
        									"@#@", 
        									"@#@" });
        leggingsrecipe.setIngredient('@', Material.IRON_NUGGET);
        leggingsrecipe.setIngredient('#', Material.AIR);
        plugin.getServer().addRecipe(leggingsrecipe);
		return leggingsrecipe;
	}
	
	public ItemStack getHelmet() {
		return helmet;
	}
	
	public ItemStack getChestPlate() {
		return chestplate;
	}
	
	public ItemStack getLeggings() {
		return leggings;
	}
	
	public ItemStack getBoots() {
		return boots;
	}
	
}