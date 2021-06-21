 package com.mygdx.game.model;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mygdx.game.model.entities.EnemiesHandler;
import com.mygdx.game.model.weapons.Bullet;

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
					if(userDataA.equals("slime") || userDataA.equals("goblin") || userDataA.equals("flying creature")) {
						for(Bullet b : BulletHandler.getInstance().getBullets()) {
							if(b.getBody() == fb.getBody()) {
								EnemiesHandler.getInstance().hitEnemy(fa.getBody(), b.getParent().getDamage());
							}
						}
					}
					GameModel.getInstance().addBodyToDispose(fb.getBody());
					BulletHandler.getInstance().removeBullet(fb.getBody());
					if(userDataA.equals("enemy bullet")) {
						BulletHandler.getInstance().removeBullet(fa.getBody());
						GameModel.getInstance().addBodyToDispose(fa.getBody());
					}
				}
				break;
			case "enemy bullet":
				if(!userDataA.equals("slime") && !userDataA.equals("goblin") && !userDataA.equals("flying creature") && !userDataA.equals("void"))
				{
					if(userDataA.equals("character")) {
						for(Bullet b : BulletHandler.getInstance().getBullets()) {
							if(b.getBody() == fb.getBody()) {
								GameModel.getInstance().getCharacter().takeDamage(b.getParent().getDamage());
								break;
							}
						}
					}
					GameModel.getInstance().addBodyToDispose(fb.getBody());
					BulletHandler.getInstance().removeBullet(fb.getBody());
				}
				break;
			case "flying creature":
				if(userDataA.equals("character"))
					GameModel.getInstance().getCharacter().takeDamage(1);
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
