package com.timvisee.dungeonmaze.populator.maze.decoration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.inventory.ItemStack;

import com.timvisee.dungeonmaze.config.DMConfigHandler;
import com.timvisee.dungeonmaze.event.generation.DMGenerationChestEvent;
import com.timvisee.dungeonmaze.populator.maze.DMMazeRoomBlockPopulator;
import com.timvisee.dungeonmaze.populator.maze.DMMazeRoomBlockPopulatorArgs;
import com.timvisee.dungeonmaze.populator.maze.DMMazeStructureType;
import com.timvisee.dungeonmaze.util.DMChestUtils;
import com.timvisee.dungeonmaze.util.ItemUtils;

public class ChestPopulator extends DMMazeRoomBlockPopulator {
	public static final int MIN_LAYER = 1;
	public static final int MAX_LAYER = 7;
	public static final int CHANCE_OF_CHEST = 3;
	public static final double CHANCE_OF_CHEST_ADDITION_PER_LEVEL = -0.333; // to 1


	@Override
	public void populateRoom(DMMazeRoomBlockPopulatorArgs args) {
		Chunk c = args.getSourceChunk();
		Random rand = args.getRandom();
		int x = args.getChunkX();
		int y = args.getChunkY();
		int z = args.getChunkZ();
		
		// Calculate chances
		if (rand.nextInt(100) < CHANCE_OF_CHEST + (CHANCE_OF_CHEST_ADDITION_PER_LEVEL * (y - 30) / 6)) {
			
			int chestX = x + rand.nextInt(6) + 1;
			int chestY = args.getFloorY() + 1;
			int chestZ = z + rand.nextInt(6) + 1;

			if(!(c.getBlock(chestX, chestY - 1, chestZ).getType() == Material.AIR)) {
				Block chestBlock = c.getBlock(chestX, chestY, chestZ);
				if(chestBlock.getType() == Material.AIR) {
					
					// Generate new inventory contents
					List<ItemStack> contents = generateChestContents(rand);
					chestBlock.setType(Material.CHEST);
					
					// Call the chest generation event
					DMGenerationChestEvent event = new DMGenerationChestEvent(chestBlock, rand, contents, DMMazeStructureType.UNSTRUCTURE);
					Bukkit.getServer().getPluginManager().callEvent(event);
					
					// Do the event
					if(!event.isCancelled()) {
						// Make sure the chest is still there, a developer could change the chest through the event!
						if(event.getBlock().getType() != Material.CHEST)
							return;
						
						// Add the contents to the chest
						DMChestUtils.addItemsToChest(event.getBlock(), event.getContents(), !event.getAddContentsInOrder(), rand);
					} else {
						// The event is cancelled
						// Put the chest back to it's orrigional state (air)
						chestBlock.setType(Material.AIR);
					}
					
				} else if (chestBlock.getType() == Material.CHEST) {
					// The follow is for rare case when the chest is generate before the plugin does the event
					Chest chest = (Chest) chestBlock.getState();
					if (chest.getInventory() != null) {
						// Generate new inventory contents
						List<ItemStack> contents = generateChestContents(rand);
					
						// Call the chest generation event
						DMGenerationChestEvent event = new DMGenerationChestEvent(chestBlock, rand, contents, DMMazeStructureType.UNSTRUCTURE);
						Bukkit.getServer().getPluginManager().callEvent(event);
						
						// Do the event
						if(!event.isCancelled()) {
							// Make sure the chest is still there, a developer could change the chest through the event!
							if(event.getBlock().getType() != Material.CHEST)
								return;
							
							// Add the contents to the chest
							DMChestUtils.addItemsToChest(event.getBlock(), event.getContents(), !event.getAddContentsInOrder(), rand);
						}
					}
				}
			}
		}
	}



	
	public List<ItemStack> generateChestContents(Random random) {
		// TODO: Use class for this, to also add feature to re loot chests
		List<ItemStack> items = new ArrayList<ItemStack>();
		
		for(int i = 0; i < DMConfigHandler.itemsCommon.size(); i++)
		{
			int next = random.nextInt(100);
			random.setSeed(System.nanoTime() + next + 30 + (System.nanoTime() / 2));
			if(random.nextInt(100) < 80)
			{
				String [] common = DMConfigHandler.itemsCommon.get(i).split(" ");
				items.add(ItemUtils.parseItem(common));
			}
		}
		
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
			if(random.nextInt(100) < 10)
			{
				String [] rare = DMConfigHandler.itemsRare.get(h).split(" ");
				items.add(ItemUtils.parseItem(rare));
			}
		}
		
		for(int a = 0; a < DMConfigHandler.itemsEpic.size(); a++)
		{
			int next = random.nextInt(30);
			random.setSeed(System.currentTimeMillis() + System.nanoTime() + System.currentTimeMillis() + next);
			if(random.nextInt(100) < 5)
			{
				String [] epic = DMConfigHandler.itemsEpic.get(a).split(" ");
				items.add(ItemUtils.parseItem(epic));
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
		}
		
		// Create a list of item contents with the right amount of items
		List<ItemStack> newContents = new ArrayList<ItemStack>();
	if(items.size() > 0)
	{
		for (int k = 0; k < itemCountInChest; k++)
		{
			newContents.add(items.get(random.nextInt(items.size())));
		}
	}
	else
	{
		String [] defaultChestItem = DMConfigHandler.defaultItem.split(" ");
		newContents.add(ItemUtils.parseItem(defaultChestItem));
	}
		return newContents;
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