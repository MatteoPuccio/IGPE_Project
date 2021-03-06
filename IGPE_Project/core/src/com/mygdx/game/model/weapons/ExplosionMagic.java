package com.mygdx.game.model.weapons;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.constants.AnimationConstants;
import com.mygdx.game.constants.ParticleEffectConstants;
import com.mygdx.game.constants.SoundConstants;
import com.mygdx.game.controller.ParticleHandler;
import com.mygdx.game.controller.SoundHandler;
import com.mygdx.game.model.GameModel;
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
		explosionRadius = 1f;
	}

	@Override
	public int getBulletAnimationId() {
		return AnimationConstants.BOMB_ANIMATION;
	}
	
	@Override
	protected void bulletDestroyedEffect(Collidable coll, Bullet bullet) {
		ParticleHandler.getInstance().addParticle(bullet.getPosition(), ParticleEffectConstants.EXPLOSION, explosionRadius * 2, explosionRadius * 2);
		explode(bullet.getPosition());
	}

	//Crea un aoe che danneggia tutti i nemici all'interno quando il proiettile generato da questa magia viene distrutto
	//Se il personaggio si trova all'interno di questa area subisce anche lui un danno, seppur ridotto
	private void explode(Vector2 center) {
		SoundHandler.getInstance().addSoundToQueue(SoundConstants.EXPLOSION);
		Circle c1 = new Circle(center,explosionRadius);
		
		Array<Enemy> enemiesInArea = EnemiesHandler.getEnemiesInArea(c1);
		
		for(Enemy e : enemiesInArea) {
			SoundHandler.getInstance().addSoundToQueue(SoundConstants.HIT);
			e.takeDamage(explosionDamage);
		}
		
		Circle c2 = new Circle(GameModel.getInstance().getCharacter().getPosition(), GameModel.getInstance().getCharacter().getRadius());
		if(Intersector.overlaps(c1, c2)) {
			GameModel.getInstance().getCharacter().takeDamage(explosionDamage / 2);
			SoundHandler.getInstance().addSoundToQueue(SoundConstants.PLAYER_HIT);
		}
	}

	@Override
	public int getRespectivePickupAnimationId() {
		return AnimationConstants.EXPLOSION_MAGIC_ANIMATION;
	}

}
