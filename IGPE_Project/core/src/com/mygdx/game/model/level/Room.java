package com.mygdx.game.model.level;

import java.util.Random;

import org.xguzm.pathfinding.gdxbridge.NavigationTiledMapLayer;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.constants.ParticleEffectConstants;
import com.mygdx.game.controller.ParticleHandler;
import com.mygdx.game.model.GameModel;
import com.mygdx.game.model.ai.NavTmxMapLoader;
import com.mygdx.game.model.collisions.Gate;
import com.mygdx.game.model.collisions.Hole;
import com.mygdx.game.model.collisions.Solid;
import com.mygdx.game.model.collisions.TreasureChest;
import com.mygdx.game.model.entities.Enemy;
import com.mygdx.game.model.pickups.Coin;
import com.mygdx.game.model.pickups.CoinBag;
import com.mygdx.game.model.pickups.HealthPotion;
import com.mygdx.game.model.pickups.Pickup;
import com.mygdx.game.model.pickups.powerups.ExplosionMagicPowerup;
import com.mygdx.game.model.pickups.powerups.FireMagicPowerup;
import com.mygdx.game.model.pickups.powerups.InvincibilityPowerup;
import com.mygdx.game.model.pickups.powerups.LightningMagicPowerup;
import com.mygdx.game.model.pickups.powerups.MagicCooldownPowerup;
import com.mygdx.game.model.pickups.powerups.ManaRechargePowerup;
import com.mygdx.game.model.pickups.powerups.RockMagicPowerup;
import com.mygdx.game.model.pickups.powerups.SpeedPowerup;
import com.mygdx.game.model.pickups.powerups.WaterMagicPowerup;

public class Room {
	private TiledMap tileMap;
	private NavigationTiledMapLayer navigationLayer;
	
	private int roomIndex;
	private Connection [] connections;
		
	private Array<Enemy> enemies;
	
	private Array<Gate> gates;
	private Array<Hole> holes;
	private Array<Solid> solids;
	private Array<TreasureChest> treasureChests;
	
	private Array<Class<? extends Pickup>> pickupTypes;
	private Array<Pickup> pickups;
	
	private Array<Class<? extends Pickup>> powerupTypes;
	private Array<Pickup> powerups;
	
	private float teleportTime;
	private float elapsedTeleportTime;
	
	private boolean generatePowerup;
	private Vector2 powerupSpawnPosition;
	
	private int endingPoint;
	
	public Room(String tileMapPath) {
		init(tileMapPath);
	}
	
	public Room(String tileMapPath, Connection connection) {
		init(tileMapPath);
		
		connection.generateEndingPoint(this, endingPoint);
		connections[endingPoint] = connection;
	}
	
	private void init(String tileMapPath) {
		//carica il pathfinding dei nemici
		tileMap = new NavTmxMapLoader().load(tileMapPath);
		connections = new Connection[4];
		
		teleportTime = 1f;
		elapsedTeleportTime = 0f;
		
		initPickups();
		initPowerups();
		
		parseMap(tileMap);
		
		if(tileMapPath.contains("rs")) {
			connections[gates.get(0).getDirection()] = new Connection(this, gates.get(0).getDirection());
			return;
		}
		
		if(tileMapPath.contains("rf")) {
			for(int i = 0; i < gates.size; ++i) {
				if(gates.get(i).getDirection() != Gate.END) {
					endingPoint = gates.get(i).getDirection();
					return;
				}
			}
		}
		
		Random r = new Random();
		//prendi un qualsiasi gate da cui chiudere la connessione e quindi da fare da entrata
		endingPoint = gates.get(r.nextInt(gates.size)).getDirection();
		
		for(int i = 0; i < gates.size; ++i) {
			//per tutti i gate (tranne l'ending point) crea una connessione aperta
			if(gates.get(i).getDirection() < connections.length && gates.get(i).getDirection() != endingPoint) {
				connections[gates.get(i).getDirection()] = new Connection(this, gates.get(i).getDirection());
			}
		}
	}
	
