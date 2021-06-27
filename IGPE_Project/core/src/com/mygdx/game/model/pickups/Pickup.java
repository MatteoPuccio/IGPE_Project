package com.mygdx.game.model.pickups;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.mygdx.game.model.GameModel;
import com.mygdx.game.model.collisions.Collidable;
import com.mygdx.game.model.entities.Character;
import com.mygdx.game.model.level.Room;
import com.mygdx.game.view.animations.Animated;

public abstract class Pickup implements Animated, Collidable {

	private Vector2 position;
	private Body body;
	private float radius;
	private Room home;
	
	private float timeBeforePickup;
	private float timeSinceCreation;
	
	public Pickup(Vector2 position, Room home, float radius) {
		
		this.position = new Vector2(position);
		this.radius = radius;
		this.home = home;
		body = createBody();
		
		timeBeforePickup = 0.5f;
		timeSinceCreation = 0;
	}
	
	public void update(float deltaTime) {
		timeSinceCreation += deltaTime;
	}
	
	public Vector2 getPosition() {
		return position;
	}
	
	public Body getBody() {
		return body;
	}
	
	public float getRadius() {
		return radius;
	}
	
	private Body createBody() {
		
		BodyDef bDef = new BodyDef();
		bDef.type = BodyType.StaticBody;
		bDef.position.set(position);
		
		Body b = GameModel.getInstance().getWorld().createBody(bDef);
		
		FixtureDef fDef = new FixtureDef();
		CircleShape shape = new CircleShape();
		shape.setRadius(radius);
		fDef.shape = shape;
		fDef.isSensor = true;
		
		b.createFixture(fDef);
		b.setUserData(this);
		
		return b;	
	}
	
	@Override
	public boolean isFlipped() {
		return false;
	}

	@Override
	public Vector2 getAnimationPosition() {
		return position;
	}

	@Override
	public float getAnimationWidth() {
		return radius * 2;
	}

	@Override
	public float getAnimationHeigth() {
		return radius * 2;
	}

	@Override
	public float getRotation() {
		return 0;
	}
	
	@Override
	public final void collidesWith(Collidable coll) {
		if(timeSinceCreation >= timeBeforePickup && coll instanceof Character) {
			Character temp = (Character) coll;
			collisionResponse(temp);
			home.removePickup(this);
		}
	}
	
	protected abstract void collisionResponse(Character character);
	
}
