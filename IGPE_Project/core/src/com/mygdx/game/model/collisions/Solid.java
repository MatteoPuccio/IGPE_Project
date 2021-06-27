package com.mygdx.game.model.collisions;

import com.badlogic.gdx.physics.box2d.Body;

//Rappresenta un oggetto solido nella tilemap
public class Solid implements Collidable {

	private Body body;
	
	public Solid(Body body) {
		
		this.body = body;
		body.setUserData(this);
		
	}
	
	public Body getBody() {
		return body;
	}
	
	@Override
	public void collidesWith(Collidable coll) {}
}
