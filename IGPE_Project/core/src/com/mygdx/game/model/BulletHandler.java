package com.mygdx.game.model;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.model.entities.EnemiesHandler;
import com.mygdx.game.model.entities.Enemy;
import com.mygdx.game.model.weapons.Bullet;

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
	
	public float getDamageAndRemoveBullet(Body bulletBody) {
		
		for(int i = 0; i < bullets.size; ++i)
		{
			if(bullets.get(i).getBody() == bulletBody)
			{
				float damage = bullets.get(i).getParent().getDamage();
				
				if(bulletBody.getUserData() instanceof String) {
					String bulletData = (String) bulletBody.getUserData();
					
					if(bulletData.contains("bomb")) {
						ParticleHandler.getInstance().addParticle(bulletBody.getPosition(), "explosion", 0.6f, 0.6f);
						explode(bulletBody.getPosition(), 0.6f);
					}
				}
				
				bullets.removeIndex(i);
				GameModel.getInstance().addBodyToDispose(bulletBody);
				return damage;
			}
		}
		
		return 0;
	}
	
	private void explode(Vector2 center, float explosionRadius) {
		Circle c1 = new Circle(center,explosionRadius);
		
		Array<Enemy> enemiesInArea = EnemiesHandler.getEnemiesInArea(c1);
		
		for(Enemy e : enemiesInArea)
			e.takeDamage(5);
		
		Circle c2 = new Circle(GameModel.getInstance().getCharacter().getPosition(), GameModel.getInstance().getCharacter().getRadius());
		if(Intersector.overlaps(c1, c2))
			GameModel.getInstance().getCharacter().takeDamage(2);
	}
	
	public Array<Bullet> getBullets() {
		return bullets;
	}
}
