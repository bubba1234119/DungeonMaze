package com.timvisee.dungeonmaze.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.plugin.Plugin;

import com.timvisee.dungeonmaze.DungeonMaze;

public class DMPluginListener implements Listener {
	

	
	@EventHandler
	public void onPluginDisable(PluginDisableEvent e) {
		Plugin p = e.getPlugin();
		
		// Make sure the plugin instance isn't null
		if(p == null)
			return;
		
		// Make sure it's not Dungeon Maze itself
		if(p.equals(DungeonMaze.instance))
			return;
		
		// Check if this plugin is hooked in to Dungeon Maze
		if(DungeonMaze.instance.getApiController().isHooked(p)) {
			// Unhook the plugin from Dungeon Maze and unregister it's API sessions
			DungeonMaze.instance.getApiController().unhookPlugin(p);
		}
	}
}
