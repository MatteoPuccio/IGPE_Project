package com.mygdx.game.model;

import com.badlogic.gdx.math.Vector2;

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
	
	public void shoot(Vector2 pointClicked) {
		if (loaded != 0) {
			loaded--;
		
		Bullet b = new Bullet(this, holder.getPosition().add(pointClicked.sub(holder.getPosition()).nor().scl(0.5f)), pointClicked.nor(), speed);
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
