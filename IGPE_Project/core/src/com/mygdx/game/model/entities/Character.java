package com.mygdx.game.model.entities;

import com.badlogic.gdx.math.Vector2;

import com.mygdx.game.Settings;
import com.mygdx.game.model.weapons.LightningMagic;
import com.mygdx.game.model.weapons.Magic;
import com.mygdx.game.model.weapons.MeleeWeapon;
import com.mygdx.game.model.weapons.Weapon;

public class Character extends Entity{
	
	private MeleeWeapon meleeWeapon;
	private Magic magic;
	private Weapon weapon;
	
	private float speed = 4;
	
	private boolean leftMove,rightMove,downMove,upMove;
	
	private boolean invincible;
	
	private float invincibilityTimer;
	private float invincibilityElapsed;
		
	public Character(Vector2 position, float radius) {
		super(position, radius, false, 20);
		body.setUserData("character");
		
		magic = new LightningMagic(this);
		meleeWeapon = new MeleeWeapon(1, 1f,0.4f, this);		
		weapon = magic;
				
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
	
	public Weapon getWeapon() {
		return weapon;
	}

	public void update(float deltaTime) 
	{
		super.update(deltaTime);
		move(deltaTime);
		weapon.attack(deltaTime);
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
			health -= damage;
			invincible = true;
			if(health <= 0)
				System.exit(0);
		}
	}

	public void setWeapon(int i) {
		switch(i)
		{
		case 1:
			weapon = magic;
			break;
		case 2:
			weapon = meleeWeapon;
			break;
		}
		
	}

	public Magic getMagic() {
		return magic;
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
	
	
}

