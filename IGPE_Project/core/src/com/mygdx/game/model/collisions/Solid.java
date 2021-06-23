package com.mygdx.game.model.collisions;

import com.badlogic.gdx.physics.box2d.Body;

public class Solid implements Collidable {

	private Body body;
	
	public Solid(Body body) {
		
		this.body = body;
		body.setUserData(this);
		
	}
	
}
