package com.mygdx.game.model;

import com.badlogic.gdx.math.Vector2;

public class Character extends Entity{
	
	boolean leftMove,rightMove,downMove,upMove;
	
	public Character(Vector2 position) 
	{
		super(position);
	}	
	public void move(float deltaTime)
	{
		if(leftMove)
			direction.x = -1;
		if(rightMove)
			direction.x = 1;
		if(downMove)
			direction.y = 1;
		if(upMove)
			direction.x = -1;
		position.add(direction.scl(deltaTime*speed));
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
