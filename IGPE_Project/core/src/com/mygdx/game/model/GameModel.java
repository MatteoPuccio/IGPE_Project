	package com.mygdx.game.model;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.model.entities.Character;
import com.mygdx.game.model.entities.Slime;

public class GameModel {
	
	private Character character;
	
	
	private static GameModel gameModel = null;
	private World world;
	private Array<Body> bodiesToDispose;
	
	private GameModel() {
		bodiesToDispose = new Array<Body>();
		world = new World(new Vector2(0,0), false);
		world.setContactListener(new CollisionHandler());
	}
	
	public static GameModel getInstance() {
		if(gameModel == null)
			gameModel = new GameModel();
		return gameModel;
	}
	
	public void initEntities() {
		character = new Character(new Vector2(5,5), 0.4f);
		
		Slime slime = new Slime(new Vector2(6,6), 0.4f);
		EnemiesHandler.getInstance().addEnemy(slime);
	}
	
	public Character getCharacter() {
		return character;
	}
	
	public World getWorld() {
		return world;
	}
	
	public void dispose() {
		world.dispose();
	}
	
	public void addBodyToDispose(Body b) {
		bodiesToDispose.add(b);
	}
	
	private void disposeBodies() {
		Array<Fixture> fixtures;
		for(Body b:bodiesToDispose)
		{
			fixtures = b.getFixtureList();
			for(Fixture f : fixtures)
				b.destroyFixture(f);
		}
		bodiesToDispose.clear();
	}

	public void update(float deltaTime) {
		world.step(deltaTime, 6, 2);
		character.update(deltaTime);
		
		disposeBodies();
	}
}