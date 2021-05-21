	package com.mygdx.game.model;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

public class GameModel {
	
	private Character character;
	private Gun gun;
	
	private static GameModel gameModel = null;
	private World world;
	private Array<Body> bodiesToDispose;
	
	private GameModel() {
		bodiesToDispose = new Array<Body>();
		world = new World(new Vector2(0,0), false);
		world.setContactListener(new CollisionHandler());
		character = new Character(world, new Vector2(5,5), 0.4f);
		gun = new Gun(0, 10, 100, character, 10);
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
	
	public void addBodyToDispose(Body b)
	{
		bodiesToDispose.add(b);
	}
	
	public void disposeBodies()
	{
		Array<Fixture> fixtures;
		for(Body b:bodiesToDispose)
		{
			fixtures = b.getFixtureList();
			for(Fixture f:fixtures)
				b.destroyFixture(f);
		}
		bodiesToDispose.clear();
	}
}
