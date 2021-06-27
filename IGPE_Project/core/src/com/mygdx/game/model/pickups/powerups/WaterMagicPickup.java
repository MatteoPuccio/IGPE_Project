package com.mygdx.game.model.pickups.powerups;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.GameMain;
import com.mygdx.game.constants.AnimationConstants;
import com.mygdx.game.constants.SoundConstants;
import com.mygdx.game.controller.SoundHandler;
import com.mygdx.game.model.entities.Character;
import com.mygdx.game.model.level.Room;
import com.mygdx.game.model.pickups.Pickup;
import com.mygdx.game.model.weapons.WaterMagic;

public class WaterMagicPickup extends Pickup {

	public WaterMagicPickup(Vector2 position, Room home) {
		super(position, home, 0.6f);
	}
	
	@Override
	public int getCurrentAnimationId() {
		return AnimationConstants.WATER_MAGIC_ANIMATION;
	}
	
	@Override
	protected void collisionResponse(Character character) {
		SoundHandler.getInstance().addSoundToQueue(SoundConstants.MAGIC_PICKUP);
		character.setPickedUpMagic(new WaterMagic(character));
		GameMain.getInstance().changeMagicPrompt();	
	}

}
