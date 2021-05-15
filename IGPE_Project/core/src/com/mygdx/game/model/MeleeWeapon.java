package com.mygdx.game.model;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;

public class MeleeWeapon {
	
	private Entity holder;
	private float damage;
	private float hitRange;
	private float hitCooldown;
	
	public MeleeWeapon(float damage, float hitRange, float hitCooldown) {
		this.damage = damage;
		this.hitRange = hitRange;
		this.hitCooldown = hitCooldown;
	}
	
	public float getDamage() {
		return damage;
	}
	
	public float getHitRange() {
		return hitRange;
	}
	
	public float getHitCooldown() {
		return hitCooldown;
	}
	
	public void hit() {
		for(Entity e : EnemiesHandler.getInstance().getEnemies())
		{
			Circle c1 = new Circle(e.getPosition(), e.getDimension());
			Circle c2 = new Circle(holder.getPosition().add(holder.getDirection().nor()), hitRange);
		}
	}
	
}
