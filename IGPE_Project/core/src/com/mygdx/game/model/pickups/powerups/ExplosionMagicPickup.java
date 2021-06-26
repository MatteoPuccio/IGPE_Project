package com.mygdx.game.model.pickups.powerups;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.GameMain;
import com.mygdx.game.constants.AnimationConstants;
import com.mygdx.game.model.GameModel;
import com.mygdx.game.model.collisions.Collidable;
import com.mygdx.game.model.entities.Character;
import com.mygdx.game.model.level.Room;
import com.mygdx.game.model.pickups.Pickup;
import com.mygdx.game.model.weapons.ExplosionMagic;

public class ExplosionMagicPickup extends Pickup {

	public ExplosionMagicPickup(Vector2 position, Room home) {
		super(position, home, 0.6f);
	}

	@Override
	public int getCurrentAnimationId() {
		return AnimationConstants.EXPLOSION_MAGIC_ANIMATION;
	}
	
	@Override
	protected void collisionResponse(Character character) {
		character.setPickedUpMagic(new ExplosionMagic(character));
		GameMain.getInstance().changeMagicPrompt();
	}
}
