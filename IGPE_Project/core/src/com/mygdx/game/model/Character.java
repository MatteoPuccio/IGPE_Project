package com.mygdx.game.model;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class Character extends Entity{
	
	boolean leftMove,rightMove,downMove,upMove;
	
	public Character(World world, Vector2 position, float radius) 
	{
		super(world, position, radius);
		leftMove = false;
		rightMove = false;
		downMove = false;
		upMove = false;
	}	
	public void move(float deltaTime)
	{
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
	
	public void setLeftMove(boolean leftMove) {
		this.leftMove = leftMove;
	}
	public void setRightMove(boolean rightMove) {
		this.rightMove = rightMove;
	}
	public void setUpMove(boolean upMove) {
		this.upMove = upMove;
	}
	public void setDownMove(boolean downMove) {
		this.downMove = downMove;
	}
}
