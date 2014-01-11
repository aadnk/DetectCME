package com.comphenix.detectcme;

/*
 *  DetectCME - Bukkit plugin that can detect the origin of ConcurrentModificationExceptions.
 *  Copyright (C) 2012 Kristian S. Stangeland
 *
 *  This program is free software; you can redistribute it and/or modify it under the terms of the 
 *  GNU General Public License as published by the Free Software Foundation; either version 2 of 
 *  the License, or (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; 
 *  without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
 *  See the GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License along with this program; 
 *  if not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 
 *  02111-1307 USA
 */

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.event.world.WorldUnloadEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.comphenix.detectcme.injector.AbstractInjector;
import com.comphenix.detectcme.injector.ServerInjector;
import com.comphenix.detectcme.injector.WorldInjector;

public class DetectCME extends JavaPlugin implements Listener {
	private Map<String, WorldInjector> injected = new HashMap<String, WorldInjector>();
	private ServerInjector serverInjector = new ServerInjector();
	
	private static final int TICKS_PER_SECOND = 20;
	
	@Override
	public void onEnable() { 
		PluginManager manager = getServer().getPluginManager();
		manager.registerEvents(this, this);
				
		getLogger().warning(ChatColor.GOLD + "DetectCME is a debug tool. Use with caution.");
		
		// Inject into all loaded worlds
		for (World world : getServer().getWorlds()) {
			inject(world);
		}
		
		// Inject into CraftScoreboardManager
		serverInjector.inject(getServer(), Thread.currentThread());
		
		// For testing purposes
		getServer().getScheduler().runTaskLaterAsynchronously(this, new Runnable() {
			@Override
			public void run() {
				getServer().getScoreboardManager().getNewScoreboard();
			}
		}, 5 * 20);
	}
	
	// Used to verify that the protector works
	public void testAccessRule(int delaySeconds) {
		// Test stuff
		getServer().getScheduler().runTaskLaterAsynchronously(this, new Runnable() {
			@SuppressWarnings("unchecked")
			@Override
			public void run() {
				for (WorldInjector injector : injected.values()) {
					// Try to get the iterator - this should FAIL
					for (Object obj : injector.getInjectedObjects()) {
						((Iterable<Object>) obj).iterator();
					}
				}
			}
		}, delaySeconds * TICKS_PER_SECOND);
	}
	
	@Override
	public void onDisable() {
		// Uninject everything
		for (AbstractInjector injector : injected.values()) {
			injector.uninject();
		}
		
		injected.clear();
		serverInjector.uninject();
	}
	
	private void uninject(World world) {
		// Uninject and remove
		if (injected.containsKey(world.getName())) {
			injected.remove(world.getName()).uninject();
	
			getLogger().log(Level.INFO, "Uninjected thread monitor from world "  + world.getName());
		}
	}
	
	private void inject(World world) {
		WorldInjector injector = new WorldInjector();
		
		uninject(world);
		injector.inject(world, Thread.currentThread());
		injected.put(world.getName(), injector);
		
		// Inform the admininstrator
		getLogger().log(Level.WARNING, "Injected thread monitor into world " + world.getName());
	}
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onWorldLoaded(WorldLoadEvent event) {
		inject(event.getWorld());
	}
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onWorldUnloaded(WorldUnloadEvent event) {
		uninject(event.getWorld());
	}
}
