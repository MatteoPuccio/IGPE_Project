package com.mygdx.game.model;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;

public abstract class Entity implements Animated {
	protected Vector2 direction;
	protected float speed = 4;
	protected int health = 5;
	protected Body body;
	protected float radius;
	
	public Entity(World world, Vector2 position, float radius)
	{
		body = createBody(world, position, radius);
		direction = new Vector2(0,0);
		this.radius = radius;
	}
	
	private Body createBody(World world, Vector2 position, float radius) {
		BodyDef bDef = new BodyDef();
		bDef.type = BodyType.DynamicBody;
		bDef.position.set(position);
		bDef.fixedRotation = true;
		Body b = world.createBody(bDef);
		FixtureDef fDef = new FixtureDef();
		CircleShape circle = new CircleShape();
		circle.setRadius(radius);
		fDef.shape = circle;
		fDef.density = 1;
		b.createFixture(fDef);
		
		circle.dispose();
		return b;
	}
	
	public Vector2 getPosition() {
		return body.getPosition();
	}
	
	public int getHealth() {
		return health;
	}
	
	public Body getBody() {
		return body;
	}
	
	public float getRadius() {
		return radius;
	}
	
	public abstract void takeDamage(float damage);
	
}
