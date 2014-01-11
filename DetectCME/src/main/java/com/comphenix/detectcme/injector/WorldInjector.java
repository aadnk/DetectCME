package com.comphenix.detectcme.injector;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Set;

import org.bukkit.World;

import com.comphenix.detectcme.Minecraft;
import com.comphenix.detectcme.reflect.FuzzyReflection;
import com.comphenix.detectcme.reflect.VolatileField;
import com.google.common.base.Function;
import com.google.common.collect.Iterables;

public class WorldInjector extends AbstractInjector {
	public Iterable<Object> getInjectedObjects() {
		return Iterables.transform(injectedFields, new Function<VolatileField, Object>() {
			public Object apply(VolatileField param) {
				return param.getValue();
			};
		});
	}
	
	/**
	 * Inject our modified set into Minecraft.
	 * @param world - the world we need to modify.
	 * @param expectedThread - the thread that will be allowed to modify the set.
	 */
	public void inject(World world, Thread expectedThread) {
		injectEntityTracker(world, expectedThread);
		injectWorldServer(world, expectedThread);
	}
	
	/**
	 * Inject all lists in the world server object.
	 * @param world - the world.
	 * @param expectedThread - the expected thread.
	 */
	private void injectWorldServer(World world, Thread expectedThread) {
		Object worldServer = Minecraft.getWorldServer(world);
		Class<?> clazz = worldServer.getClass();
		
		// Inject absolutely every list and set field
		for (; clazz != null; clazz = clazz.getSuperclass()) {
			for (Field field : clazz.getDeclaredFields()) {
				if (field.getType().equals(List.class) || field.getType().equals(Set.class)) {
					injectField(worldServer, field, expectedThread);
				}
			}
		}
	}
	
	/**
	 * Inject the entity tracker list.
	 * @param world - the world.
	 * @param expectedThread - the expected thread.
	 */
	private void injectEntityTracker(World world, Thread expectedThread) {
		Object tracker = Minecraft.getEntityTracker(world);
		
		// Get the field by its type
		Field field = FuzzyReflection.fromObject(tracker, true).
						getFieldByType("trackerEntries", Set.class);
		
		// Inject it!
		injectField(tracker, field, expectedThread);
	}
}
