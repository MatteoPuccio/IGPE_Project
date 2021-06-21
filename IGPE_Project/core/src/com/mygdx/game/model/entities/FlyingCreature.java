package com.mygdx.game.model.entities;

import com.badlogic.gdx.ai.steer.behaviors.Arrive;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.model.GameModel;

public class FlyingCreature extends Enemy {
	
	public FlyingCreature(Vector2 position) {
		super(position, 0.6f, true, 0);
		body.setUserData("flying creature");
		
		behavior = new Arrive<Vector2>(this, GameModel.getInstance().getCharacter())
				.setTimeToTarget(0.01f)
				.setArrivalTolerance(0)
				.setDecelerationRadius(0);
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
}
