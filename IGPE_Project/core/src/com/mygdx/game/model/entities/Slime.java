package com.mygdx.game.model.entities;

import com.badlogic.gdx.math.Vector2;

public class Slime extends Enemy {

	public Slime(Vector2 position, float radius) {
		super(position, radius);
	}

	@Override
	public String getCurrentAnimationString() {
		return "slime idle animation";
	}

	@Override
	public boolean isFlipped() {
		return false;
	}

	@Override
	public Vector2 getAnimationPosition() {
		return body.getPosition();
	}

	@Override
	public float getAnimationWidth() {
		return radius;
	}

	@Override
	public float getAnimationHeigth() {
		return radius;
	}
	
}
