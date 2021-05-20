package com.mygdx.game.model;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class GameModel {
	
	private Character character;
	private Gun gun;
	
	private static GameModel gameModel = null;
	private World world;
	
	private GameModel() {
		world = new World(new Vector2(0,0), false);
		character = new Character(world, new Vector2(5,5), 0.4f);
		gun = new Gun(0, 10, 100, character);
	}
	
	public static GameModel getInstance() {
		if(gameModel == null)
			gameModel = new GameModel();
		return gameModel;
	}
	
	public Character getCharacter() {
		return character;
	}
	
	public Gun getGun() {
		return gun;
	}
	
	public World getWorld() {
		return world;
	}
	
	public void dispose() {
		world.dispose();
	}
}
