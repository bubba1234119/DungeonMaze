package com.timvisee.dungeonmaze.populator.maze.structure;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Furnace;
import org.bukkit.inventory.ItemStack;

import com.timvisee.dungeonmaze.DungeonMaze;
import com.timvisee.dungeonmaze.config.DMConfigHandler;
import com.timvisee.dungeonmaze.event.generation.DMGenerationChestEvent;
import com.timvisee.dungeonmaze.populator.maze.DMMazeRoomBlockPopulator;
import com.timvisee.dungeonmaze.populator.maze.DMMazeRoomBlockPopulatorArgs;
import com.timvisee.dungeonmaze.populator.maze.DMMazeStructureType;
import com.timvisee.dungeonmaze.util.DMChestUtils;
import com.timvisee.dungeonmaze.util.ItemUtils;

public class AbandonedDefenceCastleRoomPopulator extends DMMazeRoomBlockPopulator {
	public static final int MIN_LAYER = 2;
	public static final int MAX_LAYER = 6;
	public static final int CHANCE_OF_CASTLE = 1; // Promile
	public static final int MOSS_ITERATIONS = 80;
	public static final int MOSS_CHANCE = 70;
	public static final int CRACKED_ITERATIONS = 80;
	public static final int CRACKED_CHANCE = 70;

