package com.mygdx.game.model;

public class Gun {
	
	private Character holder;
	
	private int damage;
	private int ammunition;
	private int loaded;
	private float range;
	
	public Gun(int damage, int ammunition, float range, Character holder) {
		this.damage = damage;
		this.ammunition = ammunition;
		loaded = ammunition;
		this.range = range;
		this.holder = holder;
	}
	
	public void reload() {
		loaded = ammunition;
	}
	
	public void shoot() {
		loaded--;
		Bullet b = new Bullet(this, holder.getPosition().add(holder.getLastDirection().scl(0.5f)));
		//TODO: aggiungere proiettile a un 'qualcosa' per controllare dove va il proiettile
	}
	
	public int getDamage() {
		return damage;
	}
		
}
