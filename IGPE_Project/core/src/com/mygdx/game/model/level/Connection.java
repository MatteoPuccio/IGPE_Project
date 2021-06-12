package com.mygdx.game.model.level;

import java.util.Random;

public class Connection {
	public static final int LEFT = 0,UP = 1,RIGHT = 2,DOWN = 3,UNFULFILLED = 4;
	private int startingPoint;
	private int endingPoint;
	private Room startingRoom;
	private Room endingRoom;
	private Random random;
	
	public Connection(int startingPoint,Room startingRoom) {
		random = new Random();
		this.startingPoint = startingPoint;
		this.startingRoom = startingRoom;
		endingPoint = UNFULFILLED;
	}
	
	public Connection(Room startingRoom) {
		random = new Random();
		this.startingRoom = startingRoom;
		endingPoint = UNFULFILLED;
		this.startingPoint = random.nextInt(4);
	}
	
	public void generateEndingPoint(Room endingRoom) {
		this.endingRoom = endingRoom;
		endingPoint = (startingPoint+2)%4;
	}
	
	public int getStartingPoint() {
		return startingPoint;
	}
	
	public int getEndingPoint() {
		return endingPoint;
	}
	public Room getStartingRoom() {
		return startingRoom;
	}
	public Room getEndingRoom() {
		return endingRoom;
	}
	
}