	public void update(float deltaTime) {
		for(Enemy enemy : enemies)
			enemy.update(deltaTime);
		
		for(Pickup pickup : pickups)
			pickup.update(deltaTime);
		
		for(Pickup powerup : powerups)
			powerup.update(deltaTime);
		
		if(generatePowerup) {
			generatePowerup = false;
			generateRandomPowerup();
		}
	}
	
	public void updateTeleportTime(float deltaTime) {
		if(elapsedTeleportTime + deltaTime <= teleportTime)
			elapsedTeleportTime += deltaTime;
		else
			elapsedTeleportTime = teleportTime;
	}
	
	private void initPickups() {
		pickups = new Array<Pickup>(false, 10);
		pickupTypes = new Array<Class<? extends Pickup>>();
		
		pickupTypes.add(Coin.class);
		pickupTypes.add(CoinBag.class);
		pickupTypes.add(HealthPotion.class);
	}
	
	private void initPowerups() {
		powerups = new Array<Pickup>(false,10);
		powerupTypes = new Array<Class<? extends Pickup>>();
		
		powerupTypes.add(ManaRechargePowerup.class);
		powerupTypes.add(SpeedPowerup.class);
		powerupTypes.add(InvincibilityPowerup.class);
		powerupTypes.add(MagicCooldownPowerup.class);
		
		powerupTypes.add(FireMagicPowerup.class);
		powerupTypes.add(LightningMagicPowerup.class);
		powerupTypes.add(RockMagicPowerup.class);
		powerupTypes.add(ExplosionMagicPowerup.class);
		powerupTypes.add(WaterMagicPowerup.class);
		
		generatePowerup = false;
	}
	
	//una connessione è aperta se la ending room è null
	public boolean hasFreeConnection() {
		boolean freeConnection = false;
		for(Connection connection:connections) {
			if(connection != null)
				if(connection.getEndingRoom() == null)
					freeConnection = true;
		}
		return freeConnection;
	}
	
	//restituisce la prima connessione libera
	public Connection getFreeConnection() {
		for(Connection connection:connections) {
			if(connection != null)
				if(connection.getEndingRoom() == null)
					return connection;
		}
		return null;
	}
	
	//crea room partendo chiudendo una connessione libera
	public Room createAdjacentRoom() {
		Connection roomConnection = getFreeConnection();
		Room newRoom = RandomRoomGenerator.getInstance().createRoom(roomConnection, false);
		return newRoom;
	}
	
	//chiudi tutte le connessioni libere creando una nuova stanza
	public Array<Room> createAdjacentRooms(boolean deadend) {
		Array<Room> rooms = new Array<Room>();
		Connection roomConnection;
		
		while(hasFreeConnection()) {
			roomConnection = getFreeConnection();
			Room newRoom = RandomRoomGenerator.getInstance().createRoom(roomConnection, deadend);
			rooms.add(newRoom);
		}
		
		return rooms;
	}
	
	public NavigationTiledMapLayer getNavigationLayer() {
		return navigationLayer;
	}
	
	public TiledMap getTileMap() {
		return tileMap;
	}
	
	public int getRoomIndex() {
		return roomIndex;
	}
	
	public Connection[] getConnections() {
		return connections;
	}
	
	public Array<Enemy> getEnemies() {
		return enemies;
	}
	
	public Array<TreasureChest> getTreasureChests() {
		return treasureChests;
	}
	
	public void addEnemy(Enemy enemy) {
		enemies.add(enemy);
	}
	
	protected void parseMap(TiledMap tileMap) {
		gates = TiledMapObjectsUtil.parseGates(tileMap);
		holes = TiledMapObjectsUtil.parseHoles(tileMap);
		solids = TiledMapObjectsUtil.parseSolid(tileMap);
		treasureChests = TiledMapObjectsUtil.parseTreasureChests(tileMap);
		
		enemies = TiledMapObjectsUtil.parseEnemies(tileMap, this);
		navigationLayer = TiledMapObjectsUtil.getNavigationTiledMapLayer(tileMap);
	}

