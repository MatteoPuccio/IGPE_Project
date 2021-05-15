package com.mygdx.game.model;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;

public class Gun {
	
	int damage;
	int ammunition;
	float range;
	Vector2 aim;
	ArrayList<Bullet> bullets;
	ArrayList<Bullet> released;
	
	void reload() {
		bullets.clear();
		for (int i = 0; i < ammunition; i++) {
			bullets.add(new Bullet());
		}
	}
	
	void shoot() {
		released.add(bullets.get(0));
		bullets.remove(0);
		released.get(released.size()-1).x = aim.x;
		released.get(released.size()-1).y = aim.y;
	}
}
