package com.timvisee.dungeonmaze.populator.maze.spawner;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.Block;

import com.timvisee.dungeonmaze.populator.maze.DMMazeRoomBlockPopulator;
import com.timvisee.dungeonmaze.populator.maze.DMMazeRoomBlockPopulatorArgs;

public class SilverfishBlockPopulator extends DMMazeRoomBlockPopulator {
	public static final int MIN_LAYER = 3;
	public static final int MAX_LAYER = 7;
	public static final int CHANCE = 75;
	public static final int ITERATIONS = 8;
	public static final double CHANCE_ADDITION_PER_LEVEL = -4.167; /* to 75 */

	@SuppressWarnings("deprecation")
	@Override
	public void populateRoom(DMMazeRoomBlockPopulatorArgs args) {
		Chunk c = args.getSourceChunk();
		Random rand = args.getRandom();
		int x = args.getChunkX();
		int y = args.getChunkY();
		int floorOffset = args.getFloorOffset();
		int z = args.getChunkZ();
		
		// Iterate
		for(int i = 0; i < ITERATIONS; i++) {
			if(rand.nextInt(100) < CHANCE + (CHANCE_ADDITION_PER_LEVEL * (y - 30) / 6)) {
				int blockX = x + rand.nextInt(8);
				int blockY = y + rand.nextInt(4 - floorOffset) + 1 + floorOffset;
				int blockZ = z + rand.nextInt(8);
				
				Block lanternBlock = c.getBlock(blockX, blockY, blockZ);
				if(lanternBlock.getType() == Material.STONE) {
					lanternBlock.setTypeIdAndData(Material.MONSTER_EGGS.getId(),(byte)0,false);
				} else if(lanternBlock.getType() == Material.COBBLESTONE) {
					lanternBlock.setTypeIdAndData(Material.MONSTER_EGGS.getId(),(byte) 1,false);
				} else if(lanternBlock.getType() == Material.MOSSY_COBBLESTONE) {
					lanternBlock.setTypeIdAndData(Material.MONSTER_EGGS.getId(),(byte) 1,false);
				} else if(lanternBlock.getType() == Material.SMOOTH_BRICK) {
					lanternBlock.setTypeIdAndData(Material.MONSTER_EGGS.getId(),(byte)2,false);
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
