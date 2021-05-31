package com.mygdx.game.model;

import com.badlogic.gdx.math.Vector2;

public interface Animated {
	
	public String getCurrentAnimationString();
	
	public boolean isFlipped();
	
	public Vector2 getAnimationPosition();
	
	public float getAnimationWidth();
	
	public float getAnimationHeigth();
}
