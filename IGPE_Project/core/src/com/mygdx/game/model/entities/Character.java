package com.mygdx.game.model.entities;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.GameMain;
import com.mygdx.game.constants.AnimationConstants;
import com.mygdx.game.constants.ParticleEffectConstants;
import com.mygdx.game.constants.Settings;
import com.mygdx.game.constants.SoundConstants;
import com.mygdx.game.model.ParticleHandler;
import com.mygdx.game.model.collisions.Collidable;
import com.mygdx.game.model.weapons.LightningMagic;
import com.mygdx.game.model.weapons.Magic;
import com.mygdx.game.model.weapons.WaterMagic;
import com.mygdx.game.view.audio.SoundHandler;

public class Character extends Entity{
	
	private Magic firstMagic;
	private Magic secondMagic;
	
	private float speed = 4;
	
	private boolean leftMove,rightMove,downMove,upMove;
	
	private boolean invincible;
	
	private float invincibilityTimer;
	private float invincibilityElapsed;
	
	private float stepTimer;
	private float stepElapsed;
		
	public Character(Vector2 position) {
		super(position, 0.4f, false, 100, 10, 3);
		
		firstMagic = new WaterMagic(this);
		secondMagic = new LightningMagic(this);		
		
		leftMove = false;
		rightMove = false;
		downMove = false;
		upMove = false;		
		
		invincible = false;
		
		invincibilityTimer = 1.5f;
		invincibilityElapsed = 0;
		
		stepTimer = 0.3f;
		stepElapsed = 0;
	}
	
	private void move(float deltaTime) {
		direction.x = 0;
		direction.y = 0;
		
		if(leftMove) {
			direction.x = -1;
			flippedX = true;
		}
		if(rightMove) {
			direction.x = 1;
			flippedX = false;
		}
		if(downMove) {
			direction.y = -1;
		}
		if(upMove) {
			direction.y = 1;
		}
		
		body.setLinearVelocity(speed * direction.x, speed * direction.y);
	}
	
	public void setMove(int direction, boolean moving) {
		
		switch (direction) {
		case Settings.UP:
			upMove = moving;
			break;
		case Settings.RIGHT:
			rightMove = moving;
			break;
		case Settings.DOWN:
			downMove = moving;
			break;
		case Settings.LEFT:
			leftMove = moving;
			break;
		default:
			break;
		}
	}
	
	public void update(float deltaTime) 
	{
		super.update(deltaTime);
		move(deltaTime);
		
		firstMagic.update(deltaTime);
		secondMagic.update(deltaTime);
		
		if(invincible) {
			invincibilityElapsed += deltaTime;
			if(invincibilityElapsed >= invincibilityTimer) {
				invincibilityElapsed = 0;
				invincible = false;
			}
		}
		
		if(!body.getLinearVelocity().isZero()) {
			stepElapsed += deltaTime;
			if (stepElapsed >= stepTimer) {
				stepElapsed = 0;
				SoundHandler.getInstance().addSoundToQueue(SoundConstants.STEP);
			}
		}
		
	}

	@Override
	public void takeDamage(float damage) {
		if(!invincible) {
			currentHealth -= damage;
			invincible = true;
			SoundHandler.getInstance().addSoundToQueue(SoundConstants.HURT);
			ParticleHandler.getInstance().addParticle(getPosition(), ParticleEffectConstants.HIT, radius, radius);
			if(currentHealth <= 0)
				GameMain.getInstance().death();
		}
	}
	
	public void recoverHealth(float healthRecovered) {
		
		if(currentHealth + healthRecovered >= maxHealth)
			currentHealth = maxHealth;
		
		else
			currentHealth += healthRecovered;
		
	}
	
	public void recoverMana(float manaRecovered) {
		
		if(currentMana + manaRecovered >= manaPool)
			currentMana = manaPool;
		
		else
			currentMana += manaRecovered;
		
	}

	public void setFirstMagicAttacking(boolean attacking) {
		firstMagic.setAttacking(attacking);
	}
	
	public void setSecondMagicAttacking(boolean attacking) {
		secondMagic.setAttacking(attacking);
	}
	
	public void setAttackPoint(Vector2 attackPoint) {
		firstMagic.setAttackPoint(attackPoint);
		secondMagic.setAttackPoint(attackPoint);
	}

	public Magic getFirstMagic() {
		return firstMagic;
	}
	
	public Magic getSecondMagic() {
		return secondMagic;
	}
	
	public int getCurrentAnimationId() {
		if(direction.x == 0 && direction.y == 0) {
			if(invincible)
				return AnimationConstants.KNIGHT_INVINCIBLE_IDLE_ANIMATION;
			return AnimationConstants.KNIGHT_IDLE_ANIMATION;
		}
		else {
			if(invincible)
				return AnimationConstants.KNIGHT_INVINCIBLE_RUN_ANIMATION;
			return AnimationConstants.KNIGHT_RUN_ANIMATION;
		}
	}
	
	public boolean isFlipped() {
		return flippedX;
	}

	@Override
	public Vector2 getAnimationPosition() {
		return body.getPosition();
	}

	@Override
	public float getAnimationWidth() {
		return radius * 2;
	}

	@Override
	public float getAnimationHeigth() {
		return radius * 2;
	}

	@Override
	public float getRotation() {
		return 0;
	}

	public float getCurrentHealth() {
		return currentHealth;
	}

	@Override
	public void collidesWith(Collidable coll) {
		// TODO Auto-generated method stub
		
	}
	
	
}

