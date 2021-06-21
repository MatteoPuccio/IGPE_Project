package com.mygdx.game.model.level;

import java.util.Random;

import com.badlogic.gdx.utils.Array;

public class RandomRoomGenerator {
	
	private static RandomRoomGenerator instance = null;
	
	Array<Room> rooms;
	Room startingRoom;
	Random r;
	
	private RandomRoomGenerator() {
		r = new Random();
		rooms = new Array<Room>();
	}
	
	public static RandomRoomGenerator getInstance() {
		if(instance == null)
			instance = new RandomRoomGenerator();
		return instance;
	}
	
	public void createRooms() {
		startingRoom = new Room();
		rooms.add(startingRoom);
		Room adjacentRoom;
		for(int i = 0; rooms.size < roomsNumber(1) && i < rooms.size;++i) {	//genera roomsNumber stanze
			adjacentRoom = rooms.get(i);
			if(adjacentRoom.hasFreeConnection()) {
				Array<Room> generatedRooms = adjacentRoom.createAdjacentRooms(false);
				rooms.addAll(generatedRooms);
			}
		}
		Array<Room> deadends = new Array<Room>();
		for(Room r:rooms) {		//chiude le connection ancora aperte (deadends)
			if(r.hasFreeConnection()) {
				deadends.addAll(r.createAdjacentRooms(true));
			}
		}
		rooms.addAll(deadends); //aggiungi le stanze chiuse
	}

	public void printRooms() {
		for(Room r:rooms) {
			System.out.println("Room " + r.getRoomIndex() + ": ");
			for(Connection c:r.getConnections())
				if(c != null)
					System.out.println("Connection from " + c.getStartingRoom().getRoomIndex() + " to " + c.getEndingRoom().getRoomIndex() + " from " 
							+ c.getStartingPoint() + " to " +c.getEndingPoint());
			System.out.println("");
		}
	}
	
	public int roomsNumber(int floorDepth) {
		return Integer.min(15, (3*floorDepth)+4);
	}
	
}
