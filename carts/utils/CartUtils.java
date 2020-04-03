package com.steffbeard.totalwar.modules.carts.utils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LeashHitch;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.material.Chest;
import org.bukkit.material.TrapDoor;

import com.steffbeard.totalwar.modules.horsecart.HorseCart;

/**
 * 
 * Utils for detecting a cart.
 * 
 * @authors Timelord#6631, Steffbeard
 */
public class CartUtils {
	
	/**
	 * Here we set up the block IDs so we can know what to listen for when detecting the cart.
	 *  
	 * These numbers are all block IDs representing all the diff kind of fences and fence gates.
	 * 
	 * We also are going to define what counts as an air block so we know what the cart can move through.
	 * Mostly just flowers, mushrooms, and grass type blocks as well as air itself.
	 * 
	 * Now we put that all together to determine what blocks should be counted as a cart. 
	 * We take the first two lists, the "fence" and "fence gate" lists and combine them together, then we add whatever blocks we want to include.
	 * 
	 * Let's put chests for storage (54) and trapdoors (96) as wheels
	 */
	private static final Set<Integer> fence = new HashSet<Integer>(Arrays.asList(85, 113, 188, 189, 190, 191, 192));
	private static final Set<Integer> fencegate = new HashSet<Integer>(Arrays.asList(107, 183, 184, 185, 186, 187));
	private static final Set<Integer> airBlocks = new HashSet<Integer>(Arrays.asList(0, 31, 32, 37, 38, 39, 40, 51, 78, 106, 111, 175));
	private static final Set<Integer> cartBlocks = new HashSet<Integer>(Arrays.asList(85, 113, 188, 189, 190, 191, 192, 107, 183, 184, 185, 186, 187, 54, 96));
	
	/**
	 * 
	 * Now to check if the block is a fence.
	 * 
	 * @param typeId
	 * @return boolean
	 */
	public static boolean isFence(int typeId) {
		if (fence.contains(typeId)) {
			return true;
		}
		return false;
	}
	
	/**
	 * 
	 * Now to check if the block is a fence gate.
	 * 
	 * @param typeId
	 * @return boolean
	 */
	public static boolean isFenceGate(int typeId){
		if (fencegate.contains(typeId)) {
			return true;
		}
		return false;
	}
	
	/**
	 * 
	 * Now we have to set up directions for when the cart turns to the right. 
	 * Also to determine what direction the cart is facing when it was built and when it get's registered again to determine what is "relative".
	 * This is to determine what would be considered "right".
	 *
	 * @param blockFace
	 * @return direction
	 */
	public static BlockFace relativeRight(BlockFace blockFace) {
		if (blockFace.equals(BlockFace.NORTH)) {
			return BlockFace.EAST;
		} 
		if (blockFace.equals(BlockFace.EAST)) {
			return BlockFace.SOUTH;
		} 
		if (blockFace.equals(BlockFace.SOUTH)) {
			return BlockFace.WEST;
		} 
		if (blockFace.equals(BlockFace.WEST)) {
			return BlockFace.NORTH;
		}
		return null;
	}
	
	/**
	 * 
	 * Now we have to set up directions for when the cart turns to the left. 
	 * Also to determine what direction the cart is facing when it was built and when it get's registered again to dermine what is "relative".
	 * This is to determine what would be considered "right". "left".
	 * 
	 * @param blockFace
	 * @return direction
	 */
	public static BlockFace relativeLeft(BlockFace blockFace) {
		if (blockFace.equals(BlockFace.NORTH)) {
			return BlockFace.WEST;
		} 
		if (blockFace.equals(BlockFace.WEST)) {
			return BlockFace.SOUTH;
		} 
		if (blockFace.equals(BlockFace.SOUTH)) {
			return BlockFace.EAST;
		} 
		if (blockFace.equals(BlockFace.EAST)) {
			return BlockFace.NORTH;
		}
		return null;
	}
	
