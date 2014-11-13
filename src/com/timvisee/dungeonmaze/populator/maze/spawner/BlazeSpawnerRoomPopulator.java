package com.timvisee.dungeonmaze.populator.maze.spawner;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import com.timvisee.dungeonmaze.DungeonMaze;
import com.timvisee.dungeonmaze.config.DMConfigHandler;
import com.timvisee.dungeonmaze.event.generation.DMGenerationChestEvent;
import com.timvisee.dungeonmaze.event.generation.DMGenerationSpawnerCause;
import com.timvisee.dungeonmaze.event.generation.DMGenerationSpawnerEvent;
import com.timvisee.dungeonmaze.populator.maze.DMMazeRoomBlockPopulator;
import com.timvisee.dungeonmaze.populator.maze.DMMazeRoomBlockPopulatorArgs;
import com.timvisee.dungeonmaze.populator.maze.DMMazeStructureType;
import com.timvisee.dungeonmaze.util.DMChestUtils;
import com.timvisee.dungeonmaze.util.ItemUtils;

public class BlazeSpawnerRoomPopulator extends DMMazeRoomBlockPopulator {
	public static final int MIN_LAYER = 1;
	public static final int MAX_LAYER = 4;
	public static final int CHANCE_OF_SPANWER_ROOM = 2; //Promile
	public static final double CHANCE_OF_SPANWER_ROOM_ADDITION_PER_LEVEL = -0.167; /* to 1 */
	public static final double MIN_SPAWN_DISTANCE = 5; // Chunks

