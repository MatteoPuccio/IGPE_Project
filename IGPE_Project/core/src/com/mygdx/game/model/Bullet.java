package com.mygdx.game.model;

import com.badlogic.gdx.math.Vector2;

public class Bullet extends Vector2{
	public Bullet(Vector2 aim) {
		this.x = aim.x;
		this.y = aim.y;
	}
}
