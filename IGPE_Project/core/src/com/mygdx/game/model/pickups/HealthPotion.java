package com.mygdx.game.model.pickups;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.constants.AnimationConstants;
import com.mygdx.game.constants.SoundConstants;
import com.mygdx.game.model.collisions.Collidable;
import com.mygdx.game.model.entities.Character;
import com.mygdx.game.model.level.Room;
import com.mygdx.game.view.audio.SoundHandler;

public class HealthPotion extends Pickup {

	private float healthToRecover;
	
	public HealthPotion(Vector2 position, Room home) {
		super(position, home);
	
		this.healthToRecover = 2;
	}

	@Override
	public int getCurrentAnimationId() {
		return AnimationConstants.HEALTH_POTION_ANIMATION;
	}

	@Override
	public void collidesWith(Collidable coll) {
		
		if(coll instanceof Character) {
			
			SoundHandler.getInstance().addSoundToQueue(SoundConstants.HEALTH_POTION);
			Character temp = (Character) coll;
			temp.recoverHealth(healthToRecover);
			super.collidesWith(coll);
			
		}		
	}
	
}
