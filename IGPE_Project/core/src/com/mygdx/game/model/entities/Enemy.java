package com.mygdx.game.model.entities;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.model.GameModel;

public abstract class Enemy extends Entity {

	public Enemy(Vector2 position, float radius) {
		super(position, radius);
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

}
