package com.steffbeard.totalwar.modules.recipes;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import com.steffbeard.totalwar.Core;

public class HorseItems {

	private Core plugin;
	private ItemStack saddle, ironbarding, goldbarding, lead;
	
	/**
	 * 
	 * Sets up the horse crafting recipes
	 * 
	 * @param plugin
	 */
	public HorseItems(Core plugin) {
		this.plugin = plugin;
		saddleRecipe();
		ironbardingRecipe();
		goldbardingRecipe();
		leadRecipe();
	}
	
	/**
	 * 
	 * Saddle
	 * 
	 * @return saddle recipe
	 */
	private ShapedRecipe saddleRecipe() {
		ItemStack saddle = new ItemStack(Material.SADDLE);
		ItemMeta smeta = saddle.getItemMeta();
		smeta.setDisplayName(ChatColor.YELLOW + "Saddle");
		saddle.setItemMeta(smeta);
		NamespacedKey skey = new NamespacedKey(plugin, "saddle");
		ShapedRecipe saddlerecipe = new ShapedRecipe(skey, saddle);
		saddlerecipe.shape(new String[] { "LLL",
										  "I#I"});
		saddlerecipe.setIngredient('L', Material.LEATHER);
		saddlerecipe.setIngredient('I', Material.IRON_INGOT);
		saddlerecipe.setIngredient('#', Material.AIR);
		plugin.getServer().addRecipe(saddlerecipe);
		return saddlerecipe;
	}
	
	/**
	 * 
	 * Iron Barding
	 * 
	 * @return iron barding recipe
	 */
	private ShapedRecipe ironbardingRecipe() {
		ItemStack ironbarding = new ItemStack(Material.IRON_BARDING);
		ItemMeta ibmeta = ironbarding.getItemMeta();
		ibmeta.setDisplayName(ChatColor.GRAY + "Iron Barding");
		ironbarding.setItemMeta(ibmeta);
		NamespacedKey ibkey = new NamespacedKey(plugin, "iron_barding");
		ShapedRecipe ironbardingrecipe = new ShapedRecipe(ibkey, ironbarding);
		ironbardingrecipe.shape(new String[] { "I##",
											   "ILI",
											   "I#I"});
		ironbardingrecipe.setIngredient('L', Material.LEATHER);
		ironbardingrecipe.setIngredient('I', Material.IRON_INGOT);
		ironbardingrecipe.setIngredient('#', Material.AIR);
		plugin.getServer().addRecipe(ironbardingrecipe);
		return ironbardingrecipe;
	}
	
	/**
	 * 
	 * Gold Barding
	 * 
	 * @return gold barding recipe
	 */
	private ShapedRecipe goldbardingRecipe() {
		ItemStack goldbarding = new ItemStack(Material.GOLD_BARDING);
		ItemMeta gbmeta = goldbarding.getItemMeta();
		gbmeta.setDisplayName(ChatColor.GRAY + "Gold Barding");
		goldbarding.setItemMeta(gbmeta);
		NamespacedKey gbkey = new NamespacedKey(plugin, "gold_barding");
		ShapedRecipe goldbardingrecipe = new ShapedRecipe(gbkey, goldbarding);
		goldbardingrecipe.shape(new String[] { "I##",
											   "ILI",
											   "I#I"});
		goldbardingrecipe.setIngredient('L', Material.LEATHER);
		goldbardingrecipe.setIngredient('I', Material.GOLD_INGOT);
		goldbardingrecipe.setIngredient('#', Material.AIR);
		plugin.getServer().addRecipe(goldbardingrecipe);
		return goldbardingrecipe;
	}
	
	/**
	 * 
	 * Lead 
	 * 
	 * @return lead recipe
	 */
	private ShapedRecipe leadRecipe() {
		ItemStack lead = new ItemStack(Material.LEASH);
		ItemMeta lmeta = lead.getItemMeta();
		lmeta.setDisplayName(ChatColor.YELLOW + "Lead");
		lead.setItemMeta(lmeta);
		NamespacedKey lkey = new NamespacedKey(plugin, "lead");
		ShapedRecipe leadrecipe = new ShapedRecipe(lkey, lead);
		leadrecipe.shape(new String[] { "SS#",
										"SS#",
										"##S"});
		leadrecipe.setIngredient('S', Material.STRING);
		leadrecipe.setIngredient('#', Material.AIR);
		plugin.getServer().addRecipe(leadrecipe);
		return leadrecipe;
	}
	
	/**
	 * 
	 * Get saddle
	 * 
	 * @return saddle
	 */
	public ItemStack getSaddle() {
		return saddle;
	}
	
	/**
	 * 
	 * Get iron barding
	 * 
	 * @return iron barding
	 */
	public ItemStack getIronBarding() {
		return ironbarding;
	}
	
	/**
	 * 
	 * Get gold barding
	 * 
	 * @return gold barding
	 */
	public ItemStack getGoldBarding() {
		return goldbarding;
	}
	
	/**
	 * 
	 * Get lead
	 * 
	 * @return lead
	 */
	public ItemStack getLead() {
		return lead;
	}
}
