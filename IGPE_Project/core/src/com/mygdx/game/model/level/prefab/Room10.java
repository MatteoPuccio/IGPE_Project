package com.mygdx.game.model.level.prefab;

import com.mygdx.game.model.collisions.Gate;
import com.mygdx.game.model.level.Connection;
import com.mygdx.game.model.level.Room;

public class Room10 extends Room {
	public Room10(Connection connection) {
		super(connection, "rooms/r10.tmx", Gate.UP);
		connections[Gate.DOWN] = new Connection(this, Gate.DOWN);
		connections[Gate.RIGHT] = new Connection(this, Gate.RIGHT);
	}
}
