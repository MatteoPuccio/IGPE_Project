package com.mygdx.game.model;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.model.collisions.CollisionHandler;
import com.mygdx.game.model.entities.Character;
import com.mygdx.game.model.entities.EnemiesHandler;
import com.mygdx.game.model.level.RoomHandler;
import com.mygdx.game.model.weapons.Magic;

public class GameModel {
	
	private Character character;
	
	private static GameModel gameModel = null;
	private World world;
	private Array<Body> bodiesToDispose;
	private Array<Body> bodiesToEnable;
	private Array<Body> bodiesToDisable;
	private boolean characterTransform;
	
	private int coins;
	private boolean settingMagicChangeScreen;
	private Magic pickedUpMagic;
	
	private Vector2 switchPosition;
	private final Vector2 initialSpawnPosition;
	
	private float switchAngle;

	private boolean newFloor;
	
	private GameModel() {
		initialSpawnPosition = new Vector2(10f,10f);
		bodiesToDispose = new Array<Body>();
		bodiesToEnable = new Array<Body>();
		bodiesToDisable = new Array<Body>();
		
		characterTransform = false;
		switchPosition = new Vector2();
		switchAngle = 0f;
		
		newFloor = false;
		world = new World(new Vector2(0,0), false);
		world.setContactListener(new CollisionHandler());
		
		coins = 0;
		settingMagicChangeScreen = false;
	}
	
	public static GameModel getInstance() {
		if(gameModel == null)
			gameModel = new GameModel();
		return gameModel;
	}
	
	public void init() {
		if(GameModel.getInstance().getCharacter() != null)
			GameModel.getInstance().addBodyToDispose(GameModel.getInstance().getCharacter().getBody()); 
		character = new Character(new Vector2(initialSpawnPosition));
		RoomHandler.getInstance().createRooms();
	}
	
	
	public Character getCharacter() {
		return character;
	}
	
	public World getWorld() {
		return world;
	}
	
	public boolean isSettingMagicChangeScreen() {
		return settingMagicChangeScreen;
	}
	
	public void setSettingMagicChangeScreen(boolean settingMagicChangeScreen, Magic pickedUpMagic) {
		this.settingMagicChangeScreen = settingMagicChangeScreen;
		this.pickedUpMagic = pickedUpMagic;
	}
	
	public void dispose() {
		world.dispose();
	}
	
	public void addBodyToDispose(Body b) {
		bodiesToDispose.add(b);
	}
	
	public void addBodyToEnable(Array<Body> bodies,boolean enabled) {
		if(enabled)
			bodiesToEnable.addAll(bodies);
		else
			bodiesToDisable.addAll(bodies);
	}
	
	private void disposeBodies() {
		for(int i = 0; i < bodiesToDispose.size;++i)
			world.destroyBody(bodiesToDispose.get(i));
		bodiesToDispose.clear();
	}

	private void enableBodies() {
		for(Body body:bodiesToEnable)
			body.setActive(true);
		bodiesToEnable.clear();
	}
	
	private void disableBodies() {
		for(Body body:bodiesToDisable)
			body.setActive(false);
		bodiesToDisable.clear();
	}
	
	
	public void update(float deltaTime) {
		world.step(deltaTime, 6, 2);
		character.update(deltaTime);
		EnemiesHandler.update(deltaTime);
		RoomHandler.getInstance().updateTime(deltaTime);
		if(characterTransform) {
			characterTransform = false;
			character.getBody().setTransform(switchPosition, switchAngle);
		}
		if(newFloor) {
			character.getBody().setTransform(initialSpawnPosition, character.getBody().getAngle());
			RoomHandler.getInstance().createRooms();
			newFloor = false;
		}
		disposeBodies();
		enableBodies();
		disableBodies();
	}
	public void addCoins(int coinsToAdd) {
		coins += coinsToAdd;
	}

	public void setCharacterTransform(Vector2 position, float angle) {
		characterTransform = true;
		switchPosition = position;
		switchAngle = angle;
	}
	
	public Vector2 getInitialSpawnPosition() {
		return initialSpawnPosition;
	}

	public void setNewFloor() {
		newFloor = true;
	}
}