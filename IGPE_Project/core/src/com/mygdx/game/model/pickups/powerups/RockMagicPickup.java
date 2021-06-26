package com.mygdx.game.model.pickups.powerups;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.constants.AnimationConstants;
import com.mygdx.game.constants.SoundConstants;
import com.mygdx.game.model.GameModel;
import com.mygdx.game.model.collisions.Collidable;
import com.mygdx.game.model.entities.Character;
import com.mygdx.game.model.level.Room;
import com.mygdx.game.model.pickups.Pickup;
import com.mygdx.game.model.weapons.RockMagic;
import com.mygdx.game.view.audio.SoundHandler;

public class RockMagicPickup extends Pickup {

	public RockMagicPickup(Vector2 position, Room home) {
		super(position, home, 0.6f);
	}
	
	@Override
	public int getCurrentAnimationId() {
		return AnimationConstants.ROCK_MAGIC_ANIMATION;
	}
	
	@Override
	protected void collisionResponse(Character character) {
		SoundHandler.getInstance().addSoundToQueue(SoundConstants.MAGIC_PICKUP);
		GameModel.getInstance().setSettingMagicChangeScreen(true, new RockMagic(character));		
	}
}
