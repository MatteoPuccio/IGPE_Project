package com.mygdx.game.model.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.model.GameModel;

public abstract class Enemy extends Entity {

	public Enemy(World world, Vector2 position, float radius) {
		super(world, position, radius);
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
