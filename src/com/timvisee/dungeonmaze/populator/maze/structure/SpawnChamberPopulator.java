package com.timvisee.dungeonmaze.populator.maze.structure;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;

import com.timvisee.dungeonmaze.DungeonMaze;
import com.timvisee.dungeonmaze.event.generation.DMGenerationChestEvent;
import com.timvisee.dungeonmaze.populator.maze.DMMazeRoomBlockPopulator;
import com.timvisee.dungeonmaze.populator.maze.DMMazeRoomBlockPopulatorArgs;
import com.timvisee.dungeonmaze.populator.maze.DMMazeStructureType;
import com.timvisee.dungeonmaze.util.DMChestUtils;

public class SpawnChamberPopulator extends DMMazeRoomBlockPopulator {
	public static final int MIN_LAYER = 7;
	public static final int MAX_LAYER = 7;

	@Override
	public void populateRoom(DMMazeRoomBlockPopulatorArgs args) {
		World w = args.getWorld();
		Chunk c = args.getSourceChunk();
		Random rand = args.getRandom();
		int x = args.getChunkX();
		int y = args.getChunkY();
		int z = args.getChunkZ();
		
		// Make sure this is the chunk at (0, 0)
		if(c.getX() != 0 || c.getZ() != 0 || x != 0 || z != 0)
			return;
							
		// Register the current room as constant room
		DungeonMaze.instance.registerConstantRoom(w.getName(), c.getX(), c.getZ(), x, y, z);
		
		// Break out the orriginal walls
		for (int xx = 0; xx < 8; xx++)
			for (int yy = y + 2; yy < 30+(7*6); yy++)
				for(int zz = 0; zz < 8; zz++)
					c.getBlock(x + xx, yy, z + zz).setType(Material.AIR);
		
		// Generate corners
		for (int yy = y + 2; yy < 30+(7*6); yy++) {
			c.getBlock(x + 0, yy, z + 0).setType(Material.SMOOTH_BRICK);
			c.getBlock(x + 7, yy, z + 0).setType(Material.SMOOTH_BRICK);
			c.getBlock(x + 0, yy, z + 7).setType(Material.SMOOTH_BRICK);
			c.getBlock(x + 7, yy, z + 7).setType(Material.SMOOTH_BRICK);
		}
		
		//floor
		for (int xx=x; xx <= x + 7; xx++)
		    for (int zz=z; zz <= z + 7; zz++)
		        c.getBlock(xx, y + 1, zz).setType(Material.SMOOTH_BRICK);
		        
		// Change the layer underneeth the stone floor to cobblestone
		for (int xx=x; xx <= x + 8; xx++)
		    for (int zz=z; zz <= z + 0; zz++)
		        c.getBlock(xx, y + 1, zz).setType(Material.COBBLESTONE);
		        
		//Ceiling
		for (int xx=x; xx <= x + 8; xx++)
		    for (int zz=z; zz <= z + 8; zz++)
		        c.getBlock(xx, y + 6, zz).setType(Material.SMOOTH_BRICK);
		
		// Generate 4 circulair blocks in the middle of the floor
		for (int xx=x + 3; xx <= x + 4; xx++) {
		    for (int zz=z + 3; zz <= z + 4; zz++) {
		        c.getBlock(xx, y + 1, zz).setType(Material.SMOOTH_BRICK);
		        c.getBlock(xx, y + 1, zz).setData((byte) 3);
		    }
		}
		
		// Create walls
		for (int xx=x + 1; xx <= x + 6; xx++) {
            for (int yy=y + 2; yy <= y + 5; yy++) {
                c.getBlock(xx, yy, z).setType(Material.IRON_FENCE);
                c.getBlock(xx, yy, z + 7).setType(Material.IRON_FENCE);
            }
        }
		for (int zz=z + 1; zz <= z + 6; zz++) {
            for (int yy=y + 2; yy <= y + 5; yy++) {
                c.getBlock(x, yy, zz).setType(Material.IRON_FENCE);
                c.getBlock(x + 7, yy, zz).setType(Material.IRON_FENCE);
            }
        }
		
		// Create gates
		for (int xx=x + 2; xx <= x + 5; xx++) {
            for (int yy=y + 2; yy <= y + 4; yy++) {
                c.getBlock(xx, yy, z).setType(Material.SMOOTH_BRICK);
                c.getBlock(xx, yy, z + 7).setType(Material.SMOOTH_BRICK);
            }
        }
		for (int zz=z + 2; zz <= z + 5; zz++) {
            for (int yy=y + 2; yy <= y + 4; yy++) {
                c.getBlock(x, yy, zz).setType(Material.SMOOTH_BRICK);
                c.getBlock(x + 7, yy, zz).setType(Material.SMOOTH_BRICK);
            }
        }
		for (int xx=x + 3; xx <= x + 4; xx++) {
            for (int yy=y + 2; yy <= y + 3; yy++) {
                c.getBlock(xx, yy, z).setType(Material.AIR);
                c.getBlock(xx, yy, z + 7).setType(Material.AIR);
            }
        }
		for (int zz=z + 3; zz <= z + 4; zz++) {
            for (int yy=y + 2; yy <= y + 3; yy++) {
                c.getBlock(x, yy, zz).setType(Material.AIR);
                c.getBlock(x + 7, yy, zz).setType(Material.AIR);
            }
        }

		// Empty ItemStack list for events
		List<ItemStack> emptyList = new ArrayList<ItemStack>();

		// Create chests
		c.getBlock(x + 1, y + 2, z + 1).setType(Material.CHEST);
		c.getBlock(x + 1, y + 2, z + 1).setData((byte) 3);
		
		// Call the Chest generation event
		DMGenerationChestEvent event = new DMGenerationChestEvent(c.getBlock(x + 1, y + 2, z + 1), rand, emptyList, DMMazeStructureType.SPAWN_ROOM);
		Bukkit.getServer().getPluginManager().callEvent(event);

		// Do the event
		if(!event.isCancelled()) {
			// Make sure the chest is still there, a developer could change the chest through the event!
			if(event.getBlock().getType() == Material.CHEST)
			// Add the contents to the chest
			DMChestUtils.addItemsToChest(event.getBlock(), event.getContents(), !event.getAddContentsInOrder(), rand);
		}

		c.getBlock(x + 1, y + 2, z + 6).setType(Material.CHEST);
		c.getBlock(x + 1, y + 2, z + 6).setData((byte) 2);

		// Call the Chest generation event
		DMGenerationChestEvent event2 = new DMGenerationChestEvent(c.getBlock(x + 1, y + 2, z + 6), rand, emptyList, DMMazeStructureType.SPAWN_ROOM);
		Bukkit.getServer().getPluginManager().callEvent(event2);

		// Do the event
		if(!event2.isCancelled()) {
			// Make sure the chest is still there, a developer could change the chest through the event!
			if(event2.getBlock().getType() == Material.CHEST)
			// Add the contents to the chest
			DMChestUtils.addItemsToChest(event.getBlock(), event.getContents(), !event.getAddContentsInOrder(), rand);
		}

		c.getBlock(x + 6, y + 2, z + 1).setType(Material.CHEST);
		c.getBlock(x + 6, y + 2, z + 1).setData((byte) 3);

		// Call the Chest generation event
		DMGenerationChestEvent event3 = new DMGenerationChestEvent(c.getBlock(x + 6, y + 2, z + 1), rand, emptyList, DMMazeStructureType.SPAWN_ROOM);
		Bukkit.getServer().getPluginManager().callEvent(event3);

		// Do the event
		if(!event3.isCancelled()) {
			// Make sure the chest is still there, a developer could change the chest through the event!
			if(event3.getBlock().getType() == Material.CHEST)
			// Add the contents to the chest
			DMChestUtils.addItemsToChest(event.getBlock(), event.getContents(), !event.getAddContentsInOrder(), rand);
		}

		c.getBlock(x + 6, y + 2, z + 6).setType(Material.CHEST);
		c.getBlock(x + 6, y + 2, z + 6).setData((byte) 2);

		// Call the Chest generation event
		DMGenerationChestEvent event4 = new DMGenerationChestEvent(c.getBlock(x + 6, y + 2, z + 6), rand, emptyList, DMMazeStructureType.SPAWN_ROOM);
		Bukkit.getServer().getPluginManager().callEvent(event4);

		// Do the event
		if(!event4.isCancelled()) {
			// Make sure the chest is still there, a developer could change the chest through the event!
			if(event4.getBlock().getType() == Material.CHEST)
			// Add the contents to the chest
			DMChestUtils.addItemsToChest(event.getBlock(), event.getContents(), !event.getAddContentsInOrder(), rand);
		}

		// Create torches
		c.getBlock(x + 1, y + 3, z + 2).setType(Material.TORCH);
		c.getBlock(x + 1, y + 3, z + 5).setType(Material.TORCH);
		c.getBlock(x + 6, y + 3, z + 2).setType(Material.TORCH);
		c.getBlock(x + 6, y + 3, z + 5).setType(Material.TORCH);
		c.getBlock(x + 2, y + 3, z + 1).setType(Material.TORCH);
		c.getBlock(x + 2, y + 3, z + 6).setType(Material.TORCH);
		c.getBlock(x + 5, y + 3, z + 1).setType(Material.TORCH);
		c.getBlock(x + 5, y + 3, z + 6).setType(Material.TORCH);
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