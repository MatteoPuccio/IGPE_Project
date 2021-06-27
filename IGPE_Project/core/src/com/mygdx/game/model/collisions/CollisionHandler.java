 package com.mygdx.game.model.collisions;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

public class CollisionHandler implements ContactListener { 

	@Override
	public void beginContact(Contact contact) {
			
		Fixture fa = contact.getFixtureA();
		Fixture fb = contact.getFixtureB();
		
		//Gli user data dei bodies di box2d sono stati settati ai proprietari di tali bodies
		//Ogni oggetto che contiene un body implementa l'interfaccia Collidable
		if(fa.getBody().getUserData() != null && fa.getBody().getUserData() instanceof Collidable && fb.getBody().getUserData() != null && fb.getBody().getUserData() instanceof Collidable) {
			
			Collidable collA = (Collidable) fa.getBody().getUserData();
			Collidable collB = (Collidable) fb.getBody().getUserData();
			collA.collidesWith(collB);
			collB.collidesWith(collA);
			
		}
	}
	
	@Override
	public void endContact(Contact contact) {}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {}
	
}
