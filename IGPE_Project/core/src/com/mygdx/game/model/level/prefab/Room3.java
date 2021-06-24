package com.mygdx.game.model.level.prefab;

import com.mygdx.game.model.collisions.Gate;
import com.mygdx.game.model.level.Connection;
import com.mygdx.game.model.level.Room;

public class Room3 extends Room {
	
	public Room3(Connection connection) {
		super(connection,"rooms/r3.tmx", Gate.UP);
		connections[Gate.DOWN] = new Connection(this, Gate.DOWN);
	}
}
