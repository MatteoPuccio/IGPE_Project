package com.mygdx.game.model.level.prefab;

import com.mygdx.game.model.collisions.Gate;
import com.mygdx.game.model.level.Connection;
import com.mygdx.game.model.level.Room;

public class Room8 extends Room {
	
	public Room8(Connection connection) {
		super(connection, "rooms/r8.tmx", Gate.LEFT);
		connections[Gate.UP] = new Connection(this, Gate.UP);
		connections[Gate.RIGHT] = new Connection(this, Gate.RIGHT);
	}
}
