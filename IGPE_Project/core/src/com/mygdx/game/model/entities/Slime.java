package com.mygdx.game.model.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class Slime extends Enemy {

	public Slime(World world, Vector2 position, float radius) {
		super(world, position, radius);
	}

	@Override
	public String getCurrentAnimationString() {
		return "slime idle animation";
	}

	@Override
	public boolean isFlipped() {
		return false;
	}
	
}
