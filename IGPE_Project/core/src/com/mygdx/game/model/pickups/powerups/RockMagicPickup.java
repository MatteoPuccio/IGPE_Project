package com.mygdx.game.model.pickups.powerups;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.constants.AnimationConstants;
import com.mygdx.game.model.GameModel;
import com.mygdx.game.model.collisions.Collidable;
import com.mygdx.game.model.entities.Character;
import com.mygdx.game.model.level.Room;
import com.mygdx.game.model.pickups.Pickup;
import com.mygdx.game.model.weapons.RockMagic;

public class RockMagicPickup extends Pickup {

	public RockMagicPickup(Vector2 position, Room home) {
		super(position, home);
	}
	
	@Override
	public int getCurrentAnimationId() {
		return AnimationConstants.ROCK_MAGIC_ANIMATION;
	}
	
	@Override
	protected void collisionResponse(Character character) {
		GameModel.getInstance().setSettingMagicChangeScreen(true, new RockMagic(character));		
	}
}
