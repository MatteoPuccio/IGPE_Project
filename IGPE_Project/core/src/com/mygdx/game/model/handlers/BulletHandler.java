package com.mygdx.game.model.handlers;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.model.weapons.Bullet;

public class BulletHandler {
	
	private static BulletHandler bulletHandler = null;
	
	private Array<Bullet> bullets;
	
	private BulletHandler() {
		bullets = new Array<Bullet>();
	}
	
	public static BulletHandler getInstance() {
		if(bulletHandler == null)
			bulletHandler = new BulletHandler();
		return bulletHandler;
	}
	
	public void addBullet(Bullet bullet) {
		bullets.add(bullet);
	}
	
	public void removeBullet(Body bulletBody) {
		for(int i = 0; i < bullets.size; ++i)
		{
			if(bullets.get(i).getBody() == bulletBody)
			{
				bullets.removeIndex(i);
				return;
			}
		}
	}
	
	public Array<Bullet> getBullets() {
		return bullets;
	}
}
