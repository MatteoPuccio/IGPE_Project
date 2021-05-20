package com.mygdx.game.model;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;

public class Bullet {
	
	private Gun gun;
	Body body;
	BodyDef bDef;
	float size = 0.1f;
	
	public Bullet(Gun gun, Vector2 position, Vector2 direction, float speed) {
		this.gun = gun;
		bDef = new BodyDef();
		bDef.type = BodyType.DynamicBody;
		bDef.bullet = true;
		bDef.position.set(position);
		body = GameModel.getInstance().getWorld().createBody(bDef);
		CircleShape circle = new CircleShape();
		circle.setRadius(size);
		body.createFixture(circle, 0f);
		circle.dispose();
		body.setLinearVelocity(speed * direction.x, speed * direction.y);
	}
	
}
