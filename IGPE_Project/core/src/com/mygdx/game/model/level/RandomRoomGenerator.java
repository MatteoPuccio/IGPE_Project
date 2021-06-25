package com.mygdx.game.model.level;

import java.util.Random;

import com.badlogic.gdx.utils.Array;
import com.mygdx.game.model.GameModel;
import com.mygdx.game.model.level.prefab.Room1;
import com.mygdx.game.model.level.prefab.Room10;
import com.mygdx.game.model.level.prefab.Room11;
import com.mygdx.game.model.level.prefab.Room12;
import com.mygdx.game.model.level.prefab.Room1F;
import com.mygdx.game.model.level.prefab.Room1S;
import com.mygdx.game.model.level.prefab.Room1T;
import com.mygdx.game.model.level.prefab.Room2;
import com.mygdx.game.model.level.prefab.Room2S;
import com.mygdx.game.model.level.prefab.Room2T;
import com.mygdx.game.model.level.prefab.Room3;
import com.mygdx.game.model.level.prefab.Room3S;
import com.mygdx.game.model.level.prefab.Room3T;
import com.mygdx.game.model.level.prefab.Room4;
import com.mygdx.game.model.level.prefab.Room5;
import com.mygdx.game.model.level.prefab.Room6;
import com.mygdx.game.model.level.prefab.Room7;
import com.mygdx.game.model.level.prefab.Room8;
import com.mygdx.game.model.level.prefab.Room9;

public class RandomRoomGenerator {
	
	private static RandomRoomGenerator instance = null;
	
	private Array<Room> rooms;
	
	private static Array<Class<? extends Room>> roomTypes;
	private static Array<Class<? extends Room>> starterTypes;
	private static Array<Class<? extends Room>> terminalTypes;
	private static Array<Class<? extends Room>> finalTypes;
	
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
		roomTypes = new Array<Class<? extends Room>>();
		roomTypes.add(Room1.class);
		roomTypes.add(Room2.class);
		roomTypes.add(Room3.class);
		roomTypes.add(Room4.class);
		roomTypes.add(Room5.class);
		roomTypes.add(Room6.class);
		roomTypes.add(Room7.class);
		roomTypes.add(Room8.class);
		roomTypes.add(Room9.class);
		roomTypes.add(Room10.class);
		roomTypes.add(Room11.class);
		roomTypes.add(Room12.class);
	}
	
	private void initStarterTypes() {
		starterTypes = new Array<Class<? extends Room>>();
		starterTypes.add(Room1S.class);
		starterTypes.add(Room2S.class);
		starterTypes.add(Room3S.class);
	}

	private void initTerminalTypes() {
		terminalTypes = new Array<Class<? extends Room>>();
		terminalTypes.add(Room1T.class);
		terminalTypes.add(Room2T.class);
		terminalTypes.add(Room3T.class);
	}
	
	private void initFinalTypes() {
		finalTypes = new Array<Class<? extends Room>>();
		finalTypes.add(Room1F.class);
	}
	
	public static RandomRoomGenerator getInstance() {
		if(instance == null)
			instance = new RandomRoomGenerator();
		return instance;
	}
	
	public Array<Room> createRooms() {
		rooms.clear();
		Room.resetIndex();
		startingRoom = createStarterRoom();
		rooms.add(startingRoom);
		Room adjacentRoom;
		boolean exitCreated = false;
		for(int i = 0; rooms.size < roomsNumber(GameModel.getInstance().getFloor()) && i < rooms.size;++i) {		//genera minimo roomsNumber() stanze
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
	
	private static Room createStarterRoom() {
		int index = r.nextInt(starterTypes.size);
		try {
			return starterTypes.get(index).getDeclaredConstructor().newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Room createRoom(Connection connection, boolean deadend) {
		int index;
		try {
			if(deadend) {
				index = r.nextInt(terminalTypes.size);
				return terminalTypes.get(index).getDeclaredConstructor(Connection.class).newInstance(connection);
			}
			index = r.nextInt(roomTypes.size);
			return roomTypes.get(index).getDeclaredConstructor(Connection.class).newInstance(connection);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static Room createFinalRoom(Room room) {
		Connection roomConnection = room.getFreeConnection();
		int index = r.nextInt(finalTypes.size);
		try {
			return finalTypes.get(index).getDeclaredConstructor(Connection.class).newInstance(roomConnection);
		} catch (Exception e) {
			System.out.println("Errore nel generare la stanza finale " + index);
			return null;
		}
	}
	
	public int roomsNumber(int floorDepth) {
		return r.nextInt(3*floorDepth + 2) +  (2*floorDepth + 1);
	}
	
}
