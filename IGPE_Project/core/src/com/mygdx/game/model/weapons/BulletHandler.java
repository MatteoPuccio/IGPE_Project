package com.mygdx.game.model.weapons;

import com.badlogic.gdx.utils.Array;
import com.mygdx.game.model.GameModel;

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
		boolean bulletFound = false;
		for(int i = 0; i < bullets.size && !bulletFound; ++i)
		{
			if(bullets.get(i) == bullet)
			{
				bullets.removeIndex(i);
				GameModel.getInstance().addBodyToDispose(bullet.getBody());
				bulletFound = true;
			}
		}
	}
	
	public void removeAllBullets() {
		for(int i = 0; i < bullets.size; ++i)
			GameModel.getInstance().addBodyToDispose(bullets.get(i).getBody());
		bullets.clear();
	}
	
	public Array<Bullet> getBullets() {
		return bullets;
	}
}
