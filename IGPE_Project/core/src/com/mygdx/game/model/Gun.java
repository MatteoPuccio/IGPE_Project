package com.mygdx.game.model;

import com.badlogic.gdx.math.Vector2;

public class Gun {
	
	private Entity entity;
	
	private int damage;
	private int ammunition;
	private int loaded;
	private float range;
	private Vector2 direction;
	
	public Gun(int damage, int ammunition, float range, Character character) {
		this.damage = damage;
		this.ammunition = ammunition;
		loaded = ammunition;
		this.range = range;
		this.entity = character;
		this.direction = character.getLastDirection().scl(1.2f);
	}
	
	public void reload() {
		loaded = ammunition;
	}
	
	public void shoot() {
		loaded--;
		Bullet b = new Bullet(this, entity.getPosition().add(direction));
		//TODO: aggiungere proiettile a un 'qualcosa' per controllare dove va il proiettile
	}
	
	public int getDamage() {
		return damage;
	}
		
}
