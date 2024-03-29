package com.mygdx.game.model;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.model.collisions.CollisionHandler;
import com.mygdx.game.model.entities.Character;
import com.mygdx.game.model.level.RoomHandler;

public class GameModel {
	
	private Character character;
	
	private static GameModel instance = null;
	private World world;
	private Array<Body> bodiesToDispose;
	private Array<Body> bodiesToEnable;
	private Array<Body> bodiesToDisable;
	
	private boolean characterTransform;
	private boolean newFloor;
	
	private int coins;
	
	private Vector2 switchPosition;
	private final Vector2 initialSpawnPosition;
	private float switchAngle;
	
	private int currentFloor;
	
	private GameModel() {
		
		initialSpawnPosition = new Vector2(10f,10f);
		bodiesToDispose = new Array<Body>();
		bodiesToEnable = new Array<Body>();
		bodiesToDisable = new Array<Body>();
		
		characterTransform = false;
		newFloor = false;
		
		switchPosition = new Vector2();
		switchAngle = 0f;
		
		
		world = new World(new Vector2(0,0), false);
		world.setContactListener(new CollisionHandler());
		
		coins = 0;
	}
	
	public static GameModel getInstance() {
		if(instance == null)
			instance = new GameModel();
		return instance;
	}
	
	public void reset() {
		currentFloor = 0;
		if(character != null)
			world.destroyBody(GameModel.getInstance().getCharacter().getBody()); 
		character = new Character(new Vector2(initialSpawnPosition));
		RoomHandler.getInstance().createRooms();
		
		switchAngle = 0f;
		coins = 0;
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
	
	//Questi metodi sono necessari poich� box2d non permette la modifica del world mentre esegue lo step
	
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
		for(int i = 0; i < bodiesToDispose.size;++i) {
			world.destroyBody(bodiesToDispose.get(i));
		}
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
	
	public void setCharacterTransform(Vector2 position, float angle) {
		characterTransform = true;
		switchPosition = position;
		switchAngle = angle;
	}
	
	public void setNewFloor() {
		newFloor = true;
	}
	
	private void createNewFloor() {
		character.getBody().setTransform(initialSpawnPosition, character.getBody().getAngle());
		RoomHandler.getInstance().createRooms();
		currentFloor++;
		newFloor = false;
	}
	/*******************************************************************************************************/
	
	
	public void update(float deltaTime) {
		world.step(deltaTime, 6, 2);
		
		character.update(deltaTime);
		
		if(characterTransform) {
			characterTransform = false;
			character.getBody().setTransform(switchPosition, switchAngle);
		}
		
		if(newFloor) 
			createNewFloor();
		
		RoomHandler.getInstance().getCurrentRoom().update(deltaTime);
		
		enableBodies();
		disableBodies();
		disposeBodies();
	}
	public void addCoins(int coinsToAdd) {
		coins += coinsToAdd;
	}
	
	public int getFloor() {
		return currentFloor;
	}
	
	public int getCoins() {
		return coins;
	}

}