	@SuppressWarnings("deprecation")
	@Override
	public void populateRoom(DMMazeRoomBlockPopulatorArgs args) {
		World W = args.getWorld();
		Chunk c = args.getSourceChunk();
		Random rand = args.getRandom();
		int x = args.getChunkX();
		int y = args.getChunkY();
		int floorOffset = args.getFloorOffset();
		int yFloor = args.getFloorY();
		int yCeiling = args.getCeilingY();
		int z = args.getChunkZ();
		
		// Apply chances
		if (rand.nextInt(1000) < CHANCE_OF_CASTLE) {
								
			// Register the room as constant room
			DungeonMaze.instance.registerConstantRoom(W.getName(), c, x, y, z);
			
			// Break out the orriginal walls
			for(int xx = 1; xx < 7; xx++) {
				for(int yy = yFloor + 1; yy <= yCeiling - 1; yy++) {
					c.getBlock(x + xx, yy, z + 0).setType(Material.AIR);
					c.getBlock(x + xx, yy, z + 7).setType(Material.AIR);
					c.getBlock(x + 0, yy, z + xx).setType(Material.AIR);
					c.getBlock(x + 7, yy, z + xx).setType(Material.AIR);
				}
			}
			
			// Walls
			for(int xx = 1; xx < 7; xx++) {
				for(int yy = floorOffset + 1; yy <= floorOffset + 2; yy++) {
					c.getBlock(x + xx, y + yy, z + 1).setType(Material.SMOOTH_BRICK);
					c.getBlock(x + xx, y + yy, z + 6).setType(Material.SMOOTH_BRICK);
					c.getBlock(x + 1, y + yy, z + xx).setType(Material.SMOOTH_BRICK);
					c.getBlock(x + 6, y + yy, z + xx).setType(Material.SMOOTH_BRICK);
				}
			}
			
			// Generate merlons
			for(int xx = 0; xx < 7; xx++) {
				c.getBlock(x + xx, yFloor + 3, z + 0).setType(Material.SMOOTH_BRICK);
				c.getBlock(x + xx, yFloor + 3, z + 7).setType(Material.SMOOTH_BRICK);
				c.getBlock(x + 0, yFloor + 3, z + xx).setType(Material.SMOOTH_BRICK);
				c.getBlock(x + 7, yFloor + 3, z + xx).setType(Material.SMOOTH_BRICK);
			}
			
			c.getBlock(x + 0, yFloor + 4, z + 1).setTypeIdAndData(Material.STEP.getId(),(byte) 5,false);
			c.getBlock(x + 0, yFloor + 4, z + 3).setTypeIdAndData(Material.STEP.getId(),(byte) 5,false);
			c.getBlock(x + 0, yFloor + 4, z + 5).setTypeIdAndData(Material.STEP.getId(),(byte) 5,false);
			c.getBlock(x + 7, yFloor + 4, z + 2).setTypeIdAndData(Material.STEP.getId(),(byte) 5,false);
			c.getBlock(x + 7, yFloor + 4, z + 4).setTypeIdAndData(Material.STEP.getId(),(byte) 5,false);
			c.getBlock(x + 7, yFloor + 4, z + 6).setTypeIdAndData(Material.STEP.getId(),(byte) 5,false);
			c.getBlock(x + 1, yFloor + 4, z + 0).setTypeIdAndData(Material.STEP.getId(),(byte) 5,false);
			c.getBlock(x + 3, yFloor + 4, z + 0).setTypeIdAndData(Material.STEP.getId(),(byte) 5,false);
			c.getBlock(x + 5, yFloor + 4, z + 0).setTypeIdAndData(Material.STEP.getId(),(byte) 5,false);
			c.getBlock(x + 2, yFloor + 4, z + 7).setTypeIdAndData(Material.STEP.getId(),(byte) 5,false);
			c.getBlock(x + 4, yFloor + 4, z + 7).setTypeIdAndData(Material.STEP.getId(),(byte) 5,false);
			c.getBlock(x + 6, yFloor + 4, z + 7).setTypeIdAndData(Material.STEP.getId(),(byte) 5,false);
			
			// Place torches
			c.getBlock(x + 1, yFloor + 3, z + 1).setTypeIdAndData(Material.TORCH.getId(),(byte) 5,false);
			c.getBlock(x + 1, yFloor + 3, z + 6).setTypeIdAndData(Material.TORCH.getId(),(byte) 5,false);
			c.getBlock(x + 6, yFloor + 3, z + 1).setTypeIdAndData(Material.TORCH.getId(),(byte) 5,false);
			c.getBlock(x + 6, yFloor + 3, z + 6).setTypeIdAndData(Material.TORCH.getId(),(byte) 5,false);
			c.getBlock(x + 6, yFloor + 3, z + 6).setTypeIdAndData(Material.TORCH.getId(),(byte) 5,false);
			
			// Place ladders
			c.getBlock(x + 2, yFloor + 1, z + 5).setTypeIdAndData(Material.LADDER.getId(),(byte) 2,false);
			c.getBlock(x + 2, yFloor + 2, z + 5).setTypeIdAndData(Material.LADDER.getId(),(byte) 2,false);
			
			// Place crafting table, chests and furnaces
			c.getBlock(x + 2, yFloor + 1, z + 2).setType(Material.WORKBENCH);
			c.getBlock(x + 5, yFloor + 1, z + 2).setTypeIdAndData(Material.CHEST.getId(),(byte) 2,false);
			
			//Call the Chest generation event
			DMGenerationChestEvent event = new DMGenerationChestEvent(c.getBlock(x + 5, yFloor + 1, z + 2), rand, genChestContent(rand), DMMazeStructureType.ABANDONED_DEFENCE_CASTLE_ROOM);
			Bukkit.getServer().getPluginManager().callEvent(event);
			
			// Do the event
			if(!event.isCancelled()) {
				// Make sure the chest is still there, a developer could change the chest through the event!
				if(event.getBlock().getType() == Material.CHEST)
				// Add the contents to the chest
				DMChestUtils.addItemsToChest(event.getBlock(), event.getContents(), !event.getAddContentsInOrder(), rand);
			}

			c.getBlock(x + 5, yFloor + 1, z + 3).setTypeIdAndData(Material.CHEST.getId(),(byte) 2,false);
			
			//Call the Chest generation event
			DMGenerationChestEvent event2 = new DMGenerationChestEvent(c.getBlock(x + 5, yFloor + 1, z + 3), rand, genChestContent(rand), DMMazeStructureType.ABANDONED_DEFENCE_CASTLE_ROOM);
			Bukkit.getServer().getPluginManager().callEvent(event2);
			
			// Do the event
			if(!event2.isCancelled()) {
				// Make sure the chest is still there, a developer could change the chest through the event!
				if(event2.getBlock().getType() == Material.CHEST)
				// Add the contents to the chest
				DMChestUtils.addItemsToChest(event.getBlock(), event.getContents(), !event.getAddContentsInOrder(), rand);
			}
			
			c.getBlock(x + 5, yFloor + 1, z + 4).setType(Material.FURNACE);
			c.getBlock(x + 5, yFloor + 1, z + 4).setData((byte) 4);
			addItemsToFurnace(rand, (Furnace) c.getBlock(x + 5, yFloor + 1, z + 4).getState());
			c.getBlock(x + 5, yFloor + 1, z + 5).setType(Material.FURNACE);
			c.getBlock(x + 5, yFloor + 1, z + 5).setData((byte) 4);
			addItemsToFurnace(rand, (Furnace) c.getBlock(x + 5, yFloor + 1, z + 5).getState());
			
			// Place cake (with random pieces eaten)
			c.getBlock(x + 5, yFloor + 2, z + 5).setType(Material.CAKE_BLOCK);
			c.getBlock(x + 5, yFloor + 2, z + 5).setData((byte) rand.nextInt(4));
			
			// TODO: Place painting
			
			// Place some cobweb
			c.getBlock(x + 2, yFloor + 2, z + 2).setType(Material.WEB);
			c.getBlock(x + 3, yFloor + 1, z + 2).setType(Material.WEB);
			c.getBlock(x + 6, yFloor + 3, z + 6).setType(Material.WEB);
			c.getBlock(x + 6, yFloor + 4, z + 6).setType(Material.WEB);
			c.getBlock(x + 5, yFloor + 3, z + 6).setType(Material.WEB);
			c.getBlock(x + 6, yFloor + 3, z + 5).setType(Material.WEB);
			c.getBlock(x + 0, yFloor + 4, z + 6).setType(Material.WEB);

			// Add some moss and cracked stone bricks
			for (int i = 0; i < MOSS_ITERATIONS; i++) {
				if (rand.nextInt(100) < MOSS_CHANCE) {

					Block block = c.getBlock(x + rand.nextInt(8), rand.nextInt((y + 6) - y + 1) + y, z + rand.nextInt(8));
					if (block.getType() == Material.COBBLESTONE)
						block.setType(Material.MOSSY_COBBLESTONE);

					if (block.getType() == Material.SMOOTH_BRICK)
						block.setData((byte) 1);
				}
			}

			for (int i = 0; i < CRACKED_ITERATIONS; i++) {
				if (rand.nextInt(100) < CRACKED_CHANCE) {

					Block block = c.getBlock(x + rand.nextInt(8), rand.nextInt((y + 6) - y + 1) + y, z + rand.nextInt(8));
					if (block.getType() == Material.SMOOTH_BRICK)
						block.setData((byte) 2);
				}
			}
		}
	}

