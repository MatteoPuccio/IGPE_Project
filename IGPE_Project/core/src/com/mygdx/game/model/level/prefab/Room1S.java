package com.mygdx.game.model.level.prefab;

import com.mygdx.game.model.collisions.Gate;
import com.mygdx.game.model.level.Connection;
import com.mygdx.game.model.level.Room;

public class Room1S extends Room {
	
	public Room1S() {
		super("rooms/rs1.tmx");
		connections[Gate.RIGHT] = new Connection(this,Gate.RIGHT);
	}
}
