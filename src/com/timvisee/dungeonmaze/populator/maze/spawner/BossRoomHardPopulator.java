package com.timvisee.dungeonmaze.populator.maze.spawner;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import com.timvisee.dungeonmaze.DungeonMaze;
import com.timvisee.dungeonmaze.config.DMConfigHandler;
import com.timvisee.dungeonmaze.event.generation.DMGenerationSpawnerCause;
import com.timvisee.dungeonmaze.event.generation.DMGenerationSpawnerEvent;
import com.timvisee.dungeonmaze.populator.maze.DMMazeLayerBlockPopulator;
import com.timvisee.dungeonmaze.populator.maze.DMMazeLayerBlockPopulatorArgs;
import com.timvisee.dungeonmaze.util.ItemUtils;

public class BossRoomHardPopulator extends DMMazeLayerBlockPopulator {
	public static final int MIN_LAYER = 1;
	public static final int MAX_LAYER = 4;
	public static final int CHANCE_OF_BOSSROOM = 1; //Promile
	public static final double MIN_SPAWN_DISTANCE = 10; // Chunks

	@Override
	public void populateLayer(DMMazeLayerBlockPopulatorArgs args) {
		World w = args.getWorld();
		Random rand = args.getRandom();
		Chunk c = args.getSourceChunk();
		int x = 0;
		int y = args.getY();
		int z = 0;
		
		// Make sure the distance between the spawn chunk and the current chunk is allowed
		if(distance(0, 0, c.getX(), c.getZ()) < MIN_SPAWN_DISTANCE)
			return;
		
		// Apply chances
		if(rand.nextInt(1000) < CHANCE_OF_BOSSROOM) {
			DungeonMaze.instance.registerConstantChunk(w.getName(), c.getX(), c.getZ());					
			
			// Clear the room!
			for(int x2=x; x2 < x + 15; x2+=1)
				for(int y2=y + 1; y2 <= y + (6*3) - 1; y2+=1)
					for(int z2=z; z2 <z + 15; z2+=1)
						c.getBlock(x2, y2, z2).setType(Material.AIR);
			
			// Floor
			for(int x2=x; x2 < x + 15; x2+=1)
			    for(int z2=z; z2 < z + 15; z2+=1)
			        c.getBlock(x2, y, z2).setType(Material.OBSIDIAN);
			
			// Glass shields
			c.getBlock(x + 2, y + 1, z + 3).setType(Material.GLASS);
			c.getBlock(x + 2, y + 1, z + 12).setType(Material.GLASS);
			c.getBlock(x + 3, y + 1, z + 2).setType(Material.GLASS);
			c.getBlock(x + 3, y + 1, z + 4).setType(Material.GLASS);
			c.getBlock(x + 3, y + 1, z + 11).setType(Material.GLASS);
			c.getBlock(x + 3, y + 1, z + 13).setType(Material.GLASS);
			c.getBlock(x + 4, y + 1, z + 3).setType(Material.GLASS);
			c.getBlock(x + 4, y + 1, z + 12).setType(Material.GLASS);
			c.getBlock(x + 5, y + 1, z + 7).setType(Material.GLASS);
			c.getBlock(x + 5, y + 1, z + 8).setType(Material.GLASS);
			c.getBlock(x + 6, y + 1, z + 6).setType(Material.GLASS);
			c.getBlock(x + 6, y + 1, z + 9).setType(Material.GLASS);
			c.getBlock(x + 7, y + 1, z + 5).setType(Material.GLASS);
			c.getBlock(x + 7, y + 1, z + 10).setType(Material.GLASS);
			c.getBlock(x + 8, y + 1, z + 5).setType(Material.GLASS);
			c.getBlock(x + 8, y + 1, z + 10).setType(Material.GLASS);
			c.getBlock(x + 9, y + 1, z + 6).setType(Material.GLASS);
			c.getBlock(x + 9, y + 1, z + 9).setType(Material.GLASS);
			c.getBlock(x + 10, y + 1, z + 7).setType(Material.GLASS);
			c.getBlock(x + 10, y + 1, z + 8).setType(Material.GLASS);
			c.getBlock(x + 11, y + 1, z + 3).setType(Material.GLASS);
			c.getBlock(x + 11, y + 1, z + 12).setType(Material.GLASS);
			c.getBlock(x + 12, y + 1, z + 2).setType(Material.GLASS);
			c.getBlock(x + 12, y + 1, z + 4).setType(Material.GLASS);
			c.getBlock(x + 12, y + 1, z + 11).setType(Material.GLASS);
			c.getBlock(x + 12, y + 1, z + 13).setType(Material.GLASS);
			c.getBlock(x + 13, y + 1, z + 3).setType(Material.GLASS);
			c.getBlock(x + 13, y + 1, z + 12).setType(Material.GLASS);
			c.getBlock(x + 3, y + 2, z + 3).setType(Material.GLASS);
			c.getBlock(x + 3, y + 2, z + 12).setType(Material.GLASS);
			c.getBlock(x + 5, y + 2, z + 7).setType(Material.GLASS);
			c.getBlock(x + 5, y + 2, z + 8).setType(Material.GLASS);
			c.getBlock(x + 6, y + 2, z + 6).setType(Material.GLASS);
			c.getBlock(x + 6, y + 2, z + 9).setType(Material.GLASS);
			c.getBlock(x + 7, y + 2, z + 5).setType(Material.GLASS);
			c.getBlock(x + 7, y + 2, z + 10).setType(Material.GLASS);
			c.getBlock(x + 8, y + 2, z + 5).setType(Material.GLASS);
			c.getBlock(x + 8, y + 2, z + 10).setType(Material.GLASS);
			c.getBlock(x + 9, y + 2, z + 6).setType(Material.GLASS);
			c.getBlock(x + 9, y + 2, z + 9).setType(Material.GLASS);
			c.getBlock(x + 10, y + 2, z + 7).setType(Material.GLASS);
			c.getBlock(x + 10, y + 2, z + 8).setType(Material.GLASS);
			c.getBlock(x + 12, y + 2, z + 3).setType(Material.GLASS);
			c.getBlock(x + 12, y + 2, z + 12).setType(Material.GLASS);
			c.getBlock(x + 6, y + 3, z + 7).setType(Material.GLASS);
			c.getBlock(x + 6, y + 3, z + 8).setType(Material.GLASS);
			c.getBlock(x + 7, y + 3, z + 6).setType(Material.GLASS);
			c.getBlock(x + 7, y + 3, z + 9).setType(Material.GLASS);
			c.getBlock(x + 8, y + 3, z + 6).setType(Material.GLASS);
			c.getBlock(x + 8, y + 3, z + 9).setType(Material.GLASS);
			c.getBlock(x + 9, y + 3, z + 7).setType(Material.GLASS);
			c.getBlock(x + 9, y + 3, z + 8).setType(Material.GLASS);
			c.getBlock(x + 7, y + 4, z + 7).setType(Material.GLASS);
			c.getBlock(x + 7, y + 4, z + 8).setType(Material.GLASS);
			c.getBlock(x + 8, y + 4, z + 7).setType(Material.GLASS);
			c.getBlock(x + 8, y + 4, z + 8).setType(Material.GLASS);
			
			// Netherbrick hull
			c.getBlock(x + 6, y + 1, z + 7).setType(Material.NETHER_BRICK);
			c.getBlock(x + 6, y + 1, z + 8).setType(Material.NETHER_BRICK);
			c.getBlock(x + 7, y + 1, z + 6).setType(Material.NETHER_BRICK);
			c.getBlock(x + 7, y + 1, z + 9).setType(Material.NETHER_BRICK);
			c.getBlock(x + 8, y + 1, z + 6).setType(Material.NETHER_BRICK);
			c.getBlock(x + 8, y + 1, z + 9).setType(Material.NETHER_BRICK);
			c.getBlock(x + 9, y + 1, z + 7).setType(Material.NETHER_BRICK);
			c.getBlock(x + 9, y + 1, z + 8).setType(Material.NETHER_BRICK);
			c.getBlock(x + 6, y + 2, z + 7).setType(Material.NETHER_BRICK);
			c.getBlock(x + 6, y + 2, z + 8).setType(Material.NETHER_BRICK);
			c.getBlock(x + 7, y + 2, z + 6).setType(Material.NETHER_BRICK);
			c.getBlock(x + 7, y + 2, z + 9).setType(Material.NETHER_BRICK);
			c.getBlock(x + 8, y + 2, z + 6).setType(Material.NETHER_BRICK);
			c.getBlock(x + 8, y + 2, z + 9).setType(Material.NETHER_BRICK);
			c.getBlock(x + 9, y + 2, z + 7).setType(Material.NETHER_BRICK);
			c.getBlock(x + 9, y + 2, z + 8).setType(Material.NETHER_BRICK);
			c.getBlock(x + 7, y + 3, z + 7).setType(Material.NETHER_BRICK);
			c.getBlock(x + 7, y + 3, z + 8).setType(Material.NETHER_BRICK);
			c.getBlock(x + 8, y + 3, z + 7).setType(Material.NETHER_BRICK);
			c.getBlock(x + 8, y + 3, z + 8).setType(Material.NETHER_BRICK);

			// Ores
			c.getBlock(x + 7, y + 1, z + 7).setType(Material.IRON_ORE);
			c.getBlock(x + 7, y + 1, z + 8).setType(Material.REDSTONE_ORE);
			c.getBlock(x + 8, y + 1, z + 7).setType(Material.IRON_ORE);

			// Chest
			c.getBlock(x + 8, y + 1, z + 8).setType(Material.CHEST);
			addItemsToChest(rand, (Chest) c.getBlock(x + 8, y + 1, z + 8).getState());

			// Core spawners
			if (DMConfigHandler.mobs.contains("Ghast")) {
				Block spawnerBlock = c.getBlock(x + 7, y + 2, z + 7);

				// Call the spawner generation event
				DMGenerationSpawnerEvent event = new DMGenerationSpawnerEvent(spawnerBlock, EntityType.GHAST, DMGenerationSpawnerCause.BOSSROOM_HARD, rand);
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

			if (DMConfigHandler.mobs.contains("Zombie")) {
				Block spawnerBlock = c.getBlock(x + 7, y + 2, z + 8);

				// Call the spawner generation event
				DMGenerationSpawnerEvent event = new DMGenerationSpawnerEvent(spawnerBlock, EntityType.ZOMBIE, DMGenerationSpawnerCause.BOSSROOM_HARD, rand);
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

			if (DMConfigHandler.mobs.contains("PigZombie")) {
				Block spawnerBlock = c.getBlock(x + 8, y + 2, z + 7);

				// Call the spawner generation event
				DMGenerationSpawnerEvent event = new DMGenerationSpawnerEvent(spawnerBlock, EntityType.PIG_ZOMBIE, DMGenerationSpawnerCause.BOSSROOM_HARD, rand);
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

			if (DMConfigHandler.mobs.contains("Zombie")) {
				Block spawnerBlock = c.getBlock(x + 8, y + 2, z + 8);

				// Call the spawner generation event
				DMGenerationSpawnerEvent event = new DMGenerationSpawnerEvent(spawnerBlock, EntityType.ZOMBIE, DMGenerationSpawnerCause.BOSSROOM_HARD, rand);
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

			// Loose spawners
			if (DMConfigHandler.mobs.contains("Zombie")) {
				Block spawnerBlock = c.getBlock(x + 3, y + 1, z + 3);

				// Call the spawner generation event
				DMGenerationSpawnerEvent event = new DMGenerationSpawnerEvent(spawnerBlock, EntityType.ZOMBIE, DMGenerationSpawnerCause.BOSSROOM_HARD, rand);
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

			if (DMConfigHandler.mobs.contains("Skeleton")) {
				Block spawnerBlock = c.getBlock(x + 3, y + 1, z + 12);

				// Call the spawner generation event
				DMGenerationSpawnerEvent event = new DMGenerationSpawnerEvent(spawnerBlock, EntityType.SKELETON, DMGenerationSpawnerCause.BOSSROOM_HARD, rand);
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

			if (DMConfigHandler.mobs.contains("Zombie")) {
				Block spawnerBlock = c.getBlock(x + 12, y + 1, z + 3);

				// Call the spawner generation event
				DMGenerationSpawnerEvent event = new DMGenerationSpawnerEvent(spawnerBlock, EntityType.ZOMBIE, DMGenerationSpawnerCause.BOSSROOM_HARD, rand);
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

			if (DMConfigHandler.mobs.contains("Spider")) { 
				Block spawnerBlock = c.getBlock(x + 12, y + 1, z + 12);

				// Call the spawner generation event
				DMGenerationSpawnerEvent event = new DMGenerationSpawnerEvent(spawnerBlock, EntityType.SPIDER, DMGenerationSpawnerCause.BOSSROOM_HARD, rand);
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
		}
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

	public double distance(int x1, int y1, int x2, int y2) {
		double dx   = x1 - x2;         //horizontal difference 
		double dy   = y1 - y2;         //vertical difference 
		double dist = Math.sqrt( dx*dx + dy*dy ); //distance using Pythagoras theorem
		return dist;
	}

	public void addItemsToChest(Random random, Chest chest) {
		List<ItemStack> items = new ArrayList<ItemStack>();

		for(int j = 0; j < DMConfigHandler.itemsUncommon.size(); j++)
		{
			int next = random.nextInt(1000);
			random.setSeed(System.nanoTime() + System.currentTimeMillis() + next);
			if(random.nextInt(100) < 70)
			{
				String [] uncommon = DMConfigHandler.itemsUncommon.get(j).split(" ");
				items.add(ItemUtils.parseItem(uncommon));
			}
		}

		for(int h = 0; h < DMConfigHandler.itemsRare.size(); h++)
		{
			int next = random.nextInt(100);
			random.setSeed(System.currentTimeMillis() + System.currentTimeMillis() + next + 100);
			if(random.nextInt(100) < 40)
			{
				String [] rare = DMConfigHandler.itemsRare.get(h).split(" ");
				items.add(ItemUtils.parseItem(rare));
			}
		}

		for(int a = 0; a < DMConfigHandler.itemsEpic.size(); a++)
		{
			int next = random.nextInt(30);
			random.setSeed(System.currentTimeMillis() + System.nanoTime() + System.currentTimeMillis() + next);
			if(random.nextInt(100) < 20)
			{
				String [] epic = DMConfigHandler.itemsEpic.get(a).split(" ");
				items.add(ItemUtils.parseItem(epic));
			}
		}


		int itemCountInChest = 4;
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
			itemCountInChest = 4;
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
			itemCountInChest = 4;
			break;
		}

		// Add the selected items to a random place inside the chest
		if(items.size() > 0)
		{
			for (int i = 0; i < itemCountInChest; i++)
			{
				chest.getInventory().setItem(random.nextInt(chest.getInventory().getSize()), items.get(random.nextInt(items.size())));
			}
		}
		else
		{
			String [] defaultChestItem = DMConfigHandler.defaultItem.split(" ");
			chest.getInventory().addItem(ItemUtils.parseItem(defaultChestItem));
		}
		chest.update();
	}
}