	public void enableBodies(boolean enabled) {
		//abilita o disabilita tutti i bodies appartenenti alla room
		Array<Body> bodies = new Array<Body>();
		for(Gate gate:gates)
			bodies.add(gate.getBody());
		for(Hole hole:holes)
			bodies.add(hole.getBody());
		for(Solid solid:solids)
			bodies.add(solid.getBody());
		for(Enemy enemy:enemies)
			bodies.add(enemy.getBody());
		for(Pickup pickup:pickups)
			bodies.add(pickup.getBody());
		for(Pickup powerup: powerups)
			bodies.add(powerup.getBody());
		for(TreasureChest treasureChest:treasureChests)
			bodies.add(treasureChest.getBody());
		GameModel.getInstance().addBodyToEnable(bodies, enabled);
	}
	
	public void dispose() {
		tileMap.dispose();
		connections = null;
		for(int i = 0; i < gates.size; ++i)
			GameModel.getInstance().addBodyToDispose(gates.get(i).getBody());
		for(int i = 0; i < holes.size; ++i)
			GameModel.getInstance().addBodyToDispose(holes.get(i).getBody());
		for(int i = 0; i < solids.size; ++i)
			GameModel.getInstance().addBodyToDispose(solids.get(i).getBody());
		for(int i = 0; i < enemies.size; ++i)
			GameModel.getInstance().addBodyToDispose(enemies.get(i).getBody());
		for(int i = 0; i < pickups.size; ++i)
			GameModel.getInstance().addBodyToDispose(pickups.get(i).getBody());
		for(int i = 0; i < powerups.size; ++i)
			GameModel.getInstance().addBodyToDispose(powerups.get(i).getBody());
		for(int i = 0; i < treasureChests.size;++i)
			GameModel.getInstance().addBodyToDispose(treasureChests.get(i).getBody());
		gates.clear();
		holes.clear();
		solids.clear();
		enemies.clear();
		pickups.clear();
		powerups.clear();
		treasureChests.clear();
	}
	
	public Gate getGate(int direction) {
		for(Gate gate:gates)
			if(gate.getDirection() == direction)
				return gate;
		return null;
	}
	
	public void generateRandomPickup(Vector2 position) {
		Random r = new Random();
		//check per vedere se il pickup rientra in una cella non nulla e navigabile
		if(navigationLayer.getCell((int) position.x, (int) position.y) != null && navigationLayer.getCell((int) position.x, (int) position.y).isWalkable()) {
			//50% di possibilità che venga droppato qualcosa
			if(r.nextInt(10) < 5) {
				int index = r.nextInt(pickupTypes.size);
				try {
					pickups.add(pickupTypes.get(index).getDeclaredConstructor(Vector2.class, Room.class).newInstance(position, this));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private void generateRandomPowerup() {
		Random r = new Random();
		
		int index = r.nextInt(powerupTypes.size);
		try {
			powerups.add(powerupTypes.get(index).getDeclaredConstructor(Vector2.class, Room.class).newInstance(powerupSpawnPosition, this));
			ParticleHandler.getInstance().addParticle(powerupSpawnPosition, ParticleEffectConstants.POWERUP_SPAWN, 0.6f * 2, 0.6f * 2);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setPowerupSpawnPosition(Vector2 powerupSpawnPosition) {
		generatePowerup = true;
		this.powerupSpawnPosition = new Vector2(powerupSpawnPosition);
	}
	
	public void removePickup(Pickup pickupToRemove) {
		
		for(int i = 0; i < pickups.size; ++i) {
			if(pickups.get(i) == pickupToRemove) {
				pickups.removeIndex(i);
				GameModel.getInstance().addBodyToDispose(pickupToRemove.getBody());
				return;
			}
		}
		
		for(int i = 0; i < powerups.size; ++i) {
			if(powerups.get(i) == pickupToRemove) {
				powerups.removeIndex(i);
				GameModel.getInstance().addBodyToDispose(pickupToRemove.getBody());
				return;
			}
		}
		
	}
	
	public Array<Pickup> getPickups() {
		return pickups;
	}
	
	public Array<Pickup> getPowerups() {
		return powerups;
	}
	
	public void setElapsedTeleportTime(float elapsedTeleportTime) {
		this.elapsedTeleportTime = elapsedTeleportTime;
	}
	
	public float getElapsedTeleportTime() {
		return elapsedTeleportTime;
	}
	
	public float getTeleportTime() {
		return teleportTime;
	}
}