	@SuppressWarnings("deprecation")
	@Override
	public void populateRoom(DMMazeRoomBlockPopulatorArgs args) {
		World w = args.getWorld();
		Chunk c = args.getSourceChunk();
		Random rand = args.getRandom();
		int x = args.getChunkX();
		int y = args.getChunkY();
		int yFloor = args.getFloorY();
		int yCeiling = args.getCeilingY();
		int z = args.getChunkZ();
		
		// Make sure the distance between the spawn and the current chunk is allowed
		if(distance(0, 0, c.getX(), c.getZ()) < MIN_SPAWN_DISTANCE)
			return;
		
		// Apply chances
		if (rand.nextInt(1000) < CHANCE_OF_SPANWER_ROOM + (CHANCE_OF_SPANWER_ROOM_ADDITION_PER_LEVEL * (y - 30) / 6)) {
			
			// Register the current room as constant room
			DungeonMaze.instance.registerConstantRoom(w.getName(), c, x, y, z);
			
			// Netherbrick floor in the bottom of the room
			for(int xx = x; xx <= x + 7; xx += 1)
	            for(int zz = z; zz <= z + 7; zz += 1)
	                c.getBlock(xx, yFloor, zz).setType(Material.NETHER_BRICK);
			
			// Cobblestone layer underneeth the stone floor
			for(int xx = x + 0; xx <= x + 7; xx += 1)
	            for(int zz = z + 1; zz <= z + 6; zz += 1)
	                c.getBlock(xx, yFloor - 1, zz).setType(Material.COBBLESTONE);
			
			// Break out the walls and things inside the room
			for (int xx = 0; xx < 8; xx++)
				for (int yy = yFloor + 1; yy < yCeiling; yy++)
					for(int zz = 0; zz < 8; zz++)
						c.getBlock(x + xx, yy, z + zz).setType(Material.AIR);
			
			// Generate corners
			for(int yy = yFloor + 1; yy < yCeiling; yy++) {
				c.getBlock(x + 0, yy, z + 0).setType(Material.NETHER_BRICK);
				c.getBlock(x + 7, yy, z + 0).setType(Material.NETHER_BRICK);
				c.getBlock(x + 0, yy, z + 7).setType(Material.NETHER_BRICK);
				c.getBlock(x + 7, yy, z + 7).setType(Material.NETHER_BRICK);
			}
			
			// Generate fences in the corners
			for(int yy = yFloor + 1; yy < yCeiling; yy++) {
				c.getBlock(x + 1, yy, z + 0).setType(Material.NETHER_FENCE);
				c.getBlock(x + 0, yy, z + 1).setType(Material.NETHER_FENCE);
				c.getBlock(x + 6, yy, z + 0).setType(Material.NETHER_FENCE);
				c.getBlock(x + 7, yy, z + 1).setType(Material.NETHER_FENCE);
				c.getBlock(x + 1, yy, z + 7).setType(Material.NETHER_FENCE);
				c.getBlock(x + 0, yy, z + 6).setType(Material.NETHER_FENCE);
				c.getBlock(x + 6, yy, z + 7).setType(Material.NETHER_FENCE);
				c.getBlock(x + 7, yy, z + 6).setType(Material.NETHER_FENCE);
			}
			
			// Generate platform in the middle
			for (int xx=x + 2; xx <= x + 5; xx+=1)
	            for (int zz=z + 2; zz <= z + 5; zz+=1)
	                c.getBlock(xx, yFloor + 1, zz).setType(Material.NETHER_BRICK);
			
			// Generate stairs off the platform
			c.getBlock(x + 3, yFloor + 1, z + 2).setTypeIdAndData(Material.NETHER_BRICK_STAIRS.getId(),(byte) 2,false);
			c.getBlock(x + 4, yFloor + 1, z + 2).setTypeIdAndData(Material.NETHER_BRICK_STAIRS.getId(),(byte) 2,false);
			
			c.getBlock(x + 3, yFloor + 1, z + 5).setTypeIdAndData(Material.NETHER_BRICK_STAIRS.getId(),(byte) 3,false);
			c.getBlock(x + 4, yFloor + 1, z + 5).setTypeIdAndData(Material.NETHER_BRICK_STAIRS.getId(),(byte) 3,false);
			
			c.getBlock(x + 2, yFloor + 1, z + 3).setTypeIdAndData(Material.NETHER_BRICK_STAIRS.getId(),(byte) 0,false);
			c.getBlock(x + 2, yFloor + 1, z + 4).setTypeIdAndData(Material.NETHER_BRICK_STAIRS.getId(),(byte) 0,false);
			
			c.getBlock(x + 5, yFloor + 1, z + 3).setTypeIdAndData(Material.NETHER_BRICK_STAIRS.getId(),(byte) 1,false);
			c.getBlock(x + 5, yFloor + 1, z + 4).setTypeIdAndData(Material.NETHER_BRICK_STAIRS.getId(),(byte) 1,false);

			// Generate poles on the platform
			c.getBlock(x + 2, yFloor + 2, z + 2).setType(Material.NETHER_FENCE);
			c.getBlock(x + 5, yFloor + 2, z + 2).setType(Material.NETHER_FENCE);
			c.getBlock(x + 2, yFloor + 2, z + 5).setType(Material.NETHER_FENCE);
			c.getBlock(x + 5, yFloor + 2, z + 5).setType(Material.NETHER_FENCE);
			
			// Generate the spawner
			if (DMConfigHandler.mobs.contains("Blaze")) {
				int spawnerX = x + 3 + rand.nextInt(2);
				int spawnerY = yFloor + 2;
				int spawnerZ = z + 3 + rand.nextInt(2);
				Block spawnerBlock = c.getBlock(spawnerX, spawnerY, spawnerZ);
				spawnerBlock = c.getBlock(spawnerX, spawnerY, spawnerZ);
				
				// Call the spawner generation event
				DMGenerationSpawnerEvent event = new DMGenerationSpawnerEvent(spawnerBlock, EntityType.BLAZE, DMGenerationSpawnerCause.BLAZE_SPAWNER_ROOM, rand);
				Bukkit.getServer().getPluginManager().callEvent(event);
				
				// Make sure the event isn't cancelled yet
				if(!event.isCancelled()) {
					// Change the block into a creature spawner
					spawnerBlock.setType(Material.MOB_SPAWNER);
					
					// Cast the created s pawner into a CreatureSpawner object
					CreatureSpawner theSpawner = (CreatureSpawner) spawnerBlock.getState();
					
					// Set the spawned type of the spawner
					theSpawner.setSpawnedType(event.getSpawnedType());
				}
			}
		
			// Generate hidden content/recourses underneath the platform
			// Generate a list of chest contents
			List<ItemStack> contents = generateChestContents(rand);
			Block chest1 = c.getBlock(x + 3, yFloor, z + 3);

			// Call the chest generation event
			DMGenerationChestEvent event1 = new DMGenerationChestEvent(chest1, rand, contents, DMMazeStructureType.BLAZE_SPAWNER_ROOM);
			Bukkit.getServer().getPluginManager().callEvent(event1);
			
			// Spawn the chest unless the even is cancelled
			if(!event1.isCancelled()) {
				// Make sure the chest is still there, a developer could change the chest through the event!
				if(event1.getBlock().getType() != Material.CHEST)
					return;
				
				// Add the contents to the chest
				DMChestUtils.addItemsToChest(event1.getBlock(), event1.getContents(), !event1.getAddContentsInOrder(), rand);
			}

			// Generate a list of chest contents
			List<ItemStack> contents2 = generateChestContents(rand);
			Block chest2 = c.getBlock(x + 4, yFloor, z + 4);

			// Call the chest generation event
			DMGenerationChestEvent event2 = new DMGenerationChestEvent(chest2, rand, contents2, DMMazeStructureType.BLAZE_SPAWNER_ROOM);
			Bukkit.getServer().getPluginManager().callEvent(event2);
			
			// Spawn the chest unless the even is cancelled
			if(!event2.isCancelled()) {
				// Make sure the chest is still there, a developer could change the chest through the event!
				if(event2.getBlock().getType() != Material.CHEST)
					return;
				
				// Add the contents to the chest
				DMChestUtils.addItemsToChest(event2.getBlock(), event2.getContents(), !event2.getAddContentsInOrder(), rand);
			}
		}
	}
	
	public List<ItemStack> generateChestContents(Random random) {
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
			if(random.nextInt(100) < 30)
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
		for (int i = 0; i < itemCountInChest; i++)
		{
			newContents.add(items.get(random.nextInt(items.size())));
		}
	}
		else
		{
			String [] defaultChestItem = DMConfigHandler.defaultItem.split(" ");
			items.add(ItemUtils.parseItem(defaultChestItem));
		}
		return newContents;
	}
	
	/**
	 * Calculate the distance between two 2D points
	 * @param x1 X coord of first point
	 * @param y1 Y coord of first point
	 * @param x2 X coord of second point
	 * @param y2 Y coord of second point
	 * @return Distance between the two points
	 */
	public double distance(int x1, int y1, int x2, int y2) {
		double dx   = x1 - x2;         //horizontal difference 
		double dy   = y1 - y2;         //vertical difference 
		double dist = Math.sqrt( dx*dx + dy*dy ); //distance using Pythagoras theorem
		return dist;
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