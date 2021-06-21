package com.mygdx.game.model.level;

public class RoomHandler {

	private static RoomHandler instance = null;
	
	private Room currentRoom;
	
	private RoomHandler() {}
	
	public static RoomHandler getInstance() {
		if(instance == null)
			instance = new RoomHandler();
		return instance;
	}
	
	public Room getCurrentRoom() {
		return currentRoom;
	}
	
	public void setCurrentRoom(Room currentRoom) {
		this.currentRoom = currentRoom;
	}
	
}
