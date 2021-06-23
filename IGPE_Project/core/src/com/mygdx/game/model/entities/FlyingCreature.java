package com.mygdx.game.model.entities;

import com.badlogic.gdx.ai.steer.behaviors.Arrive;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.model.GameModel;
import com.mygdx.game.model.collisions.Collidable;

public class FlyingCreature extends Enemy {
	
	private int damage;
	
	public FlyingCreature(Vector2 position) {
		super(position, 0.3f, 0, 3, 1);
		
		behavior = new Arrive<Vector2>(this, GameModel.getInstance().getCharacter())
				.setTimeToTarget(0.01f)
				.setArrivalTolerance(0)
				.setDecelerationRadius(0);
		
		damage = 1;
	}

	@Override
	public String getCurrentAnimationString() {
		return "flying creature flying animation";
	}

	@Override
	public boolean isFlipped() {
		return false;
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
	public void update(float deltaTime) {
		super.update(deltaTime);
	}

	@Override
	public float getRotation() {
		return 0;
	}
	
	@Override
	public void collidesWith(Collidable coll) {
			
		if(coll instanceof Character) {
				
			Character temp = (Character) coll;
			temp.takeDamage(damage);
			
		}
	}
}
