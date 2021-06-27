package com.mygdx.game.view.animations;

import com.badlogic.gdx.math.Vector2;

//Un'animazione particolare che deve essere riprodotta una sola volta
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
			//Non looppa
			if(frame == frameCount)
				donePlaying = true;
		}
	}
	
	//Permette alla view di capire quando smettere di riprodurre questo particle effect
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