	public void addItemsToFurnace(Random random, Furnace furnace) {
		List<ItemStack> items = new ArrayList<ItemStack>();

		for(int j = 0; j < DMConfigHandler.itemsUncommon.size(); j++)
		{
			int next = random.nextInt(1000);
			random.setSeed(System.nanoTime() + System.currentTimeMillis() + next);
			if(random.nextInt(100) < 50)
			{
				String [] uncommon = DMConfigHandler.itemsUncommon.get(j).split(" ");
				items.add(ItemUtils.parseItem(uncommon));
			}
		}

		for(int h = 0; h < DMConfigHandler.itemsRare.size(); h++)
		{
			int next = random.nextInt(100);
			random.setSeed(System.currentTimeMillis() + System.currentTimeMillis() + next + 100);
			if(random.nextInt(100) < 30)
			{
				String [] rare = DMConfigHandler.itemsRare.get(h).split(" ");
				items.add(ItemUtils.parseItem(rare));
			}
		}

		// Add the selected items into the furnace
		if(items.size() > 0)
		{
			if(random.nextInt(100) < 70)
				furnace.getInventory().setResult(items.get(random.nextInt(items.size())));
		}
		furnace.update();
	}

	public List<ItemStack> genChestContent(Random random) {
		List<ItemStack> items = new ArrayList<ItemStack>();
		for(int j = 0; j < DMConfigHandler.itemsUncommon.size(); j++)
		{
			int next = random.nextInt(1000);
			random.setSeed(System.nanoTime() + System.currentTimeMillis() + next);
			if(random.nextInt(100) < 50)
			{
				String [] uncommon = DMConfigHandler.itemsUncommon.get(j).split(" ");
				items.add(ItemUtils.parseItem(uncommon));
			}
		}


		int itemCountInChest = 3;
		switch (random.nextInt(8)) {
		case 0:
			itemCountInChest = 2;
			break;
		case 1:
			itemCountInChest = 2;
			break;
		case 2:
			itemCountInChest = 3;
			break;
		case 3:
			itemCountInChest = 3;
			break;
		case 4:
			itemCountInChest = 3;
			break;
		case 5:
			itemCountInChest = 4;
			break;
		case 6:
			itemCountInChest = 4;
			break;
		case 7:
			itemCountInChest = 5;
			break;
		default:
			itemCountInChest = 3;
			break;
		}

		List<ItemStack> result = new ArrayList<ItemStack>();

		if(items.size() > 0)
		{
			for (int i = 0; i < itemCountInChest; i++)
			{
				result.add(items.get(random.nextInt(items.size())));
			}
		}
		else
		{
			String [] defaultChestItem = DMConfigHandler.defaultItem.split(" ");
			result.add(ItemUtils.parseItem(defaultChestItem));
		}
		return result;
	}

	/**
	 * Get the minimum layer
	 * @return Minimum layer
	 */
	@Override
	public int getMinimumLayer() {
		return MIN_LAYER;
	}

	/**
	 * Get the maximum layer
	 * @return Maximum layer
	 */
	@Override
	public int getMaximumLayer() {
		return MAX_LAYER;
	}
}