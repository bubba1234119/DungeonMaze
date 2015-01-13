package com.timvisee.dungeonmaze.config;

import java.io.File;
import java.util.List;

import org.bukkit.Material;

import com.timvisee.dungeonmaze.DungeonMaze;

public class DMConfigHandler {
	
	// Configuration cache
	//public FileConfiguration config;
	private static File configFile = new File(DungeonMaze.instance.getDataFolder().getPath(),"config.yml");
	public static boolean unloadWorldsOnPluginDisable;
	public static boolean allowSurface;
	public static List<Object> blockWhiteList;
	public static boolean usePermissions;
	public static boolean useBypassPermissions;
	public static List<String> mobs;
	public static List<String> itemsCommon;
	public static List<String> itemsUncommon;
	public static List<String> itemsRare;
	public static List<String> itemsEpic;
	public static String defaultItem;
	@SuppressWarnings("unchecked")
	public static void load() {
		// Get the config instance
		//config = new DMConfig();
		
		if(!configFile.exists())
		DungeonMaze.instance.saveDefaultConfig();
		else
		DungeonMaze.instance.reloadConfig();
		
		// Load (and cache) the properties
		DMConfigHandler.unloadWorldsOnPluginDisable = DungeonMaze.instance.getConfig().getBoolean("unloadWorldsOnPluginDisable", true);
		DMConfigHandler.usePermissions = DungeonMaze.instance.getConfig().getBoolean("usePermissions", true);
		DMConfigHandler.useBypassPermissions = DungeonMaze.instance.getConfig().getBoolean("useBypassPermissions", true);
		DMConfigHandler.blockWhiteList = (List<Object>) DungeonMaze.instance.getConfig().getList("blockWhiteList");
		DMConfigHandler.mobs = DungeonMaze.instance.getConfig().getStringList("mobs");
		DMConfigHandler.itemsCommon = DungeonMaze.instance.getConfig().getStringList("itemsCommon");
		DMConfigHandler.itemsUncommon = DungeonMaze.instance.getConfig().getStringList("itemsUncommon");
		DMConfigHandler.itemsRare = DungeonMaze.instance.getConfig().getStringList("itemsRare");
		DMConfigHandler.itemsEpic = DungeonMaze.instance.getConfig().getStringList("itemsEpic");
		DMConfigHandler.defaultItem = DungeonMaze.instance.getConfig().getString("defaultItem");
		
	}
	
	/**
	 * Check whether a block is in the block whitelist or not
	 * @param int Block type ID
	 * @return true if the object is in the list
	 */
	@Deprecated // Deprecate this for use Material enum
	public boolean isInWhiteList(int target) {
		List<Object> list = DMConfigHandler.blockWhiteList;
		
		if(list == null)
			return(false);
		
		for(Object entry : list)
			if(entry instanceof Integer)
				return (((Integer) entry).intValue() == target);
		return false;
	}
	
	/**
	 * Check whether a block is in the block whitelist or not
	 * @param Material Block type
	 * @return true if the object is in the list
	 */
	/*
	 *  TODO: Actually use the getId() magic value,
	 *  need to update when Minecraft/Bukkit will remove them
	 */
	public boolean isInWhiteList(Material material) {
		int target = material.getId();
		List<Object> list = DMConfigHandler.blockWhiteList;
		
		if(list == null)
			return(false);
		
		for(Object entry : list)
			if(entry instanceof Integer)
				return (((Integer) entry).intValue() == target);
		return false;
	}
	
	/**
	 * Check whether a mob spawner is allowed or not
	 * @param String mobName The name of the mob
	 * @return True if the mob spawner is allowed for this mob
	 */
	public boolean isMobSpawnerAllowed(String mob) {
		return mobs.contains(mob);
	}
}
