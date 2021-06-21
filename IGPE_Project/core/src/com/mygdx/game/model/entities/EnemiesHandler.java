package com.mygdx.game.model.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;

public class EnemiesHandler {
	
	private static EnemiesHandler enemiesHandler = null;
	
	private Array<Enemy> enemies;
	
	private EnemiesHandler() {
		enemies = new Array<Enemy>();
	}
	
	public static EnemiesHandler getInstance() {
		if(enemiesHandler == null)
			enemiesHandler = new EnemiesHandler();
		return enemiesHandler;
	}
	
	public Array<Enemy> getEnemies() {
		return enemies;
	}
	
	public void addEnemy(Enemy enemy) {
		enemies.add(enemy);
	}
	
	public void removeEnemy(Enemy enemy) {
		enemies.removeValue(enemy, true);
	}
	
	public void hitEnemy(Body enemyBody, int damage) {
		for(int i = 0; i < enemies.size; ++i)
			if(enemies.get(i).getBody().equals(enemyBody))
			{
				enemies.get(i).takeDamage(damage);
				return;
			}
	}
	
	public void update(float deltaTime) {
		for(Enemy e : enemies)
			e.update(deltaTime);
	}
	
	public boolean isPositionOccupied(Vector2 pos) {
		for(int i = 0; i <enemies.size; ++i) {
			if(enemies.get(i) instanceof Goblin) {
				Goblin temp = (Goblin) enemies.get(i);
				if(temp.getNextPosition().equals(pos)) {
					return true;
				}
			}
		}
		return false;
	}
	
}
