package com.mygdx.game.view.ui;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.model.GameModel;

public class UserInterface 
{	
	private static UserInterface instance = null;
	
	private InterfaceBar manaBar,healthBar;
	
	private UserInterface()
	{
		Vector2 manaBarPosition = new Vector2(50,668);
		manaBar = new InterfaceBar("bar_bg.png", "mana_bar_fg.png","drop_icon.png", manaBarPosition);
		Vector2 healthBarPosition = new Vector2(50, manaBarPosition.y - 80);
		healthBar = new InterfaceBar("bar_bg.png", "health_bar_fg.png","heart_icon.png", healthBarPosition);
	}
	
	public static UserInterface getInstance()
	{
		if(instance == null)
			instance = new UserInterface();
		return instance;
	}
	
	public void update()
	{
		healthBar.update(GameModel.getInstance().getCharacter().getHealthPercentage());
		manaBar.update(GameModel.getInstance().getCharacter().getManaPercentage());
	}
	
	public void dispose()
	{
		healthBar.dispose();
		manaBar.dispose();
	}
	
	public InterfaceBar getManaBar() {
		return manaBar;
	}
	
	public InterfaceBar getHealthBar() {
		return healthBar;
	}
}
