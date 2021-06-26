package com.mygdx.game.model.pickups.powerups;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.GameMain;
import com.mygdx.game.constants.AnimationConstants;
import com.mygdx.game.model.entities.Character;
import com.mygdx.game.model.level.Room;
import com.mygdx.game.model.pickups.Pickup;
import com.mygdx.game.model.weapons.FireMagic;

public class FireMagicPickup extends Pickup {

	public FireMagicPickup(Vector2 position, Room home) {
		super(position, home, 0.6f);
	}

	@Override
	public int getCurrentAnimationId() {
		return AnimationConstants.FIRE_MAGIC_ANIMATION;
	}

	@Override
	protected void collisionResponse(Character character) {
		character.setPickedUpMagic(new FireMagic(character));
		GameMain.getInstance().changeMagicPrompt();
	}
	
}
