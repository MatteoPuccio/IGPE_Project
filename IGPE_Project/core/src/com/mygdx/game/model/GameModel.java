	package com.mygdx.game.model;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.model.collisions.CollisionHandler;
import com.mygdx.game.model.entities.Character;
import com.mygdx.game.model.entities.EnemiesHandler;
import com.mygdx.game.model.level.Room2;
import com.mygdx.game.model.level.RoomHandler;

public class GameModel {
	
	private Character character;
	
	private static GameModel gameModel = null;
	private World world;
	private Array<Body> bodiesToDispose;
	public boolean toChangeMap;
	
	private GameModel() {
		bodiesToDispose = new Array<Body>();
		world = new World(new Vector2(0,0), false);
		world.setContactListener(new CollisionHandler());
		toChangeMap = false;
	}
	
	public static GameModel getInstance() {
		if(gameModel == null)
			gameModel = new GameModel();
		return gameModel;
	}
	
	public void init() {
		character = new Character(new Vector2(9.5f,7.5f));
		createRooms();
	}
	
	private void createRooms() {
		//RandomRoomGenerator.getInstance().createRooms();
		RoomHandler.getInstance().setCurrentRoom(new Room2());
		RoomHandler.getInstance().getCurrentRoom().init();
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

	public void disposeMapBodies() {
		Array<Body> oldBodies = new Array<Body>();
		world.getBodies(oldBodies);
		for (Body b: oldBodies) {
			if (b.getUserData() != "character") {
				addBodyToDispose(b);
			}
		}
	}
	
	public void update(float deltaTime) {
		world.step(deltaTime, 6, 2);
		character.update(deltaTime);
		EnemiesHandler.update(deltaTime);
		
		disposeBodies();
	}
	
	public boolean changeMap() {
		if (toChangeMap) {
//			TODO: posizionamento nemici e personaggio nelle nuove stanze + animazione per cambio stanza
			disposeMapBodies();
			toChangeMap = false;
			return true;
		}
		return false;
	}
}