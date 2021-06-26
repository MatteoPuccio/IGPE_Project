package com.mygdx.game.model.level;

import java.util.Random;

import com.badlogic.gdx.utils.Array;
import com.mygdx.game.model.GameModel;

public class RandomRoomGenerator {
	
	private static RandomRoomGenerator instance = null;
	
	private Array<Room> rooms;

	private Array<Room> intermediateRooms;
	private Array<Room> starterRooms;
	private Array<Room> terminalRooms;
	private Array<Room> finalRooms;
	
	private Room startingRoom;
	private static Random r;
	
	private RandomRoomGenerator() {
		r = new Random();
		rooms = new Array<Room>();

		initRoomTypes();
		initStarterTypes();
		initTerminalTypes();
		initFinalTypes();
		
	}

	private void initRoomTypes() {
		
		intermediateRooms = new Array<Room>();
		
		for(int i = 0; i < 12; ++i)
			intermediateRooms.add(new Room("rooms/r" + (i+1) + ".tmx"));
	}
	
	private void initStarterTypes() {
		
		starterRooms = new Array<Room>();
		
		for(int i = 0; i < 4; ++i)
			starterRooms.add(new Room("rooms/rs" + (i+1) + ".tmx"));
	}

	private void initTerminalTypes() {
		
		terminalRooms = new Array<Room>();
		
		for(int i = 0; i < 4; ++i)
			terminalRooms.add(new Room("rooms/rt" + (i+1) + ".tmx"));
	}
	
	private void initFinalTypes() {
		
		finalRooms = new Array<Room>();
		
		for(int i = 0; i < 1; ++i)
			finalRooms.add(new Room("rooms/rf" + (i+1) + ".tmx"));
	}
	
	public static RandomRoomGenerator getInstance() {
		if(instance == null)
			instance = new RandomRoomGenerator();
		return instance;
	}
	
	public Array<Room> createRooms() {
		rooms.clear();
		startingRoom = createStarterRoom();
		rooms.add(startingRoom);
		
		Room adjacentRoom;
		boolean exitCreated = false;
		
		for(int i = 0; rooms.size < roomsNumber(GameModel.getInstance().getFloor()) && i < rooms.size; ++i) {		//genera minimo roomsNumber() stanze
			adjacentRoom = rooms.get(i);
			if(adjacentRoom.hasFreeConnection()) {
				Array<Room> generatedRooms = adjacentRoom.createAdjacentRooms(false);
				rooms.addAll(generatedRooms);
			}
		}
		
		Array<Room> deadends = new Array<Room>();
		
		for(Room r:rooms) {		//chiude le connection ancora aperte (deadends)
			while(r.hasFreeConnection()) {
				if(!exitCreated) {
					createFinalRoom(r);
					exitCreated = true;
				}
				else
					deadends.addAll(r.createAdjacentRooms(true));
			}
		}
		rooms.addAll(deadends); //aggiungi le stanze chiuse
		return rooms;
	}
	
	private Room createStarterRoom() {
		int index = r.nextInt(starterRooms.size);
		return new Room(starterRooms.get(index));
	}

	public Room createRoom(Connection connection, boolean deadend) {
		int index;
		
		if(deadend) {
			index = r.nextInt(terminalRooms.size);
			return new Room(terminalRooms.get(index), connection);
		}
		
		index = r.nextInt(intermediateRooms.size);
		return new Room(intermediateRooms.get(index), connection);
	}
	
	public Room createFinalRoom(Room room) {
		Connection connection = room.getFreeConnection();
		int index = r.nextInt(finalRooms.size);
		return new Room(finalRooms.get(index), connection);
	}
	
	public int roomsNumber(int floorDepth) {
		return Integer.min(r.nextInt(3*floorDepth) +  (2*floorDepth + 1), 24);
	}
	
}
