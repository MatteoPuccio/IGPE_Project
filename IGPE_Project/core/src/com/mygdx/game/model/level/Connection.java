package com.mygdx.game.model.level;


/*una connection è la connessione logica tra due stanze
*quando il character collide con un gate della room allora
 passa da una stanza della rispettiva connessione all'altra*/

public class Connection {
	private Room startingRoom;
	private Room endingRoom;
	
	//uscite della tilemap (nord, sud, est e ovest)
	private int startingPoint;
	private int endingPoint;
	
	public Connection(Room startingRoom, int startingPoint) {
		//connessione da startingRoom aperta (senza stanza finale)
		this.startingRoom = startingRoom;
		this.startingPoint = startingPoint;
		endingRoom = null;
	}
	
	public void generateEndingPoint(Room endingRoom, int endingPoint) {
		//chiudi la connessione con la endingRoom
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
	
	//restituisce l'altra stanza della connessione
	public Room getOtherRoom(Room room) {
		if(room == endingRoom)	
			return startingRoom;
		return endingRoom;
	}

	//restituisce l'altra direzione cardinale della connessione
	public int getOtherPoint(int direction) {
		if(direction == endingPoint)
			return startingPoint;
		return endingPoint;
	}
}
