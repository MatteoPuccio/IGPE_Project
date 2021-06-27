package com.mygdx.game.model.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ObjectMap;
import com.mygdx.game.GameMain;
import com.mygdx.game.constants.AnimationConstants;
import com.mygdx.game.constants.ParticleEffectConstants;
import com.mygdx.game.constants.PowerUpsConstants;
import com.mygdx.game.constants.Settings;
import com.mygdx.game.constants.SoundConstants;
import com.mygdx.game.controller.ParticleHandler;
import com.mygdx.game.controller.SoundHandler;
import com.mygdx.game.model.collisions.Collidable;
import com.mygdx.game.model.weapons.Magic;
import com.mygdx.game.model.weapons.RockMagic;

public class Character extends Entity{
	
	private Magic firstMagic;
	private Magic secondMagic;
	private Magic pickedUpMagic;
	
	private float speed = 4;
	
	private boolean leftMove,rightMove,downMove,upMove;
	
	private boolean invincible;
	
	private float invincibilityTimer;
	private float invincibilityElapsed;
	
	private float stepTimer;
	private float stepElapsed;

	private float speedMultiplier;
	private float magicCooldownMultiplier;
	
	private ObjectMap<Integer, Boolean> enabledPowerUps;
	private ObjectMap<Integer, Float> maxPowerUpsTimes;
	private ObjectMap<Integer, Float> elapsedPowerUpsTimes;
	
	public Character(Vector2 position) {
		super(position, 0.4f, false, 100, 10, 3);
		
		//parte con una sola magia
		firstMagic = new RockMagic(this);
		secondMagic = null;
		
		pickedUpMagic = null;
		
		leftMove = false;
		rightMove = false;
		downMove = false;
		upMove = false;		
		
		invincible = false;
		
		invincibilityTimer = 1.5f;
		invincibilityElapsed = 0;
		
		stepTimer = 0.3f;
		stepElapsed = 0;
		
		speedMultiplier = 1;
		magicCooldownMultiplier = 1;
		
		enabledPowerUps = new ObjectMap<Integer, Boolean>();
		maxPowerUpsTimes = new ObjectMap<Integer, Float>();
		elapsedPowerUpsTimes = new ObjectMap<Integer, Float>();
		
		initPowerUps();
	}
	
	private void initPowerUps() {
		
		enabledPowerUps.put(PowerUpsConstants.MANA_RECHARGE_POWERUP, false);
		enabledPowerUps.put(PowerUpsConstants.SPEED_POWERUP, false);
		enabledPowerUps.put(PowerUpsConstants.INVINCIBILITY_POWERUP, false);
		enabledPowerUps.put(PowerUpsConstants.MAGIC_COOLDOWN_POWERUP, false);
		
		maxPowerUpsTimes.put(PowerUpsConstants.MANA_RECHARGE_POWERUP, 60f);
		maxPowerUpsTimes.put(PowerUpsConstants.SPEED_POWERUP, 40f);
		maxPowerUpsTimes.put(PowerUpsConstants.INVINCIBILITY_POWERUP, 20f);
		maxPowerUpsTimes.put(PowerUpsConstants.MAGIC_COOLDOWN_POWERUP, 20f);
		
		elapsedPowerUpsTimes.put(PowerUpsConstants.MANA_RECHARGE_POWERUP, 0f);
		elapsedPowerUpsTimes.put(PowerUpsConstants.SPEED_POWERUP, 0f);
		elapsedPowerUpsTimes.put(PowerUpsConstants.INVINCIBILITY_POWERUP, 0f);
		elapsedPowerUpsTimes.put(PowerUpsConstants.MAGIC_COOLDOWN_POWERUP, 0f);
		
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
		
		body.setLinearVelocity(speed * direction.x * speedMultiplier, speed * direction.y * speedMultiplier);
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
		if(secondMagic != null)
			secondMagic.update(deltaTime);
		
		if(invincible) {
			invincibilityElapsed += deltaTime;
			if(invincibilityElapsed >= invincibilityTimer) {
				invincibilityElapsed = 0;
				invincible = false;
			}
		}
		
		//suono dei passi
		if(!body.getLinearVelocity().isZero()) {
			stepElapsed += deltaTime;
			if (stepElapsed >= stepTimer) {
				stepElapsed = 0;
				SoundHandler.getInstance().addSoundToQueue(SoundConstants.STEP);
			}
		}	
		
		for(Integer i : enabledPowerUps.keys()) {
			if(enabledPowerUps.get(i)) {
				float elapsed = elapsedPowerUpsTimes.get(i);
				elapsed += deltaTime;
				if(elapsed >= maxPowerUpsTimes.get(i)) {
					elapsed = 0;
					disablePowerUp(i);
				}
				elapsedPowerUpsTimes.put(i, elapsed);
			}
		}
		
		if(currentHealth <= 0)
			GameMain.getInstance().death();
	}

