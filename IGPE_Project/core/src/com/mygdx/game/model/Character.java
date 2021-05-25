package com.mygdx.game.model;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Settings;

public class Character extends Entity{
	
	private MeleeWeapon meleeWeapon;
	private Magic magic;
	private Weapon weapon;
	
	private boolean leftMove,rightMove,downMove,upMove;
	
	boolean flippedX;
	
	public Character(World world, Vector2 position, float radius) {
		super(world, position, radius);
		body.setUserData("character");
		
		magic = new Magic(1, 10, 0.2f, 10);
		meleeWeapon = new MeleeWeapon(1,2.0f,0.5f);		
		weapon = magic;
		
		leftMove = false;
		rightMove = false;
		downMove = false;
		upMove = false;
		
		flippedX = false;
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
	
	public String getCurrentAnimationString() {
		if(direction.x == 0 && direction.y == 0)
			return "knight idle animation";
		else
			return "knight run animation";
	}
	
	public boolean isFlipped() {
		return flippedX;
	}
	
	public Weapon getWeapon() {
		return weapon;
	}

	public void update(float deltaTime) 
	{
		move(deltaTime);
		weapon.attack(deltaTime);
		if(weapon instanceof Magic)
			((Magic)weapon).rechargeMana(deltaTime);
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
}

