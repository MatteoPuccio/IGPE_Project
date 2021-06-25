package com.mygdx.game.model.level;

import com.badlogic.gdx.utils.Array;
import com.mygdx.game.model.weapons.BulletHandler;

public class RoomHandler {

	private static RoomHandler instance = null;
	
	private Room currentRoom;
	private Array<Room> rooms;
	private boolean changeMap;
	private float elapsedTeleportTime;
	
	private RoomHandler() {
		rooms = new Array<Room>();
		elapsedTeleportTime = 3f;
		changeMap = false;
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
		elapsedTeleportTime = 3f;
		setCurrentRoom(rooms.first());
		changeMap = true;
	}
	
	public Room getCurrentRoom() {
		return currentRoom;
	}
	
	public Room setCurrentRoom(Room currentRoom) {
		if(this.currentRoom != null) 
			this.currentRoom.enableBodies(false);
		this.currentRoom = currentRoom;
		this.currentRoom.enableBodies(true);
		changeMap = true;
		return this.currentRoom;
	}

	public Room switchRoom(int direction) {
		BulletHandler.getInstance().removeAllBullets();
		elapsedTeleportTime = 0f;
		Connection [] connections = currentRoom.getConnections();
		return setCurrentRoom(connections[direction].getOtherRoom(currentRoom));
	}

	public float getElapsedTeleportTime() {
		return elapsedTeleportTime;
	}

	public void updateTime(float deltaTime) {
		elapsedTeleportTime += deltaTime;
	}

	public boolean changeMap() {
		if(changeMap) {
			changeMap = false;
			return true;
		}
		return false;
	}
	
}
