package com.mygdx.game.view.ui;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.model.GameModel;

public class UserInterface 
{	
	private InterfaceBar manaBar,healthBar;
	private EquippedMagic firstEquippedMagic, secondEquippedMagic;
	private ScoreLabel coinsLabel, floorLabel;
	
	public UserInterface() {
		manaBar = new InterfaceBar("UI/bar_bg.png", "UI/mana_bar_fg.png","UI/drop_icon.png", new Vector2(50,668));
		healthBar = new InterfaceBar("UI/bar_bg.png", "UI/health_bar_fg.png","UI/heart_icon.png", new Vector2(50, 588));
		firstEquippedMagic = new EquippedMagic(new Vector2(800, 628));
		secondEquippedMagic = new EquippedMagic(new Vector2(900, 628));
		coinsLabel = new ScoreLabel("images/coin.png" ,new Vector2(700, 748));
		floorLabel = new ScoreLabel("images/floor.png" ,new Vector2(860, 748));
	}
	
	public void update()
	{
		healthBar.update(GameModel.getInstance().getCharacter().getHealthPercentage());
		manaBar.update(GameModel.getInstance().getCharacter().getManaPercentage());
	}
	
	public void dispose() {
		healthBar.dispose();
		manaBar.dispose();
		coinsLabel.dispose();
		floorLabel.dispose();
	}
	
	public InterfaceBar getManaBar() {
		return manaBar;
	}
	
	public InterfaceBar getHealthBar() {
		return healthBar;
	}
	
	public EquippedMagic getFirstEquippedMagic() {
		return firstEquippedMagic;
	}
	
	public EquippedMagic getSecondEquippedMagic() {
		return secondEquippedMagic;
	}
	
	public ScoreLabel getCoinsLabel() {
		return coinsLabel;
	}
	
	public ScoreLabel getFloorLabel() {
		return floorLabel;
	}
}