	@Override
	public void takeDamage(float damage) {
		if(!invincible) {
			currentHealth -= (damage * getDamageMultiplier());
			invincible = true;
			SoundHandler.getInstance().addSoundToQueue(SoundConstants.PLAYER_HIT);
			ParticleHandler.getInstance().addParticle(getPosition(), ParticleEffectConstants.HIT, radius, radius);
		}
	}
	
	private float getDamageMultiplier() {
		switch(Settings.getDifficulty()) {
		case Settings.EASY:
			return 0.5f;
		case Settings.NORMAL:
			return 1.0f;
		case Settings.HARD:
			return 1.5f;
		default:
			return 1.0f;
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
	
	public void swapFirstMagic() {
		if(pickedUpMagic != null)
			firstMagic = pickedUpMagic;
		pickedUpMagic = null;
	}
	
	public void swapSecondMagic() {
		if(pickedUpMagic != null)
			secondMagic = pickedUpMagic;
		pickedUpMagic = null;
	}
	
	public void pickedUpMagic(Magic pickedUpMagic) {
		this.pickedUpMagic = pickedUpMagic;
		if(secondMagic == null) {
			secondMagic = pickedUpMagic;
			pickedUpMagic = null;
		}
		else
			GameMain.getInstance().changeMagicPrompt();		//se si hanno due magie cambia schermo per scegliere quali tenere
	}
	
	public Magic getPickedUpMagic() {
		return pickedUpMagic;
	}

	public void setFirstMagicAttacking(boolean attacking) {
		firstMagic.setAttacking(attacking);
	}
	
	public void setSecondMagicAttacking(boolean attacking) {
		if(secondMagic != null)
			secondMagic.setAttacking(attacking);
	}
	
	public void setAttackPoint(Vector2 attackPoint) {
		firstMagic.setAttackPoint(attackPoint);
		if(secondMagic != null)
			secondMagic.setAttackPoint(attackPoint);
	}

	public Magic getFirstMagic() {
		return firstMagic;
	}
	
	public Magic getSecondMagic() {
		return secondMagic;
	}
	
	public void enablePowerUp(int powerup) {
		
		if(!enabledPowerUps.get(powerup)) {
			enabledPowerUps.put(powerup, true);
			
			switch(powerup) {
			
			case PowerUpsConstants.MANA_RECHARGE_POWERUP:
				manaRechargeMultiplier *= 2;
				break;
				
			case PowerUpsConstants.SPEED_POWERUP:
				speedMultiplier *= 1.5f;
				break;
				
			case PowerUpsConstants.MAGIC_COOLDOWN_POWERUP:
				magicCooldownMultiplier /= 2;
				firstMagic.setCooldownMultiplier(magicCooldownMultiplier);
				if(secondMagic != null)
					secondMagic.setCooldownMultiplier(magicCooldownMultiplier);
				break;
					
			case PowerUpsConstants.INVINCIBILITY_POWERUP:
				invincible = true;
				invincibilityTimer = maxPowerUpsTimes.get(PowerUpsConstants.INVINCIBILITY_POWERUP);
				break;
			}
		}
		else {
			elapsedPowerUpsTimes.put(powerup, 0f);
			invincibilityElapsed = 0;
		}
	}
	
	private void disablePowerUp(int powerup) {
		
		enabledPowerUps.put(powerup, false);
		
		switch(powerup) {
		
		case PowerUpsConstants.MANA_RECHARGE_POWERUP:
			manaRechargeMultiplier /= 2;
			break;
		
		case PowerUpsConstants.SPEED_POWERUP:
			speedMultiplier /= 1.5f;
			break;
		case PowerUpsConstants.MAGIC_COOLDOWN_POWERUP:
			magicCooldownMultiplier *= 2;
			firstMagic.setCooldownMultiplier(magicCooldownMultiplier);
			if(secondMagic != null)
				secondMagic.setCooldownMultiplier(magicCooldownMultiplier);
			break;
			
		case PowerUpsConstants.INVINCIBILITY_POWERUP:
			invincibilityTimer = 1.5f;
			invincibilityElapsed = 0;
			break;
		}
		
	}
	
	public int getCurrentAnimationId() {
		if(body.getLinearVelocity().isZero()) {
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
	public void collidesWith(Collidable coll) {}
	
	public void stopMoving() {
		body.setLinearVelocity(0f, 0f);
	}
	
}