	/**
	 * 
	 * Check if the structure actually is a cart or not.
	 * Also checks if the cart is attached to a horse or not.
	 * Returns the direction the cart is facing.
	 * 
	 * @param h (cart)
	 * @return direction
	 */
	@SuppressWarnings("deprecation")
	public static BlockFace isCart(LivingEntity h) {
		
		Entity leashHolder = h.getLeashHolder();
		
		if (leashHolder instanceof LeashHitch) {
			Block hitch = leashHolder.getLocation().getBlock();
			BlockFace cartDirection = null;
			Block axle = null;
			
			if (CartUtils.isFenceGate(hitch.getRelative(BlockFace.NORTH).getTypeId())) {
				cartDirection = BlockFace.NORTH;
				axle = hitch.getRelative(BlockFace.NORTH);
			} 
			
			if (CartUtils.isFenceGate(hitch.getRelative(BlockFace.EAST).getTypeId())) {
				cartDirection = BlockFace.EAST;
				axle = hitch.getRelative(BlockFace.EAST);
			} 
			
			if (CartUtils.isFenceGate(hitch.getRelative(BlockFace.SOUTH).getTypeId())) {
				cartDirection = BlockFace.SOUTH;
				axle = hitch.getRelative(BlockFace.SOUTH);
				
			} 
			
			if (CartUtils.isFenceGate(hitch.getRelative(BlockFace.WEST).getTypeId())) {
				cartDirection = BlockFace.WEST;
				axle = hitch.getRelative(BlockFace.WEST);
			}
			
			if ((cartDirection != null) && (axle != null)) {
				if ((axle.getRelative(CartUtils.relativeLeft(cartDirection)).getTypeId() == 96) 
				&& (axle.getRelative(CartUtils.relativeRight(cartDirection)).getTypeId() == 96)) {
					if (axle.getRelative(BlockFace.UP).getTypeId() == 54) {
						return cartDirection;
					}
				}
			}
		}
		return null;
	}
	
	/**
	 * 
	 * Do we need to turn the cart?
	 * 
	 * @param h (cart)
	 * @param newDirection
	 * @return boolean
	 */
	@SuppressWarnings("deprecation")
	public static boolean turnCart(LivingEntity h, BlockFace newDirection) {
		
		BlockFace oldCartDirection = isCart(h);
		if (oldCartDirection != null) {
			
			Block oldHitch = h.getLeashHolder().getLocation().getBlock();
			Block axle = oldHitch.getRelative(oldCartDirection);
			
			if ((newDirection.equals(BlockFace.WEST)) || (newDirection.equals(BlockFace.EAST))) {
				axle.setData((byte) 1);
			}
			
			if ((newDirection.equals(BlockFace.NORTH)) || (newDirection.equals(BlockFace.SOUTH))) {
				axle.setData((byte) 0);
			}
			
			Block newHitch = axle.getRelative(newDirection.getOppositeFace());
			if ((oldCartDirection.getOppositeFace().equals(newDirection))) {
				if (!airBlocks.contains(newHitch.getTypeId())) {
					return false;
				}
			}
			
			Block trapDoor = axle.getRelative(CartUtils.relativeLeft(newDirection));
			Block trapDoor2 = axle.getRelative(CartUtils.relativeRight(newDirection));
			if (!airBlocks.contains(trapDoor.getTypeId()) && (!cartBlocks.contains(trapDoor.getTypeId()))) {
				return false;
			}
			
			if (!airBlocks.contains(trapDoor2.getTypeId()) && (!cartBlocks.contains(trapDoor2.getTypeId()))) {
				return false;
			}
			
			newHitch.setTypeId(oldHitch.getTypeId());
			oldHitch.setTypeId(0);
//			if (Cart.config.getBoolean("debugMode")) {
//				Bukkit.getServer().broadcastMessage(Integaer.toString(oldHitch.getTypeId()));
//			}
			
			if (((relativeLeft(oldCartDirection)).equals(newDirection)) || ((relativeRight(oldCartDirection)).equals(newDirection))) {
				
				Block axleRelative = axle.getRelative(newDirection);
				if (!isFence(axleRelative.getTypeId())) {
					axleRelative.setTypeId(0);
				}
				
				axleRelative = axle.getRelative(newDirection.getOppositeFace());
				if (!isFence(axleRelative.getTypeId())) {
					axleRelative.setTypeId(0);
				}
				
				/*
				 * Define the wheels (trapdoors)
				 */
				trapDoor.setTypeId(96);
				BlockState state = trapDoor.getState();
	            TrapDoor door = (TrapDoor) state.getData();
	            door.setOpen(true);
	            door.setFacingDirection(CartUtils.relativeLeft(newDirection));
	            state.update();
	            
	            trapDoor2.setTypeId(96);
				BlockState state2 = trapDoor2.getState();
	            TrapDoor door2 = (TrapDoor) state2.getData();
	            door2.setOpen(true);
	            door2.setFacingDirection(CartUtils.relativeRight(newDirection));
	            state2.update();
			}
			
			Block chestBlock = axle.getRelative(BlockFace.UP);
			BlockState blockState = chestBlock.getState();
			Chest directionChest = new Chest(newDirection);
			chestBlock.setData(directionChest.getData());
			
			h.getLeashHolder().remove();
			LeashHitch leashHitch = (LeashHitch) h.getWorld().spawnEntity(newHitch.getLocation(), EntityType.LEASH_HITCH);
			
			h.setLeashHolder(leashHitch);
		}
		return false;
	}
	
