package com.mygdx.game.model.pickups.powerups;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.constants.AnimationConstants;
import com.mygdx.game.constants.PowerUpsConstants;
import com.mygdx.game.model.collisions.Collidable;
import com.mygdx.game.model.entities.Character;
import com.mygdx.game.model.level.Room;
import com.mygdx.game.model.pickups.Pickup;

public class MagicCooldownPowerUp extends Pickup {
	public MagicCooldownPowerUp(Vector2 position, Room home) {
		super(position, home, 0.5f);
	}

	@Override
	public int getCurrentAnimationId() {
		return AnimationConstants.MAGIC_COOLDOWN_POWERUP_ANIMATION;
	}

	@Override
	protected void collisionResponse(Character character) {
		character.enablePowerUp(PowerUpsConstants.MAGIC_POWERUP);
	}
}
