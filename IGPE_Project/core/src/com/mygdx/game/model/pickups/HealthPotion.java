package com.mygdx.game.model.pickups;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.constants.AnimationConstants;
import com.mygdx.game.model.collisions.Collidable;
import com.mygdx.game.model.entities.Character;

public class HealthPotion extends Pickup {

	private float healthToRecover;
	
	public HealthPotion(Vector2 position) {
		super(position);
	
		this.healthToRecover = 3;
	}

	@Override
	public int getCurrentAnimationId() {
		return AnimationConstants.HEALTH_POTION_ANIMATION;
	}

	@Override
	public void collidesWith(Collidable coll) {
		
		if(coll instanceof Character) {
			
			Character temp = (Character) coll;
			temp.recoverHealth(healthToRecover);
			
		}
	}

	
	
}
