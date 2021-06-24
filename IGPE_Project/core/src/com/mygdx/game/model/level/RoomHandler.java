package com.mygdx.game.model.level;

import com.badlogic.gdx.utils.Array;
import com.mygdx.game.model.GameModel;

public class RoomHandler {

	private static RoomHandler instance = null;
	
	private Room currentRoom;
	private Array<Room> rooms;
	
	private RoomHandler() {
		rooms = new Array<Room>();
	}
	
	public static RoomHandler getInstance() {
		if(instance == null)
			instance = new RoomHandler();
		return instance;
	}
	
	public void createRooms() {
		for(int i = 0; i < rooms.size;++i) {
			rooms.get(i).dispose();
		}
		rooms.clear();
		rooms = RandomRoomGenerator.getInstance().createRooms();
		setCurrentRoom(rooms.first());
	}
	
	public Room getCurrentRoom() {
		return currentRoom;
	}
	
	public Room setCurrentRoom(Room currentRoom) {
		if(this.currentRoom != null) 
			this.currentRoom.enableBodies(false);
		this.currentRoom = currentRoom;
		this.currentRoom.enableBodies(true);
		System.out.println(currentRoom.getRoomIndex());
		return this.currentRoom;
	}

	public Room switchRoom(int direction) {
		Connection [] connections = currentRoom.getConnections();
		return setCurrentRoom(connections[direction].getOtherRoom(currentRoom));
	}
	
}
