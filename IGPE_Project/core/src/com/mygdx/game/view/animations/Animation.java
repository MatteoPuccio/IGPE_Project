package com.mygdx.game.view.animations;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class Animation {
	protected Array<TextureRegion> frames;
	protected float maxFrameTime;
	protected float currentFrameTime;
	protected int frameCount;
	protected int frame;
	
	public Animation(TextureRegion region, int frameCount, float cycleTime) {
		frames = new Array<TextureRegion>();
		int frameWidth = region.getRegionWidth() / frameCount;
		
		for(int i = 0; i < frameCount; ++i) {
			frames.add(new TextureRegion(region, i * frameWidth, 0, frameWidth, region.getRegionHeight()));
		}
		
		this.frameCount = frameCount;
		
		currentFrameTime = 0;
		try {
			maxFrameTime = cycleTime / frameCount;
		} catch (ArithmeticException e) {
			maxFrameTime = 0;
		}
		
		frame = 0;
	}
	
	public Animation(Animation old) {
		this.frames = new Array<TextureRegion>(old.frames);
		
		this.frameCount = frames.size;
		
		currentFrameTime = 0;
		maxFrameTime = old.maxFrameTime;
		frame = 0;
	}
	
	public void update(float deltaTime) {
		currentFrameTime += deltaTime;
		
		if(currentFrameTime >= maxFrameTime)
		{
			++frame;
			currentFrameTime = 0;
		}
		if(frame == frameCount)
			frame = 0;
	}
	
	public TextureRegion getFrame() {
		return frames.get(frame);
	}
}
