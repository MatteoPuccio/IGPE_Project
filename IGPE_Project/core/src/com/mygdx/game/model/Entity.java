package com.mygdx.game.model;

import com.badlogic.gdx.math.Vector2;

public class Entity 
{
	Vector2 position,direction;

	int health;
	
	public Entity(Vector2 position)
	{
		this.position = position;
	}
	
	public Vector2 getPosition() {
		return position;
	}
	
	public int getHealth() {
		return health;
	}
	
}
