package com.mygdx.game.model;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;

public class Bullet implements Animated {
	
	private Magic parent;
	Body body;
	BodyDef bDef;
	float size = 0.1f;
	
	public Bullet(Magic parent, Vector2 position, Vector2 direction) {
		this.parent = parent;
		bDef = new BodyDef();
		bDef.type = BodyType.DynamicBody;
		bDef.bullet = true;
		bDef.position.set(position);
		body = GameModel.getInstance().getWorld().createBody(bDef);
		CircleShape circle = new CircleShape();
		circle.setRadius(size);
		body.createFixture(circle, 0f);
		body.setUserData("bullet");
		circle.dispose();
		body.setLinearVelocity(parent.getSpeed() * direction.x, parent.getSpeed() * direction.y);
	}
	
	public Body getBody() {
		return body;
	}

	@Override
	public String getCurrentAnimationString() {
		return "fireball animation";
	}

	@Override
	public boolean isFlipped() {
		return false;
	}
	
	public Vector2 getPosition() {
		return body.getPosition();
	}
	
	public float getSize() {
		return size;
	}
}
