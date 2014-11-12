package com.timvisee.dungeonmaze.populator.maze.decoration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.inventory.ItemStack;

import com.timvisee.dungeonmaze.DungeonMaze;
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
	private final transient Pattern splitPattern = Pattern.compile("((.*)[:+',;.](\\d+))");
	private final transient Map<String, Integer> items = new HashMap<String, Integer>();
	private final transient Map<String, Short> durabilities = new HashMap<String, Short>();

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
		for(int i = 0; i < DungeonMaze.instance.getConfigHandler().itemsCommon.size(); i++)
		{
			if(random.nextInt(100) < 80)
			{
				String [] common = DungeonMaze.instance.getConfigHandler().itemsCommon.get(i).split(" ");
				items.add(ItemUtils.parseItem(common));
			}
		}
		for(int j = 0; j < DungeonMaze.instance.getConfigHandler().itemsUncommon.size(); j++)
		{
			if(random.nextInt(100) < 50)
			{
				String [] uncommon = DungeonMaze.instance.getConfigHandler().itemsUncommon.get(j).split(" ");
				items.add(ItemUtils.parseItem(uncommon));
			}
		}
		for(int h = 0; h < DungeonMaze.instance.getConfigHandler().itemsRare.size(); h++)
		{
			if(random.nextInt(100) < 30)
			{
				String [] rare = DungeonMaze.instance.getConfigHandler().itemsRare.get(h).split(" ");
				items.add(ItemUtils.parseItem(rare));
			}
		}
		for(int a = 0; a < DungeonMaze.instance.getConfigHandler().itemsRare.size(); a++)
		{
			if(random.nextInt(100) < 5)
			{
				String [] epic = DungeonMaze.instance.getConfigHandler().itemsRare.get(a).split(" ");
				items.add(ItemUtils.parseItem(epic));
			}
		}
		/*if(random.nextInt(100) < 80)
			items.add(new ItemStack(50, 4, (short) 0));
		if(random.nextInt(100) < 40)
			items.add(new ItemStack(50, 8, (short) 0));
		if(random.nextInt(100) < 20)
			items.add(new ItemStack(50, 12, (short) 0));
		if(random.nextInt(100) < 40)
			items.add(new ItemStack(260, 1, (short) 0));
		if(random.nextInt(100) < 10)
			items.add(new ItemStack(262, 16, (short) 0));
		if(random.nextInt(100) < 5)
			items.add(new ItemStack(262, 24, (short) 0));
		if(random.nextInt(100) < 20)
			items.add(new ItemStack(264, 1, (short) 0));
		if(random.nextInt(100) < 50)
			items.add(new ItemStack(265, 1, (short) 0));
		if(random.nextInt(100) < 60)
			items.add(new ItemStack(266, 1, (short) 0));
		if(random.nextInt(100) < 10)
			items.add(new ItemStack(267, 1, (short) 0));
		if(random.nextInt(100) < 40)
			items.add(new ItemStack(268, 1, (short) 0));
		if(random.nextInt(100) < 20)
			items.add(new ItemStack(272, 1, (short) 0));
		if(random.nextInt(100) < 80)
			items.add(new ItemStack(296, 1, (short) 0));
		if(random.nextInt(100) < 10)
			items.add(new ItemStack(296, 2, (short) 0));
		if(random.nextInt(100) < 5)
			items.add(new ItemStack(296, 3, (short) 0));
		if(random.nextInt(100) < 20)
			items.add(new ItemStack(297, 1, (short) 0));
		if(random.nextInt(100) < 20)
			items.add(new ItemStack(298, 1, (short) 0));
		if(random.nextInt(100) < 20)
			items.add(new ItemStack(299, 1, (short) 0));
		if(random.nextInt(100) < 20)
			items.add(new ItemStack(300, 1, (short) 0));
		if(random.nextInt(100) < 20)
			items.add(new ItemStack(301, 1, (short) 0));
		if(random.nextInt(100) < 40)
			items.add(new ItemStack(302, 1, (short) 0));
		if(random.nextInt(100) < 40)
			items.add(new ItemStack(303, 1, (short) 0));
		if(random.nextInt(100) < 40)
			items.add(new ItemStack(304, 1, (short) 0));
		if(random.nextInt(100) < 40)
			items.add(new ItemStack(305, 1, (short) 0));
		if(random.nextInt(100) < 10)
			items.add(new ItemStack(306, 1, (short) 0));
		if(random.nextInt(100) < 10)
			items.add(new ItemStack(307, 1, (short) 0));
		if(random.nextInt(100) < 10)
			items.add(new ItemStack(308, 1, (short) 0));
		if(random.nextInt(100) < 10)
			items.add(new ItemStack(309, 1, (short) 0));
		if(random.nextInt(100) < 30)
			items.add(new ItemStack(318, 3, (short) 0));
		if(random.nextInt(100) < 20)
			items.add(new ItemStack(318, 5, (short) 0));
		if(random.nextInt(100) < 10)
			items.add(new ItemStack(318, 7, (short) 0));
		if(random.nextInt(100) < 80)
			items.add(new ItemStack(319, 1, (short) 0));
		if(random.nextInt(100) < 10)
			items.add(new ItemStack(320, 1, (short) 0));
		if(random.nextInt(100) < 15)
			items.add(new ItemStack(331, 5, (short) 0));
		if(random.nextInt(100) < 10)
			items.add(new ItemStack(331, 8, (short) 0));
		if(random.nextInt(100) < 5)
			items.add(new ItemStack(331, 13, (short) 0));
		if(random.nextInt(100) < 3)
			items.add(new ItemStack(331, 21, (short) 0));
		if(random.nextInt(100) < 10)
			items.add(new ItemStack(345, 1, (short) 0));
		if(random.nextInt(100) < 80)
			items.add(new ItemStack(349, 1, (short) 0));
		if(random.nextInt(100) < 20)
			items.add(new ItemStack(350, 1, (short) 0));
		if(random.nextInt(100) < 20)
			items.add(new ItemStack(351, 1, (short) 3));
		if(random.nextInt(100) < 5)
			items.add(new ItemStack(354, 1, (short) 0));
		if(random.nextInt(100) < 80)
			items.add(new ItemStack(357, 3, (short) 0));
		if(random.nextInt(100) < 20)
			items.add(new ItemStack(357, 5, (short) 0));*/
		
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
		for (int i = 0; i < itemCountInChest; i++)
			newContents.add(items.get(random.nextInt(items.size())));
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