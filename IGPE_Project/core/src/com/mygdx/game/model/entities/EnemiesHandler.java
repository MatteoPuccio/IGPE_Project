package com.mygdx.game.model.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.model.level.RoomHandler;

public class EnemiesHandler {
	
	public static Array<Enemy> getEnemies() {
		return RoomHandler.getInstance().getCurrentRoom().getEnemies();
	}
	
	public static void addEnemy(Enemy enemy) {
		RoomHandler.getInstance().getCurrentRoom().addEnemy(enemy);
	}
	
	public static void hitEnemy(Body enemyBody, int damage) {
		for(int i = 0; i < getEnemies().size; ++i)
			if(getEnemies().get(i).getBody().equals(enemyBody))
			{
				getEnemies().get(i).takeDamage(damage);
				return;
			}
	}
	
	public static void update(float deltaTime) {
		for(Enemy e : getEnemies())
			e.update(deltaTime);
	}
	
	public static boolean isPositionOccupied(Vector2 pos) {
		for(int i = 0; i <getEnemies().size; ++i) {
			if(getEnemies().get(i) instanceof Goblin) {
				Goblin temp = (Goblin) getEnemies().get(i);
				if(temp.getNextPosition().equals(pos)) {
					return true;
				}
			}
		}
		return false;
	}

	public static void removeEnemy(Enemy enemy) {
		getEnemies().removeValue(enemy, true);
	}
	
}
