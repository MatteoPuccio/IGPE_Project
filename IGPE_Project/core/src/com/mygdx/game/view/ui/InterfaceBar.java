package com.mygdx.game.view.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class InterfaceBar {
	private Texture background,foreground,icon;
	
	private TextureRegion barFilled;
	private Vector2 position,barPosition;
	private int maxWidth = 0;
	
	InterfaceBar(String bg,String fg, String icon, Vector2 position)
	{
		background = new Texture(bg);
		foreground = new Texture(fg);
		this.icon = new Texture(icon);
		
		maxWidth = foreground.getWidth();
		barFilled = new TextureRegion(foreground,0,0,foreground.getWidth(),foreground.getHeight());
		
		this.position = position;
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

	public Texture getIcon() {
		return icon;
	}

	public Vector2 getBarPosition() {
		return barPosition;
	}

	public Vector2 getPosition() {
		return position;
	}

	public Texture getBackground() {
		return background;
	}
	
	public TextureRegion getBarFilled() {
		return barFilled;
	}
	
}
