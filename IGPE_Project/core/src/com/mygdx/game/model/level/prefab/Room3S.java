package com.mygdx.game.model.level.prefab;

import com.mygdx.game.model.collisions.Gate;
import com.mygdx.game.model.level.Connection;
import com.mygdx.game.model.level.Room;

public class Room3S extends Room {
	
	public Room3S() {
		super("rooms/rs3.tmx");
		connections[Gate.LEFT] = new Connection(this, Gate.LEFT);
	}
}
