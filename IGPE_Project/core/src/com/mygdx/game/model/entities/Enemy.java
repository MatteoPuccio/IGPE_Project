package com.mygdx.game.model.entities;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.model.GameModel;
import com.mygdx.game.model.level.Room;

public abstract class Enemy extends Entity {
	
	protected Room home;
	
	public Enemy(Vector2 position, float radius, boolean isSensor, int manaPool) {
		super(position, radius, isSensor, manaPool);
		body.setUserData("enemy");
	}
	
	@Override
	public void takeDamage(float damage) {
		health-=damage;
		if(health <= 0)
		{
			GameModel.getInstance().addBodyToDispose(body);
			EnemiesHandler.getInstance().removeEnemy(this);
		}
	}
	
	public void update(float deltaTime) {
		super.update(deltaTime);
	}
}
