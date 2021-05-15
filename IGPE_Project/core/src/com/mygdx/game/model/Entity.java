package com.mygdx.game.model;


import com.badlogic.gdx.math.Vector2;

public class Entity 
{
	Vector2 position,direction;
	float dimension,speed = 2000; 		//raggio della collision box
	int health;
	
	public Entity(Vector2 position)
	{
		this.position = position;
		direction = new Vector2(0,0);
	}
	
	public Vector2 getPosition() {
		return position;
	}
	
	public int getHealth() {
		return health;
	}
	
	public float getDimension() {
		return dimension;
	}
	
	public Vector2 getDirection() {
		return direction;
	}
	
	public void takeDamage(float damage) {
		//Da implementare
	}
	
}
