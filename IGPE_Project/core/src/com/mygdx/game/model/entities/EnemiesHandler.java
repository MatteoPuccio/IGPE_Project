package com.mygdx.game.model.entities;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.model.level.RoomHandler;

public class EnemiesHandler {
	
	public static Array<Enemy> getEnemies() {
		return RoomHandler.getInstance().getCurrentRoom().getEnemies();
	}
	
	public static void addEnemy(Enemy enemy) {
		RoomHandler.getInstance().getCurrentRoom().addEnemy(enemy);
	}
	
	public static void hitEnemy(Enemy hitEnemy, float damage) {
		for(int i = 0; i < getEnemies().size; ++i)
			if(getEnemies().get(i) == hitEnemy)
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

	public static Array<Enemy> getEnemiesInArea(Circle circle) {
		Array<Enemy> enemiesInArea = new Array<Enemy>();
		for(Enemy e : getEnemies()) {
			Circle c = new Circle(e.getPosition(), e.getRadius());
			if(Intersector.overlaps(circle, c))
				enemiesInArea.add(e);
		}
		
		return enemiesInArea;
	}
	
}
