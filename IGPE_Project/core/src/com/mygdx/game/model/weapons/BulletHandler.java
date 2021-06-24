package com.mygdx.game.model.weapons;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.model.GameModel;
import com.mygdx.game.model.ParticleHandler;
import com.mygdx.game.model.entities.EnemiesHandler;
import com.mygdx.game.model.entities.Enemy;

public class BulletHandler {
	
	private static BulletHandler bulletHandler = null;
	
	private Array<Bullet> bullets;
	
	private BulletHandler() {
		bullets = new Array<Bullet>(false, 20);
	}
	
	public static BulletHandler getInstance() {
		if(bulletHandler == null)
			bulletHandler = new BulletHandler();
		return bulletHandler;
	}
	
	public void addBullet(Bullet bullet) {
		bullets.add(bullet);
	}
	
	public void removeBullet(Bullet bullet) {
		
		for(int i = 0; i < bullets.size; ++i)
		{
			if(bullets.get(i) == bullet)
			{				
				bullets.removeIndex(i);
				GameModel.getInstance().addBodyToDispose(bullet.getBody());
			}
		}
	}
	
	public Array<Bullet> getBullets() {
		return bullets;
	}
}
