package com.mygdx.game.model.weapons;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.constants.AnimationConstants;
import com.mygdx.game.model.entities.Entity;

public class RockMagic extends Magic {

	public RockMagic(Entity owner) {
		super(2, 0.4f, 8, 0.2f, 2, owner);
	}

	@Override
	public int getCurrentAnimationId() {
		return AnimationConstants.ROCK_ANIMATION;
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

	@Override
	public float getRotation() {
		// TODO Auto-generated method stub
		return 0;
	}

}
