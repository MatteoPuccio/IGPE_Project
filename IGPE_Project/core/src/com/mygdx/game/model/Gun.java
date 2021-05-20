package com.mygdx.game.model;

public class Gun {
	
	private Character holder;
	
	private int damage;
	private int ammunition;
	private int loaded;
	private float range;
	private float speed;
	
	public Gun(int damage, int ammunition, float range, Character holder, float speed) {
		this.damage = damage;
		this.ammunition = ammunition;
		loaded = ammunition;
		this.range = range;
		this.holder = holder;
		this.speed = speed;
	}
	
	public void reload() {
		loaded = ammunition;
	}
	
	public void shoot() {
		if (loaded != 0) {
			loaded--;
			Bullet b = new Bullet(this, holder.getPosition().add(holder.getLastDirection().scl(0.5f)), speed);
			//TODO: aggiungere proiettile a un 'qualcosa' per controllare dove va il proiettile			
		}
		return;
	}
	
	public int getDamage() {
		return damage;
	}
	
	public float getRange() {
		return range;
	}
		
}
