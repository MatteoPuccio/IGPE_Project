package com.mygdx.game.model.level.prefab;

import com.mygdx.game.model.collisions.Gate;
import com.mygdx.game.model.level.Connection;
import com.mygdx.game.model.level.Room;

public class Room2S extends Room {
	
	public Room2S() {
		super("rooms/rs2.tmx");
		connections[Gate.UP] = new Connection(this, Gate.UP);
	}
}
