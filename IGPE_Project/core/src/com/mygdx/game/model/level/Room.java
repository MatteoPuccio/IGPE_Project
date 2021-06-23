package com.mygdx.game.model.level;

import java.util.Random;

import org.xguzm.pathfinding.gdxbridge.NavigationTiledMapLayer;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.model.entities.Enemy;

public class Room {
	protected TiledMap tileMap;
	protected NavigationTiledMapLayer navigationLayer;
	
	private static int rooms = 0;
	private int roomIndex;
	private Connection [] connections;
	private Random r;
	private boolean deadend;
	
	private Array<Enemy> enemies;
	
	public Room() {
		roomIndex = rooms;
		rooms++;
		connections = new Connection[4];
		Connection c = new Connection(this);
		connections[c.getStartingPoint()] = c;
		
		enemies = new Array<Enemy>();
	}
	
	public Room(Connection connection,boolean deadend) {
		roomIndex = rooms;
		rooms++;
		connections = new Connection[4];
		connection.generateEndingPoint(this);
		
		connections[connection.getEndingPoint()] = connection;
		this.deadend = deadend;
		if(!deadend)
			generateDoors();
	}

	public void generateDoors() {
		r = new Random();
		double chance = r.nextDouble();
		int doorsNumber = 0,counter = 0;
		// se chance >= 25 una porta, se chance >= 65 2 porte, chance >= 90 tre porte
		if(chance >= 0.9d)
			doorsNumber = 3;
		else if(chance >= 0.65d)
			doorsNumber = 2;
		else if(chance >= 0.25d)
			doorsNumber = 1;
		for(int i = 0; i < 4;++i) {
			if(connections[i] == null && counter < doorsNumber) {
				connections[i] = new Connection(i,this);
				counter++;
			}
		}
		
	}
	
	public boolean hasFreeConnection() {
		boolean freeConnection = false;
		for(int i = 0; i < 4;++i) {
			if(connections[i] != null)
				if(connections[i].getEndingPoint() == Connection.UNFULFILLED)
					freeConnection = true;
		}
		return freeConnection;
	}
	
	public Connection getFreeConnection() {
		for(int i = 0; i < 4;++i) {
			if(connections[i] != null)
				if(connections[i].getEndingPoint() == Connection.UNFULFILLED)
					return connections[i];
		}
		return null;
	}
	
	public Room createAdjacentRoom() {
		Connection roomConnection = getFreeConnection();
		Room newRoom = new Room(roomConnection,false);
		return newRoom;
	}
	
	public Array<Room> createAdjacentRooms(boolean deadend) {
		Array<Room> rooms = new Array<Room>();
		while(hasFreeConnection()) {
			Connection roomConnection = getFreeConnection();
			Room newRoom = new Room(roomConnection,deadend);
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
	
	public void init() { 
		
	}
}
