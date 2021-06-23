package com.mygdx.game.model.entities;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.GameMain;
import com.mygdx.game.Settings;
import com.mygdx.game.model.ParticleHandler;
import com.mygdx.game.model.collisions.Collidable;
import com.mygdx.game.model.weapons.RockMagic;
import com.mygdx.game.model.weapons.BulletHandler;
import com.mygdx.game.model.weapons.ExplosionMagic;
import com.mygdx.game.model.weapons.FireMagic;
import com.mygdx.game.model.weapons.LightningMagic;
import com.mygdx.game.model.weapons.Magic;

public class Character extends Entity{
	
	private Magic firstMagic;
	private Magic secondMagic;
	private Magic currentMagic;
	
	private float speed = 4;
	
	private boolean leftMove,rightMove,downMove,upMove;
	
	private boolean invincible;
	
	private float invincibilityTimer;
	private float invincibilityElapsed;
		
	public Character(Vector2 position) {
		super(position, 0.4f, false, 100, 10, 3);
		
		firstMagic = new RockMagic(this);
		secondMagic = new ExplosionMagic(this);		
		currentMagic = firstMagic;
				
		leftMove = false;
		rightMove = false;
		downMove = false;
		upMove = false;		
		
		invincible = false;
		
		invincibilityTimer = 1.5f;
		invincibilityElapsed = 0;
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
	
	public Magic getCurrentMagic() {
		return currentMagic;
	}

	public void update(float deltaTime) 
	{
		super.update(deltaTime);
		move(deltaTime);
		currentMagic.attack(deltaTime);
		if(invincible) {
			invincibilityElapsed += deltaTime;
			if(invincibilityElapsed >= invincibilityTimer) {
				invincibilityElapsed = 0;
				invincible = false;
			}
		}
	}

	@Override
	public void takeDamage(float damage) {
		if(!invincible) {
			currentHealth -= damage;
			invincible = true;
			ParticleHandler.getInstance().addParticle(getPosition(), "hit", radius, radius);
			if(currentHealth <= 0)
				GameMain.getInstance().death();
		}
	}

	public void setWeapon(int i) {
		switch(i)
		{
		case 1:
			currentMagic = firstMagic;
			break;
		case 2:
			if(secondMagic != null)
				currentMagic = secondMagic;
			break;
		}
		
	}

	public Magic getFirstMagic() {
		return firstMagic;
	}
	
	public Magic getSecondMagic() {
		return secondMagic;
	}
	
	public String getCurrentAnimationString() {
		if(direction.x == 0 && direction.y == 0) {
			if(invincible)
				return "knight invincible idle animation";
			return "knight idle animation";
		}
		else {
			if(invincible)
				return "knight invincible run animation";
			return "knight run animation";
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
		return radius;
	}

	@Override
	public float getAnimationHeigth() {
		return radius;
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

