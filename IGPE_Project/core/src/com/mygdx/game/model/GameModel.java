package com.mygdx.game.model;

import com.badlogic.gdx.math.Vector2;

public class GameModel {
	
	private Character character;
	
	private static GameModel gameModel = null;
	
	private GameModel() {
		character = new Character(new Vector2(0,0));
	}
	
	public static GameModel getInstance() {
		if(gameModel == null)
			gameModel = new GameModel();
		return gameModel;
	}
	
	public Character getCharacter() {
		return character;
	}
}
