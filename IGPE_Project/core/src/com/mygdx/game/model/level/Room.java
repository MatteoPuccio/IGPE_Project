package com.mygdx.game.model.level;

import java.util.ArrayList;
import java.util.Random;

public class Room {
	static int rooms = 0;
	int roomIndex;
	Connection [] connections;
	Random r;
	private boolean deadend;
	public Room() {
		roomIndex = rooms;
		rooms++;
		connections = new Connection[4];
		Connection c = new Connection(this);
		connections[c.getStartingPoint()] = c;
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

	void generateDoors() {
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
	public ArrayList<Room> createAdjacentRooms(boolean deadend) {
		ArrayList<Room> rooms = new ArrayList<Room>();
		while(hasFreeConnection()) {
			Connection roomConnection = getFreeConnection();
			Room newRoom = new Room(roomConnection,deadend);
			rooms.add(newRoom);
		}
		return rooms;
	}
}
