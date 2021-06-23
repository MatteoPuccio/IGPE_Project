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
			 
			if(userDataB.startsWith("character bullet :")) {
				if(!userDataA.equals("character") && !userDataA.equals("void") && !userDataA.startsWith("character bullet :"))
				{
					if(userDataA.startsWith("enemy :")) {
						EnemiesHandler.hitEnemy(fa.getBody(), BulletHandler.getInstance().getDamageAndRemoveBullet(fb.getBody()));
						
					}
					
					else if(userDataA.startsWith("enemy bullet :")) {
						BulletHandler.getInstance().getDamageAndRemoveBullet(fb.getBody());
						BulletHandler.getInstance().getDamageAndRemoveBullet(fa.getBody());
					}
					
					else {
						BulletHandler.getInstance().getDamageAndRemoveBullet(fb.getBody());
					}
				}
			}
				
			else if(userDataB.startsWith("enemy bullet :")) {
				if(!userDataA.startsWith("enemy :") && !userDataA.equals("void") && !userDataA.startsWith("enemy bullet :"))
				{
					if(userDataA.equals("character")) {
						GameModel.getInstance().getCharacter().takeDamage(BulletHandler.getInstance().getDamageAndRemoveBullet(fb.getBody()));
					}
					
					else if(userDataA.startsWith("character bullet :")) {
						BulletHandler.getInstance().getDamageAndRemoveBullet(fb.getBody());
						BulletHandler.getInstance().getDamageAndRemoveBullet(fa.getBody());
					}
					
					else {
						BulletHandler.getInstance().getDamageAndRemoveBullet(fb.getBody());
					}
				}
			}
				
			else if(userDataB.contains("flying creature")) {
				if(userDataA.equals("character"))
					GameModel.getInstance().getCharacter().takeDamage(1);
				
				else if (userDataA.startsWith("character bullet :")) {
					EnemiesHandler.hitEnemy(fb.getBody(), BulletHandler.getInstance().getDamageAndRemoveBullet(fa.getBody()));
				}
			}
				
			else {
				if(userDataA.startsWith("character bullet :")) { 
					if(!userDataB.equals("character") && !userDataB.equals("void") && !userDataB.startsWith("character bullet :"))
					{
						if(userDataB.startsWith("enemy :")) {
							EnemiesHandler.hitEnemy(fb.getBody(), BulletHandler.getInstance().getDamageAndRemoveBullet(fa.getBody()));
						}
						
						else if(userDataB.startsWith("enemy bullet :")) {
							BulletHandler.getInstance().getDamageAndRemoveBullet(fa.getBody());
							BulletHandler.getInstance().getDamageAndRemoveBullet(fb.getBody());
						}
						
						else {
							BulletHandler.getInstance().getDamageAndRemoveBullet(fa.getBody());
						}
					}
				}
					
				else if(userDataA.startsWith("enemy bullet :")) {
					if(!userDataB.startsWith("enemy :") && !userDataB.equals("void") && !userDataB.startsWith("enemy bullet :"))
					{
						if(userDataB.equals("character")) {
							GameModel.getInstance().getCharacter().takeDamage(BulletHandler.getInstance().getDamageAndRemoveBullet(fa.getBody()));
						}
						
						else if(userDataB.startsWith("character bullet :")) {
							BulletHandler.getInstance().getDamageAndRemoveBullet(fa.getBody());
							BulletHandler.getInstance().getDamageAndRemoveBullet(fb.getBody());
						}
						
						else {
							BulletHandler.getInstance().getDamageAndRemoveBullet(fa.getBody());
						}
					}
				}
					
				else if(userDataA.contains("flying creature")) {
					if(userDataB.equals("character"))
						GameModel.getInstance().getCharacter().takeDamage(1);
					
					else if (userDataB.startsWith("character bullet :")) {
						EnemiesHandler.hitEnemy(fa.getBody(), BulletHandler.getInstance().getDamageAndRemoveBullet(fb.getBody()));
					}
				}
			}
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
