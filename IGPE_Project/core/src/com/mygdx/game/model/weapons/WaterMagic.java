package com.mygdx.game.model.weapons;

import com.mygdx.game.constants.AnimationConstants;
import com.mygdx.game.model.entities.Entity;

public class WaterMagic extends Magic {

	public WaterMagic(Entity owner) {
		super(0.2f, 0.05f, 12, 0.15f, 0.4f, owner);
	}

	@Override
	public int getBulletAnimationId() {
		return AnimationConstants.DROPLET_ANIMATION;
	}

	@Override
	public int getRespectivePickupAnimationId() {
		return AnimationConstants.WATER_MAGIC_ANIMATION;
	}

}
