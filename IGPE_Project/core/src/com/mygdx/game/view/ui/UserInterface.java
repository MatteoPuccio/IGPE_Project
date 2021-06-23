package com.mygdx.game.view.ui;

import com.mygdx.game.model.GameModel;

public class UserInterface 
{
	
	
	private static UserInterface instance = null;
	
	public InterfaceBar manaBar;
	
	private UserInterface()
	{
		manaBar = new InterfaceBar("mana_bar_bg.png", "mana_bar_fg.png");
		
	}
	
	public static UserInterface getInstance()
	{
		if(instance == null)
			instance = new UserInterface();
		return instance;
	}
	
	public void update()
	{
		manaBar.update(GameModel.getInstance().getCharacter().getManaPercentage());
	}
	
	public void dispose()
	{
		manaBar.dispose();
	}
}
