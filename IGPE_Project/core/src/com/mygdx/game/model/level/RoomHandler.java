package com.mygdx.game.model.level;

import com.badlogic.gdx.utils.Array;
import com.mygdx.game.model.ParticleHandler;
import com.mygdx.game.model.weapons.BulletHandler;
import com.mygdx.game.view.audio.SoundHandler;

public class RoomHandler {

	private static RoomHandler instance = null;
	
	private Room currentRoom;
	private Array<Room> rooms;
	private boolean changeMap;
	private float elapsedTeleportTime;
	private static final float teleportTime = 2.5f;
	
	private RoomHandler() {
		rooms = new Array<Room>();
		elapsedTeleportTime = teleportTime;
		changeMap = false;
	}
	
	public static RoomHandler getInstance() {
		if(instance == null)
			instance = new RoomHandler();
		return instance;
	}
	
	public void createRooms() {
		reset();
		for(int i = 0; i < rooms.size;++i) {
			rooms.get(i).dispose();
		}
		rooms.clear();
		rooms = RandomRoomGenerator.getInstance().createRooms();
		elapsedTeleportTime = teleportTime;
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
		reset();
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
		
	private void reset() {
		BulletHandler.getInstance().removeAllBullets();
		ParticleHandler.getInstance().clear();
		SoundHandler.getInstance().clear();
	}
	
	public static float getTeleportTime() {
		return teleportTime;
	}
	
}
