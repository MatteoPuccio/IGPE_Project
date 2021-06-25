package com.mygdx.game.model.level;

public class Connection {
	private Room startingRoom;
	private Room endingRoom;
	
	private int startingPoint;
	private int endingPoint;
	
	public Connection(Room startingRoom, int startingPoint) {
		this.startingRoom = startingRoom;
		this.startingPoint = startingPoint;
		endingRoom = null;
	}
	
	public void generateEndingPoint(Room endingRoom, int endingPoint) {
		this.endingRoom = endingRoom;
		this.endingPoint = endingPoint;
	}
	
	public Room getStartingRoom() {
		return startingRoom;
	}
	
	public Room getEndingRoom() {
		return endingRoom;
	}
	
	public int getStartingPoint() {
		return startingPoint;
	}
	
	public int getEndingPoint() {
		return endingPoint;
	}
	
	public Room getOtherRoom(Room room) {
		if(room.equals(endingRoom))	
			return startingRoom;
		return endingRoom;
	}

	public int getOtherPoint(int direction) {
		if(direction == endingPoint)
			return startingPoint;
		return endingPoint;
	}
}
