package com.mygdx.game.model.entities;

import com.badlogic.gdx.ai.steer.behaviors.Arrive;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.constants.AnimationConstants;
import com.mygdx.game.model.GameModel;
import com.mygdx.game.model.collisions.Collidable;
import com.mygdx.game.model.level.Room;

public class FlyingCreature extends Enemy {
	
	private int damage;
	
	public FlyingCreature(Vector2 position, Room home) {
		super(position, 0.3f, 0, 3, 1, home);
		
		behavior = new Arrive<Vector2>(this, GameModel.getInstance().getCharacter())
				.setTimeToTarget(0.01f)
				.setArrivalTolerance(0)
				.setDecelerationRadius(0);
		
		damage = 1;
	}

	@Override
	public int getCurrentAnimationId() {
		return AnimationConstants.FLYING_CREATURE_FLYING_ANIMATION;
	}

	@Override
	public boolean isFlipped() {
		return false;
	}
	
	@Override
	public void collidesWith(Collidable coll) {
			
		if(coll instanceof Character) {
				
			Character temp = (Character) coll;
			temp.takeDamage(damage);
			
		}
	}
	
	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
	}
}
