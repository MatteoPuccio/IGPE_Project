package com.mygdx.game.model.weapons;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.model.entities.Entity;

public class FireMagic extends Magic {

	public FireMagic(Entity owner) {
		super(1, 10, 0.2f, 10, 0.1f, owner);
	}

	@Override
	public String getCurrentAnimationString() {
		return "fireball animation";
	}

	@Override
	public boolean isFlipped() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Vector2 getAnimationPosition() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public float getAnimationWidth() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getAnimationHeigth() {
		// TODO Auto-generated method stub
		return 0;
	}

}
