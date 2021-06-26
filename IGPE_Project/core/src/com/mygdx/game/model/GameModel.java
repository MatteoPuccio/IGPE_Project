package com.mygdx.game.model;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.constants.PowerUpsConstants;
import com.mygdx.game.model.collisions.CollisionHandler;
import com.mygdx.game.model.entities.Character;
import com.mygdx.game.model.entities.EnemiesHandler;
import com.mygdx.game.model.level.RandomRoomGenerator;
import com.mygdx.game.model.level.RoomHandler;
import com.mygdx.game.model.weapons.BulletHandler;
import com.mygdx.game.model.weapons.Magic;
import com.mygdx.game.view.audio.SoundHandler;

public class GameModel {
	
	private Character character;
	
	private static GameModel instance = null;
	private World world;
	private Array<Body> bodiesToDispose;
	private Array<Body> bodiesToEnable;
	private Array<Body> bodiesToDisable;
	
	private boolean characterTransform;
	private boolean settingMagicChangeScreen;
	private boolean newFloor;
	
	private int coins;
	private Magic pickedUpMagic;
	
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
		settingMagicChangeScreen = false;
		
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
		currentFloor = 1;
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
	
	
	public void update(float deltaTime) {
		world.step(deltaTime, 6, 2);
		
		character.update(deltaTime);
		RoomHandler.getInstance().getCurrentRoom().update(deltaTime);
		
		if(characterTransform) {
			characterTransform = false;
			character.getBody().setTransform(switchPosition, switchAngle);
		}
		
		if(newFloor) 
			createNewFloor();
		
		enableBodies();
		disableBodies();
		disposeBodies();
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
	
	public int getFloor() {
		return currentFloor;
	}
	
	private void createNewFloor() {
		character.getBody().setTransform(initialSpawnPosition, character.getBody().getAngle());
		RoomHandler.getInstance().createRooms();
		currentFloor++;
		newFloor = false;
	}
}