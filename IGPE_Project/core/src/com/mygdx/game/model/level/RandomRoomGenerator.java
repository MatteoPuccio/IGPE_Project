package com.mygdx.game.model.level;

import java.util.Random;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.model.GameModel;

public class RandomRoomGenerator {
	
	private static RandomRoomGenerator instance = null;
	
	private Array<Room> rooms;

	private Array<String> intermediateRoomsPaths;
	private Array<String> starterRoomsPaths;
	private Array<String> terminalRoomsPaths;
	private Array<String> finalRoomsPaths;
	
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
		
		intermediateRoomsPaths = new Array<String>();
		
		for(int i = 0; i < 12; ++i)
			intermediateRoomsPaths.add("rooms/r" + (i+1) + ".tmx");
	}
	
	private void initStarterTypes() {
		
		starterRoomsPaths = new Array<String>();
		
		for(int i = 0; i < 4; ++i)
			starterRoomsPaths.add("rooms/rs" + (i+1) + ".tmx");
	}

	private void initTerminalTypes() {
		
		terminalRoomsPaths = new Array<String>();
		
		for(int i = 0; i < 4; ++i)
			terminalRoomsPaths.add("rooms/rt" + (i+1) + ".tmx");
	}
	
	private void initFinalTypes() {
		
		finalRoomsPaths = new Array<String>();
		
		for(int i = 0; i < 1; ++i)
			finalRoomsPaths.add("rooms/rf" + (i+1) + ".tmx");
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
		int index = r.nextInt(starterRoomsPaths.size);
		return new Room(starterRoomsPaths.get(index));
	}

	public Room createRoom(Connection connection, boolean deadend) {
		int index;
		
		if(deadend) {
			index = r.nextInt(terminalRoomsPaths.size);
			return new Room(terminalRoomsPaths.get(index), connection);
		}
		
		index = r.nextInt(intermediateRoomsPaths.size);
		return new Room(intermediateRoomsPaths.get(index), connection);
	}
	
	public Room createFinalRoom(Room room) {
		Connection connection = room.getFreeConnection();
		int index = r.nextInt(finalRoomsPaths.size);
		return new Room(finalRoomsPaths.get(index), connection);
	}
	
	public int roomsNumber(int floorDepth) {
		return Integer.min(r.nextInt(3*floorDepth) +  (2*floorDepth + 1), 24);
	}
	
}
