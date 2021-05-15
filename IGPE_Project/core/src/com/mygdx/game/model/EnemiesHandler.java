package com.mygdx.game.model;

import java.util.ArrayList;

public class EnemiesHandler {
	
	private static EnemiesHandler enemiesHandler = null;
	
	private ArrayList<Entity> enemies;
	
	private EnemiesHandler() {
		enemies = new ArrayList<Entity>();
	}
	
	public static EnemiesHandler getInstance() {
		if(enemiesHandler == null)
			enemiesHandler = new EnemiesHandler();
		return enemiesHandler;
	}
	
	public ArrayList<Entity> getEnemies() {
		return enemies;
	}
	
}
