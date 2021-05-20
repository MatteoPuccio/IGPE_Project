package com.mygdx.game.view;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class Animation {
	private Array<TextureRegion> frames;
	private float maxFrameTIme;
	private float currentFrameTime;
	private int frameCount;
	private int frame;
	
	public Animation(TextureRegion region, int frameCount, float cycleTime) {
		frames = new Array<TextureRegion>();
		int frameWidth = region.getRegionWidth() / frameCount;
		for(int i = 0; i < frameCount; ++i) {
			frames.add(new TextureRegion(region, i * frameWidth, 0, frameWidth, region.getRegionHeight()));
		}
		this.frameCount = frameCount;
		maxFrameTIme = cycleTime / frameCount;
		frame = 0;
	}
	
	public void update(float deltaTime) {
		currentFrameTime += deltaTime;
		
		if(currentFrameTime >= maxFrameTIme)
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
