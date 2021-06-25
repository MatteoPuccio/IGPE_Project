package com.mygdx.game.model.pickups;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.constants.AnimationConstants;
import com.mygdx.game.constants.SoundConstants;
import com.mygdx.game.model.GameModel;
import com.mygdx.game.model.collisions.Collidable;
import com.mygdx.game.model.entities.Character;
import com.mygdx.game.model.level.Room;
import com.mygdx.game.view.audio.SoundHandler;

public class Coin extends Pickup {

	public Coin(Vector2 position, Room home) {
		super(position, home);
	}

	@Override
	public int getCurrentAnimationId() {
		return AnimationConstants.COIN_ANIMATION;
	}
	
	@Override
	protected void collisionResponse(Character character) {
		SoundHandler.getInstance().addSoundToQueue(SoundConstants.COIN);
		GameModel.getInstance().addCoins(1);
	}

}
