package com.mygdx.game.model;

import com.badlogic.gdx.utils.Array;

public class EnemiesHandler {
	
	private static EnemiesHandler enemiesHandler = null;
	
	private Array<Entity> enemies;
	
	private EnemiesHandler() {
		enemies = new Array<Entity>();
	}
	
	public static EnemiesHandler getInstance() {
		if(enemiesHandler == null)
			enemiesHandler = new EnemiesHandler();
		return enemiesHandler;
	}
	
	public Array<Entity> getEnemies() {
		return enemies;
	}
	
}
