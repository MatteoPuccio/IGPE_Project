package com.mygdx.game.model.collisions;

import com.badlogic.gdx.physics.box2d.Body;

public class Hole implements Collidable {

	private Body body;
	
	public Hole(Body body) {
		this.body = body;
		body.setUserData(this);
	}

	@Override
	public void collidesWith(Collidable coll) {
		// TODO Auto-generated method stub
		
	}
	
}