	/**
	 * 
	 * Method of checking what direction the block is facing based on x and z coords
	 * 
	 * @param x
	 * @param z
	 * @return direction
	 */
	public static BlockFace XZtoBlockFace(double x, double z){
		if (z > 0) {
			return BlockFace.NORTH;
		}
		
		if (z < 0) {
			return BlockFace.SOUTH;
		}
		
		if (x > 0) {
			return BlockFace.WEST;
		}
		
		if (x < 0) {
			return BlockFace.EAST;
		}
		return null;
	}
	
	/**
	 * 
	 * Method for handling the cart moving
	 * 
	 * @param hitch
	 * @param xDirection
	 * @param zDirection
	 * @param cartDirection
	 * @param h
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static boolean cartMove(Block hitch, double xDirection, double zDirection, BlockFace cartDirection, LivingEntity h) {	
		int xMod = 0;
		int yMod = 0;
		int zMod = 0;
		
		if (xDirection > 0.5) {
			xMod = 1;
		}
		
		if (xDirection < -0.5) {
			xMod = -1;
		}
		
		if (zDirection > 0.5) {
			zMod = 1;
		}
		
		if (zDirection < -0.5) {
			zMod = -1;
		}
		
		BlockFace newDirection = null;
		if (Math.abs(xDirection) >= (Math.abs(zDirection))) {
			newDirection = XZtoBlockFace(xDirection,0);
//			if (Cart.config.getBoolean("debugMode")) {
//				Bukkit.getServer().broadcastMessage("YESSSSS -1");
//			}
		}
		
		if (Math.abs(zDirection) > (Math.abs(xDirection))) {
			newDirection = XZtoBlockFace(0,zDirection);
//			if (Cart.config.getBoolean("debugMode")) {
//				Bukkit.getServer().broadcastMessage("YESSSSS -2");
//			}
		}
		
		if (!(cartDirection.equals(newDirection))) {
			if (!HorseCart.cartsCooldown.contains((Player) h.getPassenger())) {
				turnCart(h, newDirection);
				HorseCart.cartsCooldown.add((Player) h.getPassenger());
				return true;				
//				if (Cart.config.getBoolean("debugMode")) {
//					Bukkit.getServer().broadcastMessage("YESSSSS");
//				}
			}
		}
		
//		cartDirection = newDirection;
		
		Block newHitch = hitch.getRelative(xMod, yMod, zMod);
		Block newAxle = newHitch.getRelative(cartDirection);
		Block aboveHitch = newAxle.getRelative(BlockFace.UP);
		Block belowHitch = newAxle.getRelative(BlockFace.DOWN);
		
		boolean alreadyChanged = false;
		if (!airBlocks.contains(newAxle.getRelative(CartUtils.relativeLeft(cartDirection)).getTypeId())) {
			yMod = 1;
//			if (Cart.config.getBoolean("debugMode")) {
//				Bukkit.getServer().broadcastMessage("Y mod 1 - 3");
//			}
			
			if (newAxle.getRelative(CartUtils.relativeLeft(cartDirection)).getLocation().getBlock().equals(h.getLeashHolder().getLocation().getBlock())) {
				yMod = 0;
//				if (Cart.config.getBoolean("debugMode")) {
//					Bukkit.getServer().broadcastMessage("Nvm, it's 0 - 2");
//				}
			}
			alreadyChanged = true;
		}
		
		if (!airBlocks.contains(newAxle.getRelative(CartUtils.relativeRight(cartDirection)).getTypeId())) {
			yMod = 1;
//			if (Cart.config.getBoolean("debugMode")) {
//				Bukkit.getServer().broadcastMessage("Nvm, it's 0 - 2");
//			}
		}
		
		if ((!airBlocks.contains(newHitch.getTypeId())) 
				&& (airBlocks.contains(newHitch.getRelative(BlockFace.UP).getTypeId())) 
				&& (!newHitch.equals(hitch.getRelative(cartDirection).getRelative(relativeLeft(cartDirection)))) 
				&& (!newHitch.equals(hitch.getRelative(cartDirection).getRelative(relativeRight(cartDirection))))) {
			yMod = 1;
//			if (Cart.config.getBoolean("debugMode")) {
//				Bukkit.getServer().broadcastMessage("Y mod 1 - 1");
//			}
		}
		
		if ((airBlocks.contains(belowHitch.getRelative(cartDirection.getOppositeFace()).getTypeId())) 
				&& (airBlocks.contains(belowHitch.getRelative(CartUtils.relativeLeft(cartDirection)).getTypeId())) 
				&& (airBlocks.contains(belowHitch.getRelative(CartUtils.relativeRight(cartDirection)).getTypeId())) 
				&& (airBlocks.contains(belowHitch.getTypeId()))) {
			yMod = -1;
//			if (Cart.config.getBoolean("debugMode")) {
//				Bukkit.getServer().broadcastMessage("Y mod -1");
//			}
		}
		
		newHitch = hitch.getRelative(xMod, yMod, zMod);
		newAxle = newHitch.getRelative(cartDirection);
		aboveHitch = newAxle.getRelative(BlockFace.UP);
		belowHitch = newAxle.getRelative(BlockFace.DOWN);
		
		if (yMod > 0) {
			if ((!airBlocks.contains(aboveHitch.getTypeId())) || 
					(!airBlocks.contains(aboveHitch.getRelative(cartDirection.getOppositeFace()).getTypeId())) || 
					(!airBlocks.contains(aboveHitch.getRelative(CartUtils.relativeLeft(cartDirection)).getTypeId())) || 
					(!airBlocks.contains(aboveHitch.getRelative(CartUtils.relativeRight(cartDirection)).getTypeId())) || 
					(!airBlocks.contains(aboveHitch.getRelative(BlockFace.UP).getTypeId()))) {
//				if (Cart.config.getBoolean("debugMode")) {
//					Bukkit.getServer().broadcastMessage("Return false due");
//					return false;
//				}
			}
		}
		
		/*
		 * TODO:
		 * Need to see if this section is important or not. 
		 * It doesn't look like it actually does anything in the old code.
		 */
		//) || (!airBlocks.contains(newAxle.getTypeId()))
		
//		if (yMod == 0) {
//			if (HorseCart.cartsCooldown.contains((Player) h.getPassenger())) {
//				HorseCart.cartsCooldown.remove((Player) h.getPassenger());
//			}
//			
//			if (yMod >=0) {
//				if ((!airBlocks.contains(newHitch.getTypeId()) || ((!airBlocks.contains(newAxle.getRelative(relativeLeft(cartDirection)).getTypeId())) 
//						&& (!newAxle.getRelative(relativeLeft(cartDirection)).equals(h.getLeashHolder().getLocation().getBlock()))) || 
//						((!airBlocks.contains(newAxle.getRelative(relativeRight(cartDirection)).getTypeId())) 
//						&& (!newAxle.getRelative(relativeRight(cartDirection)).equals(h.getLeashHolder().getLocation().getBlock()))) || 
//						(!airBlocks.contains(newAxle.getRelative(BlockFace.UP).getTypeId())))) {
//					return false;
//				}
//			}
//			
//			Block axle = hitch.getRelative(cartDirection);
//			newHitch.setTypeId(hitch.getTypeId());
//			hitch.setTypeId(0);
//			Block trapDoor = newAxle.getRelative(CartUtils.relativeLeft(cartDirection));
//			trapDoor.setTypeId(96);
//			BlockState state = trapDoor.getState();
//            TrapDoor door = (TrapDoor) state.getData();
//            door.setOpen(true);
//            door.setFacingDirection(CartUtils.relativeLeft(cartDirection));
//            state.update();
//			axle.getRelative(CartUtils.relativeLeft(cartDirection)).setTypeId(0);
//			Block trapDoor2 = newAxle.getRelative(CartUtils.relativeRight(cartDirection));
//			trapDoor2.setTypeId(96);
//			BlockState state2 = trapDoor2.getState();
//            TrapDoor door2 = (TrapDoor) state2.getData();
//            door2.setOpen(true);
//            door2.setFacingDirection(CartUtils.relativeRight(cartDirection));
//            state2.update();
//			axle.getRelative(CartUtils.relativeRight(cartDirection)).setTypeId(0);
//			
//			Block chestBlock = hitch.getRelative(cartDirection).getRelative(BlockFace.UP);
//			Chest chest = (Chest) chestBlock.getState();
//			Inventory chestInv = chest.getBlockInventory();
//			
//			Block newChestBlock = newHitch.getRelative(cartDirection).getRelative(BlockFace.UP);
//			newChestBlock.setTypeId(54);
//			Chest newChest = (Chest) newChestBlock.getState();
//			Inventory newChestInv = newChest.getBlockInventory();
//			newChestInv.setContents(chestInv.getContents());
//			chestInv.clear();
//			chestBlock.setTypeId(0);
//			BlockState blockState = newChestBlock.getState();
//			org.bukkit.material.Chest directionChest = new org.bukkit.material.Chest(cartDirection);
//			newChestBlock.setData(directionChest.getData());
//			
//			
//			newAxle.setTypeId(axle.getTypeId());
//			if ((cartDirection.equals(BlockFace.WEST)) || (cartDirection.equals(BlockFace.EAST))) {
//				newAxle.setData((byte) 1);
//			}
//			
//			if ((cartDirection.equals(BlockFace.NORTH)) || (cartDirection.equals(BlockFace.SOUTH))) {
//				newAxle.setData((byte) 0);
//			}
//			
//			axle.setTypeId(0);
//			h.getLeashHolder().remove();
//			LeashHitch leashHitch = (LeashHitch) h.getWorld().spawnEntity(newHitch.getLocation(), EntityType.LEASH_HITCH);
//			
//			h.setLeashHolder(leashHitch);
//			
//			return true;
//			
//			if (Cart.config.getBoolean("debugMode")) {
//				Bukkit.getServer().broadcastMessage("Y mod 0");
//			}
//		}
		return false;
	}
}
