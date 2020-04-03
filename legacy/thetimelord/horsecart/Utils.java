package me.thetimelord.horsecart;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.LeashHitch;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.material.TrapDoor;

public class Utils {
	private static final Set<Integer> fence = new HashSet<Integer>(Arrays.asList(85,113, 188, 189, 190, 191, 192));
	private static final Set<Integer> fencegate = new HashSet<Integer>(Arrays.asList(107, 183, 184, 185, 186, 187));
	private static final Set<Integer> airBlocks = new HashSet<Integer>(Arrays.asList(0,31,32,37,38,39,40,51,78,106,111,175));
	public static BlockFace relativeRight(BlockFace blockFace)
	{
		if (blockFace.equals(BlockFace.NORTH))
		{
			return BlockFace.EAST;
		}
		if (blockFace.equals(BlockFace.EAST))
		{
			return BlockFace.SOUTH;
		}
		if (blockFace.equals(BlockFace.SOUTH))
		{
			return BlockFace.WEST;
		}
		if (blockFace.equals(BlockFace.WEST))
		{
			return BlockFace.NORTH;
		}
		return null;
	}
	public static BlockFace relativeLeft(BlockFace blockFace)
	{
		if (blockFace.equals(BlockFace.NORTH))
		{
			return BlockFace.WEST;
		}
		if (blockFace.equals(BlockFace.EAST))
		{
			return BlockFace.NORTH;
		}
		if (blockFace.equals(BlockFace.SOUTH))
		{
			return BlockFace.EAST;
		}
		if (blockFace.equals(BlockFace.WEST))
		{
			return BlockFace.SOUTH;
		}
		return null;
	}
	
	public static boolean isFence(int typeId){
		if (fence.contains(typeId))
		{
			return true;
		}
		return false;
	}
	public static boolean isFencegate(int typeId){
		if (fencegate.contains(typeId))
		{
			return true;
		}
		return false;
	}
	public static BlockFace isCart(Horse h){
		Entity leashHolder = h.getLeashHolder();
		if (leashHolder instanceof LeashHitch)
		{
			Block hitch = leashHolder.getLocation().getBlock();
			BlockFace cartDirection = null;
			Block axle = null;
			if (Utils.isFencegate(hitch.getRelative(BlockFace.NORTH).getTypeId()))
			{
				cartDirection = BlockFace.NORTH;
				axle = hitch.getRelative(BlockFace.NORTH);
			}
			if (Utils.isFencegate(hitch.getRelative(BlockFace.EAST).getTypeId()))
			{
				cartDirection = BlockFace.EAST;
				axle = hitch.getRelative(BlockFace.EAST);
			}
			if (Utils.isFencegate(hitch.getRelative(BlockFace.SOUTH).getTypeId()))
			{
				cartDirection = BlockFace.SOUTH;
				axle = hitch.getRelative(BlockFace.SOUTH);
			}
			if (Utils.isFencegate(hitch.getRelative(BlockFace.WEST).getTypeId()))
			{
				cartDirection = BlockFace.WEST;
				axle = hitch.getRelative(BlockFace.WEST);
			}
			if ((cartDirection != null) && (axle != null))
			{
				if ((axle.getRelative(Utils.relativeLeft(cartDirection)).getTypeId() == 96) && (axle.getRelative(Utils.relativeRight(cartDirection)).getTypeId() == 96))
				{
					if (axle.getRelative(BlockFace.UP).getTypeId() == 54)
					{
						return cartDirection;
					}
				}
			}
		}
		return null;
	}
	
