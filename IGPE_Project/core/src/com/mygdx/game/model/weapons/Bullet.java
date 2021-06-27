package com.mygdx.game.model.weapons;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.mygdx.game.model.GameModel;
import com.mygdx.game.model.ai.SteeringUtils;
import com.mygdx.game.model.collisions.Collidable;
import com.mygdx.game.view.animations.Animated;

//Ogni Bullet cambia a seconda della Magic da cui è stato generato, perciò fa riferimento a molti metodi del parent
public class Bullet implements Animated, Collidable {
	
	private Magic parent;
	
	private Body body;
	
	private Vector2 direction;
	
	Bullet(Magic parent, Vector2 position, Vector2 direction) {
		this.parent = parent;
		this.direction = new Vector2(direction);
		
		BodyDef bDef = new BodyDef();
		bDef.type = BodyType.DynamicBody;
		bDef.position.set(position);
		
		body = GameModel.getInstance().getWorld().createBody(bDef);
		
		FixtureDef fDef = new FixtureDef();
		CircleShape circle = new CircleShape();
		circle.setRadius(parent.getBulletSize());
		fDef.density = 0;
		fDef.shape = circle;
		fDef.isSensor = true;
		
		body.createFixture(fDef);
		
		body.setUserData(this);
		
		//Fa partire il proiettile
		body.setLinearVelocity(this.direction.scl(parent.getSpeed()));
		
		circle.dispose();
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
	
	public Magic getParent() {
		return parent;
	}
	
	@Override
	public int getCurrentAnimationId() {
		return parent.getBulletAnimationId();
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
		return getSize() * 2;
	}

	@Override
	public float getAnimationHeigth() {
		return getSize() * 2;
	}

	@Override
	public float getRotation() {
		//Serve per alcuni proiettili la cui animazione ha un lato che deve puntare verso i nemici
		return (float) Math.toDegrees(SteeringUtils.vectorToAngle(new Vector2(-direction.x, -direction.y)));
	}
	
	@Override
	public void collidesWith(Collidable coll) {
		parent.bulletCollidedWith(coll, this);
	}
	
	
}
