package com.mygdx.game.model.entities;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.model.GameModel;
import com.mygdx.game.model.level.RoomHandler;

//Una classe di utility che applica i metodi sui nemici attualmente attivi
public class EnemiesHandler {
	
	public static Array<Enemy> getEnemies() {
		return RoomHandler.getInstance().getCurrentRoom().getEnemies();
	}
	
	public static void hitEnemy(Enemy hitEnemy, float damage) {
		for(int i = 0; i < getEnemies().size; ++i)
			if(getEnemies().get(i) == hitEnemy)
			{
				getEnemies().get(i).takeDamage(damage);
				return;
			}
	}
	
	//Metodo necessario per il pathfinding dei goblin
	//Restituisce true se qualche goblin si sta dirigendo già nella posizione specificata, false altrimenti
	public static boolean isPositionOccupied(Vector2 pos) {
		for(int i = 0; i < getEnemies().size; ++i) {
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
		GameModel.getInstance().addBodyToDispose(enemy.getBody());
		getEnemies().removeValue(enemy, true);
	}

	//Restituisce tutti i nemici all'interno di un cerchio
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
