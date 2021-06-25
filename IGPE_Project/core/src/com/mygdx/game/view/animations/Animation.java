package com.mygdx.game.view.animations;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class Animation {
	private Texture spriteSheet;
	protected Array<TextureRegion> frames;
	protected float maxFrameTime;
	protected float currentFrameTime;
	protected int frameCount;
	protected int frame;
	
	public Animation(String internalPath,int frameCount, float cycleTime) {
		
		spriteSheet = new Texture(internalPath);
		TextureRegion region = new TextureRegion(spriteSheet);
		
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
	
	public Animation(Array<TextureRegion> frames, int frameCount, float cycleTime) {
		this.frames = frames;
		this.frameCount = frameCount;
		maxFrameTime = cycleTime / frameCount;
	}
	
	public Animation(Animation old) {
		this.frames = new Array<TextureRegion>(old.frames);
		
		this.frameCount = frames.size;
		
		currentFrameTime = 0;
		maxFrameTime = old.maxFrameTime;

		frame = 0;
	}
	
	public void update(float deltaTime) {
		if(frameCount > 1) {
			currentFrameTime += deltaTime;
			
			if(currentFrameTime >= maxFrameTime)
			{
				++frame;
				currentFrameTime = 0;
			}
			if(frame == frameCount)
				frame = 0;
		}
	}
	
	public void dispose() {
		spriteSheet.dispose();
	}
	
	public TextureRegion getFrame() {
		return frames.get(frame);
	}
}
