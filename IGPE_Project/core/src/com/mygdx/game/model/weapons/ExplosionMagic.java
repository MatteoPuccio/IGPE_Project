package com.mygdx.game.model.weapons;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.model.GameModel;
import com.mygdx.game.model.ParticleHandler;
import com.mygdx.game.model.collisions.Collidable;
import com.mygdx.game.model.entities.EnemiesHandler;
import com.mygdx.game.model.entities.Enemy;
import com.mygdx.game.model.entities.Entity;

//Konosuba reference
public class ExplosionMagic extends Magic {

	private float explosionDamage;
	private float explosionRadius;
	
	public ExplosionMagic(Entity owner) {
		super(0, 2.5f, 5, 0.3f, 10, owner);
		
		explosionDamage = 5;
		explosionRadius = 0.6f;
	}

	@Override
	public String getCurrentAnimationString() {
		return "bomb animation";
	}
	
	@Override
	protected void bulletDestroyedEffect(Collidable coll, Bullet bullet) {
		ParticleHandler.getInstance().addParticle(bullet.getPosition(), "explosion", explosionRadius, explosionRadius);
		explode(bullet.getPosition());
	}

	private void explode(Vector2 center) {
		Circle c1 = new Circle(center,explosionRadius);
		
		Array<Enemy> enemiesInArea = EnemiesHandler.getEnemiesInArea(c1);
		
		for(Enemy e : enemiesInArea)
			e.takeDamage(explosionDamage);
		
		Circle c2 = new Circle(GameModel.getInstance().getCharacter().getPosition(), GameModel.getInstance().getCharacter().getRadius());
		if(Intersector.overlaps(c1, c2))
			GameModel.getInstance().getCharacter().takeDamage(2);
	}
	
	@Override
	public boolean isFlipped() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Vector2 getAnimationPosition() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public float getAnimationWidth() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getAnimationHeigth() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getRotation() {
		// TODO Auto-generated method stub
		return 0;
	}

}
