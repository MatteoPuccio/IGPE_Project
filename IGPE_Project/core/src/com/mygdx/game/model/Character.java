package com.mygdx.game.model;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Settings;

public class Character extends Entity{
	
	boolean leftMove,rightMove,downMove,upMove;
	
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
		
		if(leftMove)
			direction.x = -1;
		if(rightMove)
			direction.x = 1;
		if(downMove)
			direction.y = -1;
		if(upMove)
			direction.y = 1;
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
}
