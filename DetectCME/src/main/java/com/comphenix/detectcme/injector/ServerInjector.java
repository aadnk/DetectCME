package com.comphenix.detectcme.injector;

import java.lang.reflect.Field;
import org.bukkit.Server;
import org.bukkit.scoreboard.ScoreboardManager;

public class ServerInjector extends AbstractInjector {
	/**
	 * Inject our modified set into Minecraft.
	 * @param world - the server we need to inject.
	 * @param expectedThread - the thread that will be allowed to modify the set.
	 */
	public void inject(Server server, Thread expectedThread) {
		injectScoreboardManager(server.getScoreboardManager(), expectedThread);
	}
	
	/**
	 * Inject the list of scoreboards.
	 * @param manager - the scoreboard manager.
	 * @param expectedThread - the expected thread (main thread).
	 */
	private void injectScoreboardManager(ScoreboardManager manager, Thread expectedThread) {
		try {
			Field scoreboardsField = manager.getClass().getDeclaredField("scoreboards");
			
			injectField(manager, scoreboardsField, expectedThread);
		} catch (Exception e) {
			throw new RuntimeException("Cannot inject scoreboard.", e);
		}
	}
}
