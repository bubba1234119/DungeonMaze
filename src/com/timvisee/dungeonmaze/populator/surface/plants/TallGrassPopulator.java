package com.timvisee.dungeonmaze.populator.surface.plants;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Material;

import com.timvisee.dungeonmaze.populator.surface.DMSurfaceBlockPopulator;
import com.timvisee.dungeonmaze.populator.surface.DMSurfaceBlockPopulatorArgs;

public class TallGrassPopulator extends DMSurfaceBlockPopulator {
	public static final int CHANCE_OF_GRASS = 35;
	public static final int ITERATIONS = 100;

	@Override
	public void populateSurface(DMSurfaceBlockPopulatorArgs args) {
		Chunk c = args.getSourceChunk();
		Random rand = args.getRandom();
		
		// Iterate
		for(int i = 0; i < ITERATIONS; i++) {
			// Apply chances
			if(rand.nextInt(100) < CHANCE_OF_GRASS) {
				int xGrass = rand.nextInt(16);
				int zGrass = rand.nextInt(16);
				
				for(int j = 94; j > 79; j--)
				{
				
				if(c.getBlock(xGrass, j-1, zGrass).getType() == Material.GRASS) {
					int yGrass = j;
					c.getBlock(xGrass, yGrass, zGrass).setType(Material.LONG_GRASS);
					c.getBlock(xGrass, yGrass, zGrass).setData((byte) 1);
				}
			}
		}
	}
  }
}