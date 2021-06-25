package com.mygdx.game.model.weapons;

import com.mygdx.game.constants.AnimationConstants;
import com.mygdx.game.model.entities.Entity;

public class RockMagic extends Magic {

	public RockMagic(Entity owner) {
		super(2, 0.4f, 8, 0.2f, 2, owner);
	}

	@Override
	public int getBulletAnimationId() {
		return AnimationConstants.ROCK_ANIMATION;
	}


}
