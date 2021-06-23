package com.mygdx.game.model.entities;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.constants.AnimationConstants;
import com.mygdx.game.model.GameModel;
import com.mygdx.game.model.collisions.Collidable;
import com.mygdx.game.model.level.Room;
import com.mygdx.game.model.weapons.SlimeMagic;

public class Slime extends Enemy {

	private SlimeMagic slimeMagic;
	
	public Slime(Vector2 position, Room home) {
		super(position, 0.4f, 100, 5, 5, home);
		
		slimeMagic = new SlimeMagic(this);
		slimeMagic.setAttacking(true);
	}
	
	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
		
		slimeMagic.setAttackPoint(GameModel.getInstance().getCharacter().getPosition());
		slimeMagic.update(deltaTime);
	}

	@Override
	public int getCurrentAnimationId() {
		return AnimationConstants.SLIME_IDLE_ANIMATION;
	}

	@Override
	public boolean isFlipped() {
		return GameModel.getInstance().getCharacter().getPosition().x < getPosition().x;
	}

	@Override
	public Vector2 getAnimationPosition() {
		return body.getPosition();
	}

	@Override
	public float getAnimationWidth() {
		return radius;
	}

	@Override
	public float getAnimationHeigth() {
		return radius;
	}

	@Override
	public float getRotation() {
		return 0;
	}

	@Override
	public void collidesWith(Collidable coll) {
		// TODO Auto-generated method stub
		
	}

}
