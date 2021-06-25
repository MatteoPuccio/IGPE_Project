package com.mygdx.game.model.pickups.powerups;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.constants.AnimationConstants;
import com.mygdx.game.constants.PowerUpsConstants;
import com.mygdx.game.model.collisions.Collidable;
import com.mygdx.game.model.entities.Character;
import com.mygdx.game.model.level.Room;
import com.mygdx.game.model.pickups.Pickup;

public class SpeedPowerUp extends Pickup {

	public SpeedPowerUp(Vector2 position, Room home) {
		super(position, home);
	}

	@Override
	public int getCurrentAnimationId() {
		return AnimationConstants.SPEED_POWERUP_ANIMATION;
	}

	@Override
	public void collidesWith(Collidable coll) {

		if(coll instanceof Character) {
			
			Character temp = (Character) coll;
			temp.enablePowerUp(PowerUpsConstants.SPEED_POWERUP);
			
		}
		
		super.collidesWith(coll);
		
	}

}