	public static boolean turnCart(Horse h, BlockFace newDirection){
		BlockFace oldCartDirection = isCart(h);
		if (oldCartDirection != null)
		{
			Block oldHitch = h.getLeashHolder().getLocation().getBlock();
			Block axle = oldHitch.getRelative(oldCartDirection);
			
			if ((newDirection.equals(BlockFace.WEST)) || (newDirection.equals(BlockFace.EAST)))
			{
				axle.setData((byte) 1);
			}
			if ((newDirection.equals(BlockFace.NORTH)) || (newDirection.equals(BlockFace.SOUTH)))
			{
				axle.setData((byte) 0);
			}
			
			Block newHitch = axle.getRelative(newDirection.getOppositeFace());
			if ((oldCartDirection.getOppositeFace().equals(newDirection)))
			{
				if (!airBlocks.contains(newHitch.getTypeId()))
				{
					return false;
				}
			}
			newHitch.setTypeId(oldHitch.getTypeId());
			//Bukkit.getServer().broadcastMessage(Integaer.toString(oldHitch.getTypeId()));
			oldHitch.setTypeId(0);
			if (((relativeLeft(oldCartDirection)).equals(newDirection)) || ((relativeRight(oldCartDirection)).equals(newDirection)))
			{
				Block axleRelative = axle.getRelative(newDirection);
				if (!isFence(axleRelative.getTypeId()))
				{
					axleRelative.setTypeId(0);
				}
				
				axleRelative = axle.getRelative(newDirection.getOppositeFace());
				
				if (!isFence(axleRelative.getTypeId()))
				{
					axleRelative.setTypeId(0);
				}
				
				Block trapDoor = axle.getRelative(Utils.relativeLeft(newDirection));
				trapDoor.setTypeId(96);
				BlockState state = trapDoor.getState();
	            TrapDoor door = (TrapDoor) state.getData();
	            door.setOpen(true);
	            door.setFacingDirection(Utils.relativeLeft(newDirection));
	            state.update();
				Block trapDoor2 = axle.getRelative(Utils.relativeRight(newDirection));
				trapDoor2.setTypeId(96);
				BlockState state2 = trapDoor2.getState();
	            TrapDoor door2 = (TrapDoor) state2.getData();
	            door2.setOpen(true);
	            door2.setFacingDirection(Utils.relativeRight(newDirection));
	            state2.update();
			}
			Block chestBlock = axle.getRelative(BlockFace.UP);
			BlockState blockState = chestBlock.getState();
			org.bukkit.material.Chest directionChest = new org.bukkit.material.Chest(newDirection);
			chestBlock.setData(directionChest.getData());
			
			h.getLeashHolder().remove();
			LeashHitch leashHitch = (LeashHitch) h.getWorld().spawnEntity(newHitch.getLocation(), EntityType.LEASH_HITCH);
			
			h.setLeashHolder(leashHitch);
		}
		
		return false;
	}
	
	public static BlockFace XZtoBlockFace(double x, double z){
		if (z > 0)
		{
			return BlockFace.NORTH;
		}
		if (z < 0)
		{
			return BlockFace.SOUTH;
		}
		if (x > 0)
		{
			return BlockFace.WEST;
		}
		if (x < 0)
		{
			return BlockFace.EAST;
		}
		
		return null;
	}
	
