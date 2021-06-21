 package com.mygdx.game.model;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mygdx.game.model.entities.EnemiesHandler;

public class CollisionHandler implements ContactListener { 

	@Override
	public void beginContact(Contact contact) {
			
		Fixture fa = contact.getFixtureA();
		Fixture fb = contact.getFixtureB();

		if(fa.getBody().getUserData() != null && fb.getBody().getUserData() != null && fa.getBody().getUserData() instanceof String && fb.getBody().getUserData() instanceof String) {
			
			String userDataA = (String) fa.getBody().getUserData();
			String userDataB = (String) fb.getBody().getUserData();
			
			switch (userDataB) {
			case "character bullet":
				if(!userDataA.equals("character") && !userDataA.equals("void"))
				{
					GameModel.getInstance().addBodyToDispose(fb.getBody());
					BulletHandler.getInstance().removeBullet(fb.getBody());
					if(userDataA.equals("enemy"))
						EnemiesHandler.getInstance().hitEnemy(fa.getBody());
					if(userDataA.equals("enemy bullet"))
						BulletHandler.getInstance().removeBullet(fa.getBody());
				}
				break;
			case "enemy bullet":
				if(!userDataA.equals("enemy") && !userDataA.equals("void"))
				{
					GameModel.getInstance().addBodyToDispose(fb.getBody());
					BulletHandler.getInstance().removeBullet(fb.getBody());
					if(userDataA.equals("character"))
						GameModel.getInstance().getCharacter().takeDamage(1);
					if(userDataA.equals("character bullet"))
						BulletHandler.getInstance().removeBullet(fa.getBody());
				}
				break;
			default:
				break;
			}
			
		}		

//		if (fb.getBody().getUserData().equals("character") && fa.getBody().getUserData().equals("gate")) {
//			GameModel.getInstance().toChangeMap = true;
//		}
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
