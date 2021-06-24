package com.mygdx.game.model.pickups;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.constants.AnimationConstants;
import com.mygdx.game.model.GameModel;
import com.mygdx.game.model.collisions.Collidable;
import com.mygdx.game.model.entities.Character;
import com.mygdx.game.model.weapons.RockMagic;

public class RockMagicPickup extends Pickup {

	public RockMagicPickup(Vector2 position) {
		super(position);
	}
	
	@Override
	public int getCurrentAnimationId() {
		return AnimationConstants.ROCK_MAGIC_ANIMATION;
	}

	@Override
	public void collidesWith(Collidable coll) {

		if(coll instanceof Character) {
			
			Character temp = (Character) coll;
			GameModel.getInstance().setSettingMagicChangeScreen(true, new RockMagic(temp));
			
		}
		
	}

}