	public static boolean cartMove(Block hitch, double xDirection, double zDirection, BlockFace cartDirection, Horse h) {	
		int xMod = 0;
		int yMod = 0;
		int zMod = 0;
		
		if (xDirection > 0.5)
		{
			xMod = 1;
		}
		if (xDirection < -0.5)
		{
			xMod = -1;
		}
		if (zDirection > 0.5)
		{
			zMod = 1;
		}
		if (zDirection < -0.5)
		{
			zMod = -1;
		}
		
		BlockFace newDirection = null;
		if (Math.abs(xDirection) >= (Math.abs(zDirection)))
		{
			//Bukkit.getServer().broadcastMessage("YESSSSS -1");
			newDirection = XZtoBlockFace(xDirection,0);
		}
		
		if (Math.abs(zDirection) > (Math.abs(xDirection)))
		{
			//Bukkit.getServer().broadcastMessage("YESSSSS -2");
			newDirection = XZtoBlockFace(0,zDirection);
		}
		
		if (!(cartDirection.equals(newDirection)))
		{
			//Bukkit.getServer().broadcastMessage("YESSSSS");
			if (!HorseCart.cartsCooldown.contains((Player) h.getPassenger()))
			{
				turnCart(h, newDirection);
				HorseCart.cartsCooldown.add((Player) h.getPassenger());
				return true;
			}
		}
		//cartDirection = newDirection;
		
		Block newHitch = hitch.getRelative(xMod, yMod, zMod);
		Block newAxle = newHitch.getRelative(cartDirection);
		Block aboveHitch = newAxle.getRelative(BlockFace.UP);
		Block belowHitch = newAxle.getRelative(BlockFace.DOWN);
		//Bukkit.getServer().broadcastMessage(cartDirection.toString());
		//Bukkit.getServer().broadcastMessage(cartDirection.getOppositeFace().toString());
		//Bukkit.getServer().broadcastMessage(Integer.toString(hitch.getRelative(cartDirection.getOppositeFace()).getTypeId()));
		
		if (!airBlocks.contains(newAxle.getRelative(Utils.relativeLeft(cartDirection)).getTypeId()))
		{
			yMod = 1;
			//Bukkit.getServer().broadcastMessage("Y mod 1 - 2");
			if (newAxle.getRelative(Utils.relativeLeft(cartDirection)).getLocation().getBlock().equals(h.getLeashHolder().getLocation().getBlock()))
			{
				//Bukkit.getServer().broadcastMessage("Nvm, it's 0 - 1");
				yMod = 0;
			}
		}
		
		if (!airBlocks.contains(newAxle.getRelative(Utils.relativeRight(cartDirection)).getTypeId()))
		{
			yMod = 1;
			//Bukkit.getServer().broadcastMessage("Y mod 1 - 3");
			if (newAxle.getRelative(Utils.relativeRight(cartDirection)).getLocation().getBlock().equals(h.getLeashHolder().getLocation().getBlock()))
			{
				//Bukkit.getServer().broadcastMessage("Nvm, it's 0 - 2");
				yMod = 0;
			}
		}
		
		if ((!airBlocks.contains(newHitch.getTypeId())) && (airBlocks.contains(newHitch.getRelative(BlockFace.UP).getTypeId())) && (!newHitch.equals(hitch.getRelative(cartDirection).getRelative(relativeLeft(cartDirection)))) && (!newHitch.equals(hitch.getRelative(cartDirection).getRelative(relativeRight(cartDirection)))))
		{
			yMod = 1;
			//Bukkit.getServer().broadcastMessage("Y mod 1 - 1");
		}
		
		if ((airBlocks.contains(belowHitch.getRelative(cartDirection.getOppositeFace()).getTypeId())) && (airBlocks.contains(belowHitch.getRelative(Utils.relativeLeft(cartDirection)).getTypeId())) && (airBlocks.contains(belowHitch.getRelative(Utils.relativeRight(cartDirection)).getTypeId())) && (airBlocks.contains(belowHitch.getTypeId())))
		{
			//Bukkit.getServer().broadcastMessage("Y mod -1");
			yMod = -1;
		}
		newHitch = hitch.getRelative(xMod, yMod, zMod);
		newAxle = newHitch.getRelative(cartDirection);
		aboveHitch = newAxle.getRelative(BlockFace.UP);
		belowHitch = newAxle.getRelative(BlockFace.DOWN);
		
		if (yMod > 0)
		{
			if ((!airBlocks.contains(aboveHitch.getTypeId())) || (!airBlocks.contains(aboveHitch.getRelative(cartDirection.getOppositeFace()).getTypeId())) || (!airBlocks.contains(aboveHitch.getRelative(Utils.relativeLeft(cartDirection)).getTypeId())) || (!airBlocks.contains(aboveHitch.getRelative(Utils.relativeRight(cartDirection)).getTypeId())) || (!airBlocks.contains(aboveHitch.getRelative(BlockFace.UP).getTypeId())))
			{
				//Bukkit.getServer().broadcastMessage("Return false due");
				//return false;
			}
		}
		//) || (!airBlocks.contains(newAxle.getTypeId()))
		
		//if (yMod == 0)
		{
			//if (HorseCart.cartsCooldown.contains((Player) h.getPassenger()))
			{
				HorseCart.cartsCooldown.remove((Player) h.getPassenger());
			}
			if (yMod >=0)
			{
				if ((!airBlocks.contains(newHitch.getTypeId()) || ((!airBlocks.contains(newAxle.getRelative(relativeLeft(cartDirection)).getTypeId())) && (!newAxle.getRelative(relativeLeft(cartDirection)).equals(h.getLeashHolder().getLocation().getBlock()))) || ((!airBlocks.contains(newAxle.getRelative(relativeRight(cartDirection)).getTypeId())) && (!newAxle.getRelative(relativeRight(cartDirection)).equals(h.getLeashHolder().getLocation().getBlock()))) || (!airBlocks.contains(newAxle.getRelative(BlockFace.UP).getTypeId()))))
				{
					return false;
				}
			}
			Block axle = hitch.getRelative(cartDirection);
			//Bukkit.getServer().broadcastMessage("Y mod 0");
			newHitch.setTypeId(hitch.getTypeId());
			hitch.setTypeId(0);
			Block trapDoor = newAxle.getRelative(Utils.relativeLeft(cartDirection));
			trapDoor.setTypeId(96);
			BlockState state = trapDoor.getState();
            TrapDoor door = (TrapDoor) state.getData();
            door.setOpen(true);
            door.setFacingDirection(Utils.relativeLeft(cartDirection));
            state.update();
			axle.getRelative(Utils.relativeLeft(cartDirection)).setTypeId(0);
			Block trapDoor2 = newAxle.getRelative(Utils.relativeRight(cartDirection));
			trapDoor2.setTypeId(96);
			BlockState state2 = trapDoor2.getState();
            TrapDoor door2 = (TrapDoor) state2.getData();
            door2.setOpen(true);
            door2.setFacingDirection(Utils.relativeRight(cartDirection));
            state2.update();
			axle.getRelative(Utils.relativeRight(cartDirection)).setTypeId(0);
			
			Block chestBlock = hitch.getRelative(cartDirection).getRelative(BlockFace.UP);
			Chest chest = (Chest) chestBlock.getState();
			Inventory chestInv = chest.getBlockInventory();
			
			Block newChestBlock = newHitch.getRelative(cartDirection).getRelative(BlockFace.UP);
			newChestBlock.setTypeId(54);
			Chest newChest = (Chest) newChestBlock.getState();
			Inventory newChestInv = newChest.getBlockInventory();
			newChestInv.setContents(chestInv.getContents());
			chestInv.clear();
			chestBlock.setTypeId(0);
			BlockState blockState = newChestBlock.getState();
			org.bukkit.material.Chest directionChest = new org.bukkit.material.Chest(cartDirection);
			newChestBlock.setData(directionChest.getData());
			
			
			newAxle.setTypeId(axle.getTypeId());
			if ((cartDirection.equals(BlockFace.WEST)) || (cartDirection.equals(BlockFace.EAST)))
			{
				newAxle.setData((byte) 1);
			}
			if ((cartDirection.equals(BlockFace.NORTH)) || (cartDirection.equals(BlockFace.SOUTH)))
			{
				newAxle.setData((byte) 0);
			}
			axle.setTypeId(0);
			h.getLeashHolder().remove();
			LeashHitch leashHitch = (LeashHitch) h.getWorld().spawnEntity(newHitch.getLocation(), EntityType.LEASH_HITCH);
			
			h.setLeashHolder(leashHitch);
			
			return true;
		}
		
		
		//return false;
	}
}
