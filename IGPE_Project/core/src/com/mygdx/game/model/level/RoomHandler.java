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
	
	private RoomHandler() {
		rooms = new Array<Room>();
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
		currentRoom.setElapsedTeleportTime(0);
		this.currentRoom.enableBodies(true);
		changeMap = true;
		return this.currentRoom;
	}

	public Room switchRoom(int direction) {
		reset();
		Connection [] connections = currentRoom.getConnections();
		return setCurrentRoom(connections[direction].getOtherRoom(currentRoom));
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
}
