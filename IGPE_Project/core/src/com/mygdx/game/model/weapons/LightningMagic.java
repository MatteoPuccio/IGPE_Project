package com.mygdx.game.model.weapons;

import com.mygdx.game.constants.AnimationConstants;
import com.mygdx.game.model.entities.Entity;

public class LightningMagic extends Magic {

	public LightningMagic(Entity owner) {
		super(5, 3, 20, 0.15f, 5, owner);
	}

	@Override
	public int getBulletAnimationId() {
		return AnimationConstants.LIGHTNINGBOLT_ANIMATION;
	}

}
