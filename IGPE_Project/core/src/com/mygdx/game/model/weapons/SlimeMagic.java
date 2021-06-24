package com.mygdx.game.model.weapons;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.constants.AnimationConstants;
import com.mygdx.game.model.entities.Entity;

public class SlimeMagic extends Magic {

	public SlimeMagic(Entity owner) {
		super(2, 2f, 10, 0.2f, 1, owner);
	}

	@Override
	public int getBulletAnimationId() {
		return AnimationConstants.SLIMEBALL_ANIMATION;
	}

}
