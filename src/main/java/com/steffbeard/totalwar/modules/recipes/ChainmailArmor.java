package com.steffbeard.totalwar.modules.recipes;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import com.steffbeard.totalwar.Core;

public class ChainmailArmor {

	private Core plugin;
	private ItemStack helmet, chestplate, leggings, boots;

	/**
	 * 
	 * Sets up the recipe class and pulls the recipes defined below.
	 * 
	 * @param plugin
	 */
	public ChainmailArmor(Core plugin) {
        this.plugin = plugin;
        helmetRecipe();
        leggingsRecipe();
        chestplateRecipe();
        bootsRecipe();
    }
	
	/**
	 * 
	 * Chainmail Helmet
	 * 
	 * @return helmet recipe
	 */
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
	
	/**
	 * 
	 * Chainmail Chestplate
	 * 
	 * @return chestplate recipe
	 */
	private ShapedRecipe chestplateRecipe() {
		ItemStack chestplate = new ItemStack(Material.CHAINMAIL_CHESTPLATE);
		ItemMeta cmeta = chestplate.getItemMeta();
		cmeta.setDisplayName(ChatColor.DARK_GRAY + "Mail Tunic");
		chestplate.setItemMeta(cmeta);
		chestplate.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        chestplate.addEnchantment(Enchantment.PROTECTION_PROJECTILE, 4);
        NamespacedKey ckey = new NamespacedKey(plugin, "chainmail_chestplate");
        ShapedRecipe chestplaterecipe = new ShapedRecipe(ckey, chestplate);
        chestplaterecipe.shape(new String[] { "@#@", 
        									  "@@@", 
        									  "@@@" });
        chestplaterecipe.setIngredient('@', Material.IRON_NUGGET);
        chestplaterecipe.setIngredient('#', Material.AIR);
        plugin.getServer().addRecipe(chestplaterecipe);
		return chestplaterecipe;
	}
	
	/**
	 * 
	 * Chainmail Leggings
	 * 
	 * @return leggings recipe
	 */
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
	
	/**
	 * 
	 * Chainmail Boots
	 * 
	 * @return boots recipe
	 */
	private ShapedRecipe bootsRecipe() {
		ItemStack boots = new ItemStack(Material.CHAINMAIL_BOOTS);
		ItemMeta bmeta = boots.getItemMeta();
		bmeta.setDisplayName(ChatColor.DARK_GRAY + "Mail Boots");
		boots.setItemMeta(bmeta);
		boots.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        boots.addEnchantment(Enchantment.PROTECTION_PROJECTILE, 4);
        NamespacedKey bkey = new NamespacedKey(plugin, "chainmail_boots");
        ShapedRecipe bootsrecipe = new ShapedRecipe(bkey, boots);
        bootsrecipe.shape(new String[] { "@#@", 
        							     "@#@"});
        bootsrecipe.setIngredient('@', Material.IRON_NUGGET);
        bootsrecipe.setIngredient('#', Material.AIR);
        plugin.getServer().addRecipe(bootsrecipe);
		return bootsrecipe;
	}
	
	/**
	 * 
	 * Get helmet
	 * 
	 * @return helmet
	 */
	public ItemStack getHelmet() {
		return helmet;
	}
	
	/**
	 * 
	 * Get chestplate
	 * 
	 * @return chestplate
	 */
	public ItemStack getChestPlate() {
		return chestplate;
	}
	
	/**
	 * 
	 * Get leggings
	 * 
	 * @return leggings
	 */
	public ItemStack getLeggings() {
		return leggings;
	}
	
	/**
	 * 
	 * Get boots
	 * 
	 * @return boots
	 */
	public ItemStack getBoots() {
		return boots;
	}
	
}