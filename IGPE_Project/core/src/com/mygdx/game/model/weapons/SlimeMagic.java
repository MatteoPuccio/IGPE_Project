package com.mygdx.game.model.weapons;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.model.entities.Entity;

public class SlimeMagic extends Magic {

	public SlimeMagic(Entity owner) {
		super(2, 2f, 10, 0.2f, 1, owner, "enemy bullet : slimeball");
	}

	@Override
	public String getCurrentAnimationString() {
		return "slimeball animation";
	}

	@Override
	public boolean isFlipped() {
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

	@Override
	public float getRotation() {
		return 0;
	}

}