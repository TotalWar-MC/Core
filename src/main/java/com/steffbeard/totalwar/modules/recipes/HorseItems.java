package com.steffbeard.totalwar.modules.recipes;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import com.steffbeard.totalwar.Core;

public class HorseItems {

	private Core plugin;
	private ItemStack saddle, ironbarding, goldbarding, lead, nametag;
	
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
		nametagRecipe();
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
		plugin.getServer().addRecipe((Recipe)saddlerecipe);
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
		plugin.getServer().addRecipe((Recipe)ironbardingrecipe);
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
		plugin.getServer().addRecipe((Recipe)goldbardingrecipe);
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
		plugin.getServer().addRecipe((Recipe)leadrecipe);
		return leadrecipe;
	}

	/**
	 * 
	 * Nametag recipes
	 * 
	 * I don't know if this actually works because I always used "ShapedRecipe" in the constructor and not void 
	 * so I always had a return statement. Let's see if this method works. This method is technically better
	 * because I can have multiple shapes for the same recipe inexchange for my fingers hurting.
	 * 
	 */
	private void nametagRecipe() {
		ItemStack nametag = new ItemStack(Material.NAME_TAG);
		ItemMeta nmeta = nametag.getItemMeta();
		nmeta.setDisplayName(ChatColor.YELLOW + "Nametag");
		nametag.setItemMeta(nmeta);
        ShapedRecipe nametag1 = new ShapedRecipe(new NamespacedKey(plugin, "nametag_1"), nametag).shape(new String[]{"ABB", "   ", "   "}).setIngredient('A', Material.STRING).setIngredient('B', Material.PAPER);
        ShapedRecipe nametag2 = new ShapedRecipe(new NamespacedKey(plugin, "nametag_2"), nametag).shape(new String[]{"   ", "ABB", "   "}).setIngredient('A', Material.STRING).setIngredient('B', Material.PAPER);
        ShapedRecipe nametag3 = new ShapedRecipe(new NamespacedKey(plugin, "nametag_3"), nametag).shape(new String[]{"   ", "   ", "ABB"}).setIngredient('A', Material.STRING).setIngredient('B', Material.PAPER);
        ShapedRecipe nametag4 = new ShapedRecipe(new NamespacedKey(plugin, "nametag_4"), nametag).shape(new String[]{"BBA", "   ", "   "}).setIngredient('A', Material.STRING).setIngredient('B', Material.PAPER);
        ShapedRecipe nametag5 = new ShapedRecipe(new NamespacedKey(plugin, "nametag_5"), nametag).shape(new String[]{"   ", "BBA", "   "}).setIngredient('A', Material.STRING).setIngredient('B', Material.PAPER);
        ShapedRecipe nametag6 = new ShapedRecipe(new NamespacedKey(plugin, "nametag_6"), nametag).shape(new String[]{"   ", "   ", "BBA"}).setIngredient('A', Material.STRING).setIngredient('B', Material.PAPER);
        ShapedRecipe nametag7 = new ShapedRecipe(new NamespacedKey(plugin, "nametag_7"), nametag).shape(new String[]{"A  ", "B  ", "B  "}).setIngredient('A', Material.STRING).setIngredient('B', Material.PAPER);
        ShapedRecipe nametag8 = new ShapedRecipe(new NamespacedKey(plugin, "nametag_8"), nametag).shape(new String[]{" A ", " B ", " B "}).setIngredient('A', Material.STRING).setIngredient('B', Material.PAPER);
        ShapedRecipe nametag9 = new ShapedRecipe(new NamespacedKey(plugin, "nametag_9"), nametag).shape(new String[]{"  A", "  B", "  B"}).setIngredient('A', Material.STRING).setIngredient('B', Material.PAPER);
        ShapedRecipe nametag10 = new ShapedRecipe(new NamespacedKey(plugin, "nametag_10"), nametag).shape(new String[]{"B  ", "B  ", "A  "}).setIngredient('A', Material.STRING).setIngredient('B', Material.PAPER);
        ShapedRecipe nametag11 = new ShapedRecipe(new NamespacedKey(plugin, "nametag_11"), nametag).shape(new String[]{" B ", " B ", " A "}).setIngredient('A', Material.STRING).setIngredient('B', Material.PAPER);
        ShapedRecipe nametag12 = new ShapedRecipe(new NamespacedKey(plugin, "nametag_12"), nametag).shape(new String[]{"  B", "  B", "  A"}).setIngredient('A', Material.STRING).setIngredient('B', Material.PAPER);
        ShapedRecipe nametag13 = new ShapedRecipe(new NamespacedKey(plugin, "nametag_13"), nametag).shape(new String[]{"  A", " B ", "B  "}).setIngredient('A', Material.STRING).setIngredient('B', Material.PAPER);
        ShapedRecipe nametag14 = new ShapedRecipe(new NamespacedKey(plugin, "nametag_14"), nametag).shape(new String[]{"  B", " B ", "A  "}).setIngredient('A', Material.STRING).setIngredient('B', Material.PAPER);
        ShapedRecipe nametag15 = new ShapedRecipe(new NamespacedKey(plugin, "nametag_15"), nametag).shape(new String[]{"A  ", " B ", "  B"}).setIngredient('A', Material.STRING).setIngredient('B', Material.PAPER);
        ShapedRecipe nametag16 = new ShapedRecipe(new NamespacedKey(plugin, "nametag_16"), nametag).shape(new String[]{"B  ", " B ", "  A"}).setIngredient('A', Material.STRING).setIngredient('B', Material.PAPER);
        plugin.getServer().addRecipe((Recipe)nametag1);
        plugin.getServer().addRecipe((Recipe)nametag2);
        plugin.getServer().addRecipe((Recipe)nametag3);
        plugin.getServer().addRecipe((Recipe)nametag4);
        plugin.getServer().addRecipe((Recipe)nametag5);
        plugin.getServer().addRecipe((Recipe)nametag6);
        plugin.getServer().addRecipe((Recipe)nametag7);
        plugin.getServer().addRecipe((Recipe)nametag8);
        plugin.getServer().addRecipe((Recipe)nametag9);
        plugin.getServer().addRecipe((Recipe)nametag10);
        plugin.getServer().addRecipe((Recipe)nametag11);
        plugin.getServer().addRecipe((Recipe)nametag12);
        plugin.getServer().addRecipe((Recipe)nametag13);
        plugin.getServer().addRecipe((Recipe)nametag14);
        plugin.getServer().addRecipe((Recipe)nametag15);
        plugin.getServer().addRecipe((Recipe)nametag16);
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
	
	/**
	 * 
	 * Get nametag
	 * 
	 * @return nametag
	 */
	public ItemStack getNameTag() {
		return nametag;
	}
}
