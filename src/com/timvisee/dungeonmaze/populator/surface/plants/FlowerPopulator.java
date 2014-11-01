package com.timvisee.dungeonmaze.populator.surface.plants;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Material;

import com.timvisee.dungeonmaze.populator.surface.DMSurfaceBlockPopulator;
import com.timvisee.dungeonmaze.populator.surface.DMSurfaceBlockPopulatorArgs;

public class FlowerPopulator extends DMSurfaceBlockPopulator {
	public static final int CHANCE_OF_FLOWER = 15;
	public static final int ITERATIONS = 10;

	@Override
	public void populateSurface(DMSurfaceBlockPopulatorArgs args) {
		Chunk c = args.getSourceChunk();
		Random rand = args.getRandom();
		
		// Iterate
		for(int i = 0; i < ITERATIONS; i++) {
			// Apply chances
			if(rand.nextInt(100) < CHANCE_OF_FLOWER) {
				int xFlower = rand.nextInt(16);
				int zFlower = rand.nextInt(16);
				
				for(int j = 94; j > 79; j--)
				{
				if(c.getBlock(xFlower, j -1, zFlower).getType() == Material.GRASS) {
					int flowerY = j;
					
					// Spawn the flower
					if (rand.nextInt(2) == 0) {
						c.getBlock(xFlower, flowerY, zFlower).setType(Material.YELLOW_FLOWER);
					} else {
						c.getBlock(xFlower, flowerY, zFlower).setType(Material.RED_ROSE);
						c.getBlock(xFlower, flowerY, zFlower).setData(getRandomFlowerType(rand));
					}
				}
			}
		}
	  }
	}
	
	/**
	 * Get a random flower type
	 * @param rand Random instance
	 * @return Random flower type ID
	 */
	public byte getRandomFlowerType(Random rand) {
		return (byte) (rand.nextInt(9));
	}
}