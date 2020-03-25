package com.steffbeard.totalwar.modules.ore.config;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.steffbeard.totalwar.modules.ore.HiddenOre;

public class BlockConfig {
	private String material;
	private Set<Byte> subtypes;
	private Collection<MaterialWrapper> validGenTypes;
	private boolean dropMultiple;
	private boolean suppressDrops;
	private Map<String, DropConfig> dropConfigs;
	private String prefix;
	
	public BlockConfig(String material, boolean dropMultiple, boolean suppressDrops, String prefix, Collection<MaterialWrapper> validGenTypes) {
		this(material, null, dropMultiple, suppressDrops, prefix, validGenTypes);
	}

	public BlockConfig(String material, Collection<Byte> subtype, boolean dropMultiple, boolean suppressDrops, String prefix, Collection<MaterialWrapper> validGenTypes) {
		this.material = material;
		this.subtypes = (subtype != null) ? new HashSet<Byte>(subtype) : new HashSet<Byte>();
		this.setDropMultiple(dropMultiple);
		this.setSuppressDrops(suppressDrops);
		this.dropConfigs = new HashMap<String, DropConfig>();
		this.prefix = prefix;
		this.validGenTypes = validGenTypes;
	}
	
	public boolean checkSubType(Byte subtype) {
		return this.subtypes.isEmpty() || this.subtypes.contains(subtype);	
	}	

	public String getMaterial() {	
		return this.material;
	}
	
	@SuppressWarnings("deprecation")
	public boolean checkBlock(Block check) {
		return material.equals(check.getType().name()) && checkSubType(check.getData());
	}
	
	/**
	 * Allows check against an expanded list of valid blocks that ores can generate in -- companion blocks, effectively.
	 * For instance, if you separately configure stone types but want generation in stone of all types;
	 * 
	 * @param check the block to check
	 * @return true if we can generate into this block
	 */
	public boolean checkGenerateBlock(Block check) {
		if (checkBlock(check)) return true;
		
		if (validGenTypes == null) return false;
		for (MaterialWrapper wrapper : validGenTypes) {
			if (wrapper.checkBlock(check)) return true;
		}
		
		return false;
	}

	public String getPrefix(String drop) {
		if (drop == null) return prefix;
		DropConfig dc = dropConfigs.get(drop);
		return (dc == null || dc.prefix == null) ? prefix : dc.prefix;
	}

	public boolean hasCustomPrefix(String drop) {
		if (drop == null) return false;
		DropConfig dc = dropConfigs.get(drop);
		return (dc == null || dc.prefix == null) ? (prefix != null) : true;
	}

	public void addDropConfig(String drop, DropConfig dropConfig) {
		dropConfigs.put(drop, dropConfig);
	}

	public Set<String> getDrops() {
		return dropConfigs.keySet();
	}

	public DropConfig getDropConfig(String drop) {
		return dropConfigs.get(drop);
	}

	public String getDropConfig(double dice, String biome, ItemStack tool, Player player, Location loc) {
		// accrue possible drops based on biome / tool
		// check dice against stacked probabilities

		double cumChance = 0.0d;
		double localChance = 0.0d;
		int counted = 0;
		int blockY = loc.getBlockY();

		for (Map.Entry<String, DropConfig> dce : dropConfigs.entrySet()) {
			DropConfig dc = dce.getValue();
			if (dc.dropsWithTool(biome, tool) && blockY <= dc.getMaxY(biome)
					&& blockY >= dc.getMinY(biome)) {
				
				ToolConfig tc = dc.dropsWithToolConfig(biome, tool);
				localChance = dc.getChance(biome) * (tc == null ? 1.0 : tc.getDropChanceModifier()) * dc.getStateChance(biome, player);
				
				VeinConfig vc = dc.getVeinNature();
				if (vc != null) {
					localChance *= vc.getOreChance(loc);
					
					/*DIAGNOSTICS
					HiddenOre.getPlugin().getLogger()
							.log(Level.INFO, "Base chance {0}| tool mod {1}| vein mod {2}| totalChance {3}| tc {4}",
							new Object[] {Double.toString(dc.getChance(biome)),
									Double.toString(tc == null ? 1.0 : tc.getDropChanceModifier()),
									vc.getOreChance(loc), localChance, (tc == null ? null : tc.toString())});*/
				} else {
					/*DIAGNOSTICS
					HiddenOre.getPlugin().getLogger()
							.log(Level.INFO, "Base chance {0}| tool mod {1}| totalChance {2}| tc {3}",
							new Object[] {Double.toString(dc.getChance(biome)),
									Double.toString(tc == null ? 1.0 : tc.getDropChanceModifier()),
									localChance, (tc == null ? null : tc.toString())});*/
				}
				
				if (dice >= cumChance && dice < cumChance + localChance) {
					return dce.getKey();
				}
				cumChance += localChance;
				counted++;
			}
		}
		if (Config.isDebug) {
			HiddenOre.getPlugin().getLogger()
					.log(Level.INFO, "{0} tested {1} cumm {2} dice", new Object[] {counted, Double.toString(cumChance), Double.toString(dice)});
		}

		return null;
	}

	public boolean isDropMultiple() {
		return dropMultiple;
	}

	public void setDropMultiple(boolean dropMultiple) {
		this.dropMultiple = dropMultiple;
	}

	public boolean shouldSuppressDrops() {
		return suppressDrops;
	}

	public void setSuppressDrops(boolean suppressDrops) {
		this.suppressDrops = suppressDrops;
	}

	static class MaterialWrapper {	
		String material;	
		Set<Byte> subtypes;	

		public MaterialWrapper(String material, Collection<Byte> subtypes) {	
			this.material = material;	
			this.subtypes = (subtypes != null) ? new HashSet<Byte>(subtypes) : new HashSet<Byte>();		
		}	

		public boolean checkSubType(Byte subtype) {	
			return this.subtypes.isEmpty() || this.subtypes.contains(subtype);	
		}	

		public String getMaterial() {	
			return this.material;	
		}	

		@SuppressWarnings("deprecation")	
		public boolean checkBlock(Block check) {	
			return material.equals(check.getType().name()) && checkSubType(check.getData());	
		}	
	}	
}