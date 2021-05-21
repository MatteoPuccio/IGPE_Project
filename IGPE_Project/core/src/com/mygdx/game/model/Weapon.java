package com.mygdx.game.model;

import com.badlogic.gdx.math.Vector2;

public abstract class Weapon {

	protected Character holder;
	
	protected Vector2 attackPoint;
	
	boolean attacking;
	
	protected int damage;
	protected float range;
	protected float cooldown;
	
	public Weapon(int damage, float range, float cooldown, Character holder) {
		this.damage = damage;
		this.range = range;
		this.cooldown = cooldown;
		this.holder = holder;
		
		attacking = false;
	}
	
	public abstract void attack(float deltaTime);
	
	public int getDamage() {
		return damage;
	}
	
	public float getRange() {
		return range;
	}
	
	public Character getHolder() {
		return holder;
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
