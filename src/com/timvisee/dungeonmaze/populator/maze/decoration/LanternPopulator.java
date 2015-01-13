package com.timvisee.dungeonmaze.populator.maze.decoration;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.Block;

import com.timvisee.dungeonmaze.populator.maze.DMMazeRoomBlockPopulator;
import com.timvisee.dungeonmaze.populator.maze.DMMazeRoomBlockPopulatorArgs;

public class LanternPopulator extends DMMazeRoomBlockPopulator {
	public static final int MIN_LAYER = 3;
	public static final int MAX_LAYER = 7;
	public static final int CHANCE_1 = 30;
	public static final double CHANCE_1_ADDITION_PER_LEVEL = 7.5; /* to 75 */
	public static final int ITERATIONS_1 = 2;
	public static final int CHANCE_2 = 10;
	public static final double CHANCE_2_ADDITION_PER_LEVEL = 4.167; /* to 35 */
	public static final int ITERATIONS_2 = 2;

	@Override
	public void populateRoom(DMMazeRoomBlockPopulatorArgs args) {
		Chunk c = args.getSourceChunk();
		Random rand = args.getRandom();
		int x = args.getChunkX();
		int y = args.getChunkY();
		int z = args.getChunkZ();
		int floorOffset = args.getFloorOffset();
		
		// Apply chances
		if(rand.nextInt(100) < CHANCE_1 + (CHANCE_1_ADDITION_PER_LEVEL * (y - 30) / 6)) {
			for(int i = 0; i < ITERATIONS_1; i++) {
				int lanternX = x + rand.nextInt(8);
				int lanternY = y + rand.nextInt(4 - floorOffset) + 2 + floorOffset;
				int lanternZ = z + rand.nextInt(8);
				
				Block b = c.getBlock(lanternX, lanternY, lanternZ);
				if(b.getType() == Material.COBBLESTONE || b.getType() == Material.MOSSY_COBBLESTONE || b.getType() == Material.SMOOTH_BRICK)
				{
					b.setType(Material.GLOWSTONE);
					if(c.getBlock(lanternX - 1, lanternY, lanternZ).getType() == Material.AIR)
					{
						c.getBlock(lanternX - 1, lanternY, lanternZ).setType(Material.COBBLESTONE);
						c.getBlock(lanternX - 1, lanternY, lanternZ).setType(Material.AIR);
					}
					if(c.getBlock(lanternX + 1, lanternY, lanternZ).getType() == Material.AIR)
					{
						c.getBlock(lanternX + 1, lanternY, lanternZ).setType(Material.COBBLESTONE);
						c.getBlock(lanternX + 1, lanternY, lanternZ).setType(Material.AIR);
					}
					if(c.getBlock(lanternX, lanternY, lanternZ + 1).getType() == Material.AIR)
					{
						c.getBlock(lanternX, lanternY, lanternZ + 1).setType(Material.COBBLESTONE);
						c.getBlock(lanternX, lanternY, lanternZ +1).setType(Material.AIR);
					}
					if(c.getBlock(lanternX, lanternY, lanternZ - 1).getType() == Material.AIR)
					{
						c.getBlock(lanternX-1, lanternY, lanternZ - 1).setType(Material.COBBLESTONE);
						c.getBlock(lanternX-1, lanternY, lanternZ - 1).setType(Material.AIR);
					}
				}
			}
		}
		
		if(rand.nextInt(100) < CHANCE_2 + (CHANCE_2_ADDITION_PER_LEVEL * (y - 30) / 6)) {
			for(int i = 0; i < ITERATIONS_2; i++) {
				int lanternX = x + rand.nextInt(8);
				int lanternY = rand.nextInt(4 - floorOffset) + 2 + floorOffset;
				int lanternZ = z + rand.nextInt(8);

				Block b = c.getBlock(lanternX, lanternY, lanternZ);
				if(b.getType() == Material.COBBLESTONE || b.getType() == Material.MOSSY_COBBLESTONE || b.getType() == Material.SMOOTH_BRICK)
				{
					b.setType(Material.GLOWSTONE);

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
}
