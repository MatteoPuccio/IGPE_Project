package com.mygdx.game.model;

import com.badlogic.gdx.math.Vector2;

public class Bullet {
	
	Vector2 position;
	
	
	public Bullet(Vector2 aim) {
		position.x = aim.x;
		position.y = aim.y;
	}
}
