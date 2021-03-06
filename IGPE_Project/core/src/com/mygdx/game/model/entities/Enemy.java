package com.mygdx.game.model.entities;

import com.badlogic.gdx.math.Vector2;

import com.mygdx.game.constants.ParticleEffectConstants;
import com.mygdx.game.constants.SoundConstants;
import com.mygdx.game.controller.ParticleHandler;
import com.mygdx.game.controller.SoundHandler;
import com.mygdx.game.model.level.Room;

public abstract class Enemy extends Entity {
	
	protected Room home;
	
	public Enemy(Vector2 position, float radius, int manaPool, int health, float manaRechargeMultiplier, Room home) {
		super(position, radius, true, manaPool, health, manaRechargeMultiplier);
		
		this.home = home;
	}
	
	@Override
	public void takeDamage(float damage) {
		SoundHandler.getInstance().addSoundToQueue(SoundConstants.HIT);
		currentHealth -= damage;
		if(currentHealth > 0)
			ParticleHandler.getInstance().addParticle(getPosition(), ParticleEffectConstants.HIT, getAnimationWidth(), getAnimationHeigth());
	}
	
	public void update(float deltaTime) {
		super.update(deltaTime);
		if(currentHealth <= 0) {
			ParticleHandler.getInstance().addParticle(getPosition(), ParticleEffectConstants.ENEMY_DEATH_EXPLOSION, getAnimationWidth(), getAnimationHeigth());
			home.generateRandomPickup(getPosition());
			EnemiesHandler.removeEnemy(this);
		}
	}
	
	@Override
	public Vector2 getAnimationPosition() {
		return getBody().getPosition();
	}

	@Override
	public float getAnimationWidth() {
		return getRadius() * 2;
	}

	@Override
	public float getAnimationHeigth() {
		return getRadius() * 2;
	}

	@Override
	public float getRotation() {
		return 0;
	}
}
