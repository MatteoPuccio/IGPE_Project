package com.mygdx.game.view.animations;

import com.badlogic.gdx.math.Vector2;

public interface Animated {
	
	public int getCurrentAnimationId();
	
	public boolean isFlipped();
	
	public Vector2 getAnimationPosition();
	
	public float getAnimationWidth();
	
	public float getAnimationHeigth();
	
	public float getRotation();
}
