package com.mygdx.game.model;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

public class CollisionHandler implements ContactListener
{ 

	@Override
	public void beginContact(Contact contact) {
			
		Fixture fa = contact.getFixtureA();
		Fixture fb = contact.getFixtureB();
		
		if(fb.getBody().getUserData() != null && fb.getBody().getUserData().equals("bullet") && (fa.getBody().getUserData() == null || !fa.getBody().getUserData().equals("character")))
		{
			GameModel.getInstance().addBodyToDispose(fb.getBody());
			System.out.println(fa.getBody().getUserData() + " " + fb.getBody().getUserData());
		}
	}

	@Override
	public void endContact(Contact contact) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub
		
	}
	
}
