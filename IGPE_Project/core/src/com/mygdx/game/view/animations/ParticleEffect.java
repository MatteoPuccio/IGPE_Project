package com.mygdx.game.view.animations;

import com.badlogic.gdx.math.Vector2;

public class ParticleEffect extends Animation {

	private boolean donePlaying;
	private Vector2 effectPosition;
	private float width;
	private float heigth;
	
	public ParticleEffect(String internalPath, int frameCount, float cycleTime) {
		super(internalPath, frameCount, cycleTime);
		
		donePlaying = false;
		effectPosition = null;
	}
	
	public ParticleEffect(ParticleEffect old, Vector2 effectPosition, float width, float heigth) {
		super(old);
		
		donePlaying = false;
		this.effectPosition = new Vector2(effectPosition);
		this.width = width;
		this.heigth = heigth;
	}
	
	@Override
	public void update(float deltaTime) {
		if(!donePlaying) {
			currentFrameTime += deltaTime;
			
			if(currentFrameTime >= maxFrameTime)
			{
				++frame;
				currentFrameTime = 0;
			}
			if(frame == frameCount)
				donePlaying = true;
		}
	}
	
	public boolean isDonePlaying() {
		return donePlaying;
	}
	
	public Vector2 getEffectPosition() {
		return effectPosition;
	}
	
	public float getWidth() {
		return width;
	}
	
	public float getHeigth() {
		return heigth;
	}
	
}
