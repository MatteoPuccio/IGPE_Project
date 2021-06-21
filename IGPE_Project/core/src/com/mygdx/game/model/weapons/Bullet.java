package com.mygdx.game.model.weapons;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.mygdx.game.model.Animated;
import com.mygdx.game.model.GameModel;

public class Bullet implements Animated {
	
	Magic parent;
	
	private Body body;
	
	Bullet(Magic parent, Vector2 position, Vector2 direction, String userData) {
		this.parent = parent;
		
		BodyDef bDef = new BodyDef();
		bDef.type = BodyType.DynamicBody;
		bDef.bullet = true;
		bDef.position.set(position);
		body = GameModel.getInstance().getWorld().createBody(bDef);
		
		FixtureDef fDef = new FixtureDef();
		CircleShape circle = new CircleShape();
		circle.setRadius(parent.getBulletSize());
		fDef.density = 0;
		fDef.shape = circle;
		fDef.isSensor = true;
		body.createFixture(fDef);
		
		body.setUserData(userData);
		circle.dispose();
		body.setLinearVelocity(parent.getSpeed() * direction.x, parent.getSpeed() * direction.y);
	}
	
	public Body getBody() {
		return body;
	}
	
	public Vector2 getPosition() {
		return body.getPosition();
	}
	
	public float getSize() {
		return parent.getBulletSize();
	}
	
	@Override
	public String getCurrentAnimationString() {
		return parent.getCurrentAnimationString();
	}
	
	@Override
	public boolean isFlipped() {
		return false;
	}

	@Override
	public Vector2 getAnimationPosition() {
		return body.getPosition();
	}

	@Override
	public float getAnimationWidth() {
		return getSize();
	}

	@Override
	public float getAnimationHeigth() {
		return getSize();
	}
	
	
}
