package com.mygdx.game.model.entities;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.model.GameModel;
import com.mygdx.game.model.ParticleHandler;
import com.mygdx.game.model.level.Room;

public abstract class Enemy extends Entity {
	
	protected Room home;
	
	public Enemy(Vector2 position, float radius, int manaPool, int health, float manaRechargeMultiplier) {
		super(position, radius, true, manaPool, health, manaRechargeMultiplier);
	}
	
	@Override
	public void takeDamage(float damage) {
		health-=damage;
		if(health > 0)
			ParticleHandler.getInstance().addParticle(getPosition(), "hit", radius, radius);
	}
	
	public void update(float deltaTime) {
		super.update(deltaTime);
		if(health <= 0) {
			ParticleHandler.getInstance().addParticle(getPosition(), "enemy death explosion", radius, radius);
			GameModel.getInstance().addBodyToDispose(body);
			EnemiesHandler.removeEnemy(this);
		}
	}
}
