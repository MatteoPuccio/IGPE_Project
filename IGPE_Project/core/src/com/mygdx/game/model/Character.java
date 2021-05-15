package com.mygdx.game.model;

import com.badlogic.gdx.math.Vector2;

public class Character extends Entity{
	
	public Character(Vector2 position) 
	{
		super(position);
	}
	
	void move(float deltaTime)
	{
		position.add(direction.scl(deltaTime));
	}
}
