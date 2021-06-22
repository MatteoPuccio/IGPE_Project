package com.mygdx.game.model.entities;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.model.GameModel;
import com.mygdx.game.model.ParticleHandler;
import com.mygdx.game.model.level.Room;

public abstract class Enemy extends Entity {
	
	protected Room home;
	
	public Enemy(Vector2 position, float radius, int manaPool) {
		super(position, radius, true, manaPool);
		body.setUserData("enemy");
	}
	
	@Override
	public void takeDamage(float damage) {
		health-=damage;
		if(health <= 0) {
			ParticleHandler.getInstance().addParticle(getPosition(), "enemy death explosion", radius, radius);
			GameModel.getInstance().addBodyToDispose(body);
			EnemiesHandler.getInstance().removeEnemy(this);
		}
		else {
			ParticleHandler.getInstance().addParticle(getPosition(), "hit", radius, radius);
		}
	}
	
	public void update(float deltaTime) {
		super.update(deltaTime);
	}
}
