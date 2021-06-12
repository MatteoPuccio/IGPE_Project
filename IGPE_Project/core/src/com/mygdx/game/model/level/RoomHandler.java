package com.mygdx.game.model.level;

import java.util.ArrayList;
import java.util.Random;

public class RoomHandler {
	ArrayList<Room> rooms;
	Room startingRoom;
	Random r;
	
	RoomHandler() {
		r = new Random();
		rooms = new ArrayList<Room>();
		createRooms();
	}
	
	private void createRooms() {
		startingRoom = new Room();
		rooms.add(startingRoom);
		Room adjacentRoom;
		for(int i = 0; rooms.size() < roomsNumber(1) && i < rooms.size();++i) {	//genera roomsNumber stanze
			adjacentRoom = rooms.get(i);
			if(adjacentRoom.hasFreeConnection()) {
				ArrayList<Room> generatedRooms = adjacentRoom.createAdjacentRooms(false);
				rooms.addAll(generatedRooms);
			}
		}
		ArrayList<Room> deadends = new ArrayList<Room>();
		for(Room r:rooms) {		//chiude le connection ancora aperte (deadends)
			if(r.hasFreeConnection()) {
				deadends.addAll(r.createAdjacentRooms(true));
			}
		}
		rooms.addAll(deadends); //aggiungi le stanze chiuse
	}

	public void printRooms() {
		for(Room r:rooms) {
			System.out.println("Room " + r.roomIndex + ": ");
			for(Connection c:r.connections)
				if(c != null)
					System.out.println("Connection from " + c.getStartingRoom().roomIndex + " to " + c.getEndingRoom().roomIndex + " from " 
							+ c.getStartingPoint() + " to " +c.getEndingPoint());
			System.out.println("");
		}
	}
	
	public static int roomsNumber(int floorDepth) {
		return Integer.min(15, (3*floorDepth)+4);
	}
	
}
