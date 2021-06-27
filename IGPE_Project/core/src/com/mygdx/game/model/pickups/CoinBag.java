package com.mygdx.game.model.pickups;

import java.util.Random;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.constants.AnimationConstants;
import com.mygdx.game.constants.SoundConstants;
import com.mygdx.game.controller.SoundHandler;
import com.mygdx.game.model.GameModel;
import com.mygdx.game.model.entities.Character;
import com.mygdx.game.model.level.Room;

public class CoinBag extends Pickup {

	public CoinBag(Vector2 position, Room home) {
		super(position, home, 0.3f);
	}

	@Override
	public int getCurrentAnimationId() {
		return AnimationConstants.COIN_BAG_ANIMATION;
	}
	
	@Override
	protected void collisionResponse(Character character) {
		Random r = new Random();
		SoundHandler.getInstance().addSoundToQueue(SoundConstants.COINS);
		GameModel.getInstance().addCoins(r.nextInt(5) + 3);
	}

}
