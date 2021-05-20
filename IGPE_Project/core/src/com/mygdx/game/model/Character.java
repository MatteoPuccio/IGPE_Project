package com.mygdx.game.model;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Settings;

public class Character extends Entity{
	
	boolean leftMove,rightMove,downMove,upMove;
	Vector2 lastDirection = new Vector2(0.5f,0);
	
	public Character(World world, Vector2 position, float radius) {
		super(world, position, radius);
		leftMove = false;
		rightMove = false;
		downMove = false;
		upMove = false;
	}
	
	public void move(float deltaTime) {
		direction.x = 0;
		direction.y = 0;
		
		if(leftMove) {
			direction.x = -1;
			lastDirection.set(-1, 0);
		}
		if(rightMove) {
			direction.x = 1;
			lastDirection.set(1f, 0);
		}
		if(downMove) {
			direction.y = -1;
			lastDirection.set(0, -1f);
		}
		if(upMove) {
			direction.y = 1;
			lastDirection.set(0, 1f);
		}
		body.setLinearVelocity(speed * direction.x, speed * direction.y);
	}
	
	public Vector2 getLastDirection() {
		return lastDirection;
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
		if(direction.x == -1)
			return true;
		return false;
	}
}

