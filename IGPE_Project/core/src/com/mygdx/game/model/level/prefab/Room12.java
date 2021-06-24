package com.mygdx.game.model.level.prefab;

import com.mygdx.game.model.collisions.Gate;
import com.mygdx.game.model.level.Connection;
import com.mygdx.game.model.level.Room;

public class Room12 extends Room{
	public Room12(Connection connection) {
		super(connection, "rooms/r12.tmx", Gate.RIGHT);
		connections[Gate.DOWN] = new Connection(this, Gate.DOWN);
	}
}
