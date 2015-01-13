package com.timvisee.dungeonmaze.listener;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Spider;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.timvisee.dungeonmaze.DungeonMaze;

public class DMWorldListener implements Listener {
	
	@EventHandler
	public void onWorldLoad(WorldLoadEvent e) {
		// Refresh the list with DM worlds
		DungeonMaze.instance.getWorldManager().refresh();
	}
	@EventHandler
	public void onChunkLoad(ChunkLoadEvent e)
	{
		
		
	}
	@EventHandler
	public void createJockeys(CreatureSpawnEvent e)
	{
		if(DungeonMaze.instance.getWorldManager().getDMWorlds().contains(e.getLocation().getWorld().getName()))
		{
			if(e.getSpawnReason() == SpawnReason.SPAWNER)
			{
				if(e.getEntityType() == EntityType.SPIDER || e.getEntityType() == EntityType.SKELETON)
				{
					Random r = new Random();
					Random z = new Random();
					z.setSeed(System.nanoTime());
					int zNext = 1 + z.nextInt(8);
					r.setSeed(System.nanoTime() + ((System.currentTimeMillis()/4) * zNext));
					int rNext = r.nextInt(100);
					if(rNext < 10)
					{
						Location loc = e.getEntity().getLocation();
						World world = loc.getWorld();
						if(e.getEntityType() == EntityType.SPIDER)
						{
							Skeleton skeleton = (Skeleton) world.spawnEntity(loc, EntityType.SKELETON);
							e.getEntity().setPassenger(skeleton);
						}
						if(e.getEntityType() == EntityType.SKELETON)
						{
							Spider spider = (Spider) world.spawnEntity(loc, EntityType.SPIDER);
							spider.setPassenger(e.getEntity());
						}
					}
				}
			}
		}
	}
	@EventHandler
	public void increaseGhastHealth(CreatureSpawnEvent e)
	{
		if(DungeonMaze.instance.getWorldManager().getDMWorlds().contains(e.getLocation().getWorld().getName()))
		{
			if(e.getEntityType() == EntityType.GHAST)
			{
				e.getEntity().setMaxHealth(100);
				e.getEntity().setHealth(100);
			}
		}
	}
	@EventHandler
	public void armTheZombies(CreatureSpawnEvent e)
	{
		if(DungeonMaze.instance.getWorldManager().getDMWorlds().contains(e.getLocation().getWorld().getName()))
		{
			if(e.getSpawnReason() == SpawnReason.SPAWNER)
			{
				if(e.getEntityType() == EntityType.ZOMBIE)
				{
					Random r = new Random();
					Random z = new Random();
					z.setSeed(System.nanoTime());
					int zNext = 1 + z.nextInt(8);
					r.setSeed(System.nanoTime() + ((System.currentTimeMillis()/4) * zNext));
					int rNext = r.nextInt(100);
					if(rNext < 20)
					{
						Zombie zombie = (Zombie)e.getEntity();
						rNext = r.nextInt(4);
						if(rNext == 0)
						{
							zombie.getEquipment().setHelmet(new ItemStack(Material.LEATHER_HELMET));
							zombie.getEquipment().setHelmetDropChance(0.5F);
							zombie.getEquipment().setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE));
							zombie.getEquipment().setChestplateDropChance(0.5F);
							zombie.getEquipment().setLeggings(new ItemStack(Material.LEATHER_LEGGINGS));
							zombie.getEquipment().setLeggingsDropChance(0.5F);
							zombie.getEquipment().setBoots(new ItemStack(Material.LEATHER_BOOTS));
							zombie.getEquipment().setBootsDropChance(0.5F);
							zombie.getEquipment().setItemInHand(new ItemStack(Material.WOOD_SWORD));
							zombie.getEquipment().setItemInHandDropChance(0.5F);
							zombie.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,10000, 1), true);
						}
						else if(rNext == 1)
						{
							zombie.getEquipment().setHelmet(new ItemStack(Material.LEATHER_HELMET));
							zombie.getEquipment().setHelmetDropChance(0.3F);
							zombie.getEquipment().setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE));
							zombie.getEquipment().setChestplateDropChance(0.3F);
							zombie.getEquipment().setLeggings(new ItemStack(Material.LEATHER_LEGGINGS));
							zombie.getEquipment().setLeggingsDropChance(0.3F);
							zombie.getEquipment().setBoots(new ItemStack(Material.LEATHER_BOOTS));
							zombie.getEquipment().setBootsDropChance(0.3F);
							zombie.getEquipment().setItemInHand(new ItemStack(Material.IRON_SWORD));
							zombie.getEquipment().setItemInHandDropChance(0.3F);
							zombie.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,10000, 1), true);
						}
						else if(rNext == 2)
						{
							zombie.getEquipment().setHelmet(new ItemStack(Material.IRON_HELMET));
							zombie.getEquipment().setHelmetDropChance(0.2F);
							zombie.getEquipment().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
							zombie.getEquipment().setChestplateDropChance(0.2F);
							zombie.getEquipment().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
							zombie.getEquipment().setLeggingsDropChance(0.2F);
							zombie.getEquipment().setBoots(new ItemStack(Material.IRON_BOOTS));
							zombie.getEquipment().setBootsDropChance(0.2F);
							zombie.getEquipment().setItemInHand(new ItemStack(Material.IRON_SWORD));
							zombie.getEquipment().setItemInHandDropChance(0.2F);
							zombie.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,10000, 1), true);
						}
						else if(rNext == 3)
						{
							zombie.getEquipment().setHelmet(new ItemStack(Material.DIAMOND_HELMET));
							zombie.getEquipment().setHelmetDropChance(0.1F);
							zombie.getEquipment().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
							zombie.getEquipment().setChestplateDropChance(0.1F);
							zombie.getEquipment().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
							zombie.getEquipment().setLeggingsDropChance(0.1F);
							zombie.getEquipment().setBoots(new ItemStack(Material.DIAMOND_BOOTS));
							zombie.getEquipment().setBootsDropChance(0.1F);
							zombie.getEquipment().setItemInHand(new ItemStack(Material.DIAMOND_SWORD));
							zombie.getEquipment().setItemInHandDropChance(0.1F);
							zombie.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,10000, 1), true);
						}
					}
				}
			}
		}
	}
	@EventHandler
	public void makeThemHostile(CreatureSpawnEvent e)
	{
		if(DungeonMaze.instance.getWorldManager().getDMWorlds().contains(e.getLocation().getWorld().getName()))
		{
			if(e.getEntityType() == EntityType.PIG_ZOMBIE)
			{
				PigZombie pigzombie = (PigZombie) e.getEntity();
				pigzombie.setAngry(true);
			}
		}
	}
}
