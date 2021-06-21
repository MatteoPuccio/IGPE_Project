package com.mygdx.game.model.weapons;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.model.entities.Entity;

public abstract class Weapon {
	
	protected Entity owner;
	
	protected Vector2 attackPoint;
	
	protected boolean attacking;
	
	protected int damage;
	protected float range;
	protected float cooldown;
	protected float timePassed;
	
	public Weapon(int damage, float range, float cooldown, Entity owner) {
		this.damage = damage;
		this.range = range;
		this.cooldown = cooldown;
		this.owner = owner;
		
		this.timePassed = cooldown;
		
		attacking = false;
	}
	
	public abstract void attack(float deltaTime);
	
	public int getDamage() {
		return damage;
	}
	
	public float getRange() {
		return range;
	}

	public float getCooldown() {
		return cooldown;
	}
	
	public void setAttackPoint(Vector2 attackPoint) {
		this.attackPoint = attackPoint;
	}
	
	public Vector2 getAttackPoint() {
		return attackPoint;
	}
	
	public void setAttacking(boolean attacking) {
		this.attacking = attacking;
	}
	
	public boolean isAttacking() {
		return attacking;
	}
}
