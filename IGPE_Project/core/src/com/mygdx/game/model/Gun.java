package com.mygdx.game.model;

import com.badlogic.gdx.math.Vector2;

public class Gun {
	
	private int damage;
	private int ammunition;
	private int loaded;
	private float range;
	private Vector2 direction;
	
	public Gun(int damage, int ammunition, float range) {
		this.damage = damage;
		this.ammunition = ammunition;
		loaded = ammunition;
		this.range = range;
	}
	
	public void reload() {
		loaded = ammunition;
	}
	
	public void shoot() {
		loaded--;
		Bullet b = new Bullet(direction);
		//TODO: aggiungere proiettile a un 'qualcosa' per controllare dove va il proiettile
	}
		
}
