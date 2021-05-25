package com.mygdx.game.view.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class InterfaceBar 
{
	public Texture background,foreground;
	
	public TextureRegion barFilled;
	public Vector2 position,barPosition;
	private int maxWidth = 0;
	InterfaceBar(String bg,String fg)
	{
		background = new Texture(bg);
		foreground = new Texture(fg);
		
		maxWidth = foreground.getWidth();
		barFilled = new TextureRegion(foreground,0,0,foreground.getWidth(),foreground.getHeight());
		
		position = new Vector2(50,Gdx.graphics.getHeight()-100);
		barPosition = new Vector2(position.x+12,position.y+10);

	}
	
	public void setPercentage(float percentage)
	{
		barFilled.setRegionWidth((int)(maxWidth * percentage));
	}

	public void dispose()
	{
		background.dispose();
		foreground.dispose();
	}

	public void update(float percentage)
	{
		setPercentage(percentage);
	}
}
