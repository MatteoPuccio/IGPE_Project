package com.mygdx.game.model.collisions;

import com.badlogic.gdx.physics.box2d.Body;

public class Gate implements Collidable {

	private Body body;
	
	public Gate(Body body) {
		
		this.body = body;
		body.setUserData(this);
		
	}
	
}
