package com.mygdx.game.model.collisions;

import com.badlogic.gdx.physics.box2d.Body;

//Rappresenta i buchi della tilemap
public class Hole implements Collidable {

	private Body body;
	
	public Hole(Body body) {
		this.body = body;
		body.setUserData(this);
	}
	
	public Body getBody() {
		return body;
	}
	
	@Override
	public void collidesWith(Collidable coll) {}
}
