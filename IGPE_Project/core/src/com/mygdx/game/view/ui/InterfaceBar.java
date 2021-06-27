package com.mygdx.game.view.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class InterfaceBar {
	private Texture background,foreground,icon;
	
	private TextureRegion barFilled;
	private Vector2 position,barPosition;
	private int maxWidth;
	
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
	
	public void draw(SpriteBatch batchUI) {
		 batchUI.draw(background, position.x,position.y,background.getWidth(),background.getHeight());
	     batchUI.draw(barFilled,barPosition.x,barPosition.y,barFilled.getRegionWidth(),barFilled.getRegionHeight());
	     batchUI.draw(icon, position.x + background.getWidth()/2 - icon.getWidth() /2, (barPosition.y + position.y) / 2, icon.getWidth() ,icon.getHeight());
	}
	
	public void setPercentage(float percentage)
	{
		//setta la larghezza della barra in base alla percentuale fornita
		barFilled.setRegionWidth((int)(maxWidth * percentage));
	}

	public void dispose()
	{
		background.dispose();
		foreground.dispose();
		icon.dispose();
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
