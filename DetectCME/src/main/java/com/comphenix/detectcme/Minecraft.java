package com.comphenix.detectcme;

import java.lang.reflect.Field;

import org.bukkit.World;

import com.comphenix.detectcme.reflect.BukkitUnwrapper;
import com.comphenix.detectcme.reflect.FuzzyReflection;

public class Minecraft {
	private static Field entityTrackerField;
	
	public static Object getEntityTracker(World world) {
		Object worldServer = getWorldServer(world);

		// We have to rely on the class naming here.
		if (entityTrackerField == null)
			entityTrackerField = FuzzyReflection.fromObject(worldServer).getFieldByType(".*Tracker");
		
		// Get the tracker
		try {
			entityTrackerField.setAccessible(true);
			return entityTrackerField.get(worldServer);
		} catch (IllegalAccessException e) {
			throw new RuntimeException("Cannot access 'tracker' field due to security limitations.", e);
		}
	}
	
	public static Object getWorldServer(World world) {
		return BukkitUnwrapper.unwrapItem(world);
	}
}
