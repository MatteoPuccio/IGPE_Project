	package com.mygdx.game.model;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.model.entities.Character;
import com.mygdx.game.model.entities.EnemiesHandler;
import com.mygdx.game.model.entities.FlyingCreature;
import com.mygdx.game.model.entities.Goblin;
import com.mygdx.game.model.entities.Slime;
import com.mygdx.game.model.level.RandomRoomGenerator;
import com.mygdx.game.model.level.Room1;
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
		createRooms();
		initEntities();
	}
	
	private void initEntities() {
		character = new Character(new Vector2(7.5f,7.5f), 0.4f);
		
//		Goblin goblin1 = new Goblin(new Vector2(6.5f,6.5f));
//		Goblin goblin2 = new Goblin(new Vector2(12.5f,7.5f));
//		Goblin goblin3 = new Goblin(new Vector2(15.5f,6.5f));
//		
//		FlyingCreature flyingCreature1 = new FlyingCreature(new Vector2(8,8));
//		FlyingCreature flyingCreature2 = new FlyingCreature(new Vector2(10,12));
//
//		EnemiesHandler.getInstance().addEnemy(goblin1);
//		EnemiesHandler.getInstance().addEnemy(goblin2);
//		EnemiesHandler.getInstance().addEnemy(goblin3);
//		
//		EnemiesHandler.getInstance().addEnemy(flyingCreature1);
//		EnemiesHandler.getInstance().addEnemy(flyingCreature2);
		
		Slime slime1 = new Slime(new Vector2(6,6));
		EnemiesHandler.getInstance().addEnemy(slime1);
	}
	
	private void createRooms() {
		//RandomRoomGenerator.getInstance().createRooms();
		RoomHandler.getInstance().setCurrentRoom(new Room1());
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
		EnemiesHandler.getInstance().update(deltaTime);
		
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