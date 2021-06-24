package com.mygdx.game.model.pickups;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.constants.AnimationConstants;
import com.mygdx.game.model.GameModel;
import com.mygdx.game.model.collisions.Collidable;
import com.mygdx.game.model.entities.Character;

public class Coin extends Pickup {

	public Coin(Vector2 position) {
		super(position);
	}

	@Override
	public int getCurrentAnimationId() {
		return AnimationConstants.COIN_ANIMATION;
	}

	@Override
	public void collidesWith(Collidable coll) {

		if(coll instanceof Character)
			GameModel.getInstance().addCoins(1);
		
	}

	
	
}
