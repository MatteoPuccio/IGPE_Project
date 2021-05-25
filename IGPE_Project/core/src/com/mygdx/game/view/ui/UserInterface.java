package com.mygdx.game.view.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.model.GameModel;
import com.mygdx.game.model.Magic;

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
		manaBar.update(((Magic)GameModel.getInstance().getCharacter().getWeapon()).getPercentage());
	}
	
	public void dispose()
	{
		manaBar.dispose();
	}
}
