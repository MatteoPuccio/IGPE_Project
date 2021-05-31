package com.mygdx.game.model.entities;

import com.badlogic.gdx.math.Vector2;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.mygdx.game.model.Animated;
import com.mygdx.game.model.GameModel;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;

public abstract class Entity implements Animated {
	protected Vector2 direction;
	protected float speed = 4;
	protected int health = 5;
	protected Body body;
	protected float radius;
	
	public Entity(Vector2 position, float radius)
	{
		this.radius = radius;
		body = createBody(position);
		direction = new Vector2(0,0);
	}
	
	private Body createBody(Vector2 position) {
		BodyDef bDef = new BodyDef();
		bDef.type = BodyType.DynamicBody;
		bDef.position.set(position);
		bDef.fixedRotation = true;
		Body b = GameModel.getInstance().getWorld().createBody(bDef);
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