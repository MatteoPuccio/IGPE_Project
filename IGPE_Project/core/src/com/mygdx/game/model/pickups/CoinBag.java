package com.mygdx.game.model.pickups;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.constants.AnimationConstants;
import com.mygdx.game.constants.SoundConstants;
import com.mygdx.game.model.GameModel;
import com.mygdx.game.model.collisions.Collidable;
import com.mygdx.game.model.entities.Character;
import com.mygdx.game.model.level.Room;
import com.mygdx.game.view.audio.SoundHandler;

public class CoinBag extends Pickup {

	public CoinBag(Vector2 position, Room home) {
		super(position, home);
	}

	@Override
	public int getCurrentAnimationId() {
		return AnimationConstants.COIN_BAG_ANIMATION;
	}

	@Override
	public void collidesWith(Collidable coll) {

		if(coll instanceof Character) {
			SoundHandler.getInstance().addSoundToQueue(SoundConstants.COIN_BAG);
			GameModel.getInstance().addCoins(5);
			super.collidesWith(coll);
		}	
	}

}
