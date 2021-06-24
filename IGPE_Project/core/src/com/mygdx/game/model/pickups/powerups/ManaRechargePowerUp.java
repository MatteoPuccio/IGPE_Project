package com.mygdx.game.model.pickups.powerups;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.constants.AnimationConstants;
import com.mygdx.game.constants.PowerUpsConstants;
import com.mygdx.game.model.collisions.Collidable;
import com.mygdx.game.model.entities.Character;
import com.mygdx.game.model.pickups.Pickup;

public class ManaRechargePowerUp extends Pickup {

	public ManaRechargePowerUp(Vector2 position) {
		super(position);
	}

	@Override
	public int getCurrentAnimationId() {
		return AnimationConstants.MANA_RECHARGE_POWERUP_ANIMATION;
	}

	@Override
	public void collidesWith(Collidable coll) {

		if(coll instanceof Character) {
			
			Character temp = (Character) coll;
			temp.enablePowerUp(PowerUpsConstants.MANA_RECHARGE_POWERUP);
			
		}
		
	}

}
