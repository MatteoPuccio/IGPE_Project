package com.mygdx.game.model.level;

import java.util.Random;

import org.xguzm.pathfinding.gdxbridge.NavigationTiledMapLayer;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.model.GameModel;
import com.mygdx.game.model.TiledMapObjectsUtil;
import com.mygdx.game.model.ai.NavTmxMapLoader;
import com.mygdx.game.model.collisions.Gate;
import com.mygdx.game.model.collisions.Hole;
import com.mygdx.game.model.collisions.Solid;
import com.mygdx.game.model.entities.Enemy;
import com.mygdx.game.model.pickups.Coin;
import com.mygdx.game.model.pickups.CoinBag;
import com.mygdx.game.model.pickups.HealthPotion;
import com.mygdx.game.model.pickups.Pickup;

public class Room {
	protected TiledMap tileMap;
	protected NavigationTiledMapLayer navigationLayer;
	
	private static int rooms = 0;
	private int roomIndex;
	protected Connection [] connections;
		
	private Array<Enemy> enemies;
	
	protected Array<Gate> gates;
	protected Array<Hole> holes;
	protected Array<Solid> solids;
	
	protected Array<Class<? extends Pickup>> pickupTypes;
	protected Array<Pickup> pickups;
	
	public Room(String tileMapPath) {
		tileMap = new NavTmxMapLoader().load(tileMapPath);
		roomIndex = rooms;
		rooms++;
		connections = new Connection[4];
		
		initPickups();

		parseMap(tileMap);
		
	}
	
	public Room(Connection connection, String tileMapPath, int endingPoint) {
		tileMap = new NavTmxMapLoader().load(tileMapPath);
		roomIndex = rooms;
		rooms++;
		connections = new Connection[4];
		
		initPickups();
		
		connection.generateEndingPoint(this, endingPoint);
		connections[endingPoint] = connection;
		parseMap(tileMap);
	}
	
	private void initPickups() {
		pickups = new Array<Pickup>(false, 10);
		pickupTypes = new Array<Class<? extends Pickup>>();
		
		pickupTypes.add(Coin.class);
		pickupTypes.add(CoinBag.class);
		pickupTypes.add(HealthPotion.class);
	}
	
	public boolean hasFreeConnection() {
		boolean freeConnection = false;
		for(Connection connection:connections) {
			if(connection != null)
				if(connection.getEndingRoom() == null)
					freeConnection = true;
		}
		return freeConnection;
	}
	
	public Connection getFreeConnection() {
		for(Connection connection:connections) {
			if(connection != null)
				if(connection.getEndingRoom() == null)
					return connection;
		}
		return null;
	}
	
	public Room createAdjacentRoom() {
		Connection roomConnection = getFreeConnection();
		Room newRoom = RandomRoomGenerator.createRoom(roomConnection, false);
		return newRoom;
	}
	
	public Array<Room> createAdjacentRooms(boolean deadend) {
		Array<Room> rooms = new Array<Room>();
		Connection roomConnection;
		while(hasFreeConnection()) {
			roomConnection = getFreeConnection();
			Room newRoom = RandomRoomGenerator.createRoom(roomConnection, deadend);
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
	
	public void addEnemy(Enemy enemy) {
		enemies.add(enemy);
	}
	
	protected void parseMap(TiledMap tileMap) {
		gates = TiledMapObjectsUtil.parseGates(tileMap);
		holes = TiledMapObjectsUtil.parseHoles(tileMap);
		solids = TiledMapObjectsUtil.parseSolid(tileMap);
		
		enemies = TiledMapObjectsUtil.parseEnemies(tileMap, this);
		navigationLayer = TiledMapObjectsUtil.getNavigationTiledMapLayer(tileMap);
	}

	public void enableBodies(boolean enabled) {
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
		GameModel.getInstance().addBodyToEnable(bodies, enabled);
	}
	
	public void dispose() {
		tileMap.dispose();
		for(int i = 0; i < gates.size;++i)
			GameModel.getInstance().addBodyToDispose(gates.get(i).getBody());
		for(int i = 0; i < holes.size;++i)
			GameModel.getInstance().addBodyToDispose(holes.get(i).getBody());
		for(int i = 0; i < solids.size;++i)
			GameModel.getInstance().addBodyToDispose(solids.get(i).getBody());
		for(int i = 0; i < enemies.size;++i)
			GameModel.getInstance().addBodyToDispose(enemies.get(i).getBody());
	}
	
	public Gate getGate(int direction) {
		for(Gate gate:gates)
			if(gate.getDirection() == direction)
				return gate;
		return null;
	}
	
	public static void resetIndex() {
		rooms = 0;
	}
	
	public void generateRandomPickup(Vector2 position) {
		Random r = new Random();
		
		if(navigationLayer.getCell((int) position.x, (int) position.y) != null && navigationLayer.getCell((int) position.x, (int) position.y).isWalkable()) {
			if(r.nextInt(10) <= 4) {
				int index = r.nextInt(pickupTypes.size);
				try {
					pickups.add(pickupTypes.get(index).getDeclaredConstructor(Vector2.class, Room.class).newInstance(position, this));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void removePickup(Pickup pickupToRemove) {
		
		for(int i = 0; i < pickups.size; ++i) {
			if(pickups.get(i) == pickupToRemove) {
				pickups.removeIndex(i);
				GameModel.getInstance().addBodyToDispose(pickupToRemove.getBody());
			}
		}
		
	}
	
	public Array<Pickup> getPickups() {
		return pickups;
	}
}
