package com.mygdx.game.model.pickups;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.constants.AnimationConstants;
import com.mygdx.game.constants.SoundConstants;
import com.mygdx.game.controller.SoundHandler;
//github.com/MatteoPuccio/IGPE_Project.git
import com.mygdx.game.model.entities.Character;
import com.mygdx.game.model.level.Room;

public class HealthPotion extends Pickup {

	private float healthToRecover;
	
	public HealthPotion(Vector2 position, Room home) {
		super(position, home, 0.3f);
	
		this.healthToRecover = 2;
	}

	@Override
	public int getCurrentAnimationId() {
		return AnimationConstants.HEALTH_POTION_ANIMATION;
	}

	@Override
	protected void collisionResponse(Character character) {
		SoundHandler.getInstance().addSoundToQueue(SoundConstants.HEALTH_POTION);
		character.recoverHealth(healthToRecover);
	}
	
}
