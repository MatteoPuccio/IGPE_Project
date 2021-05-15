package com.mygdx.game.model;

public class MeleeWeapon {
	
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
	
}
