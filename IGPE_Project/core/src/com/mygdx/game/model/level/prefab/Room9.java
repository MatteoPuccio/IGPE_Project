package com.mygdx.game.model.level.prefab;

import com.mygdx.game.model.collisions.Gate;
import com.mygdx.game.model.level.Connection;
import com.mygdx.game.model.level.Room;

public class Room9 extends Room {
	public Room9(Connection connection) {
		super(connection, "rooms/r9.tmx", Gate.DOWN);
		connections[Gate.UP] = new Connection(this, Gate.UP);
		connections[Gate.LEFT] = new Connection(this, Gate.LEFT);
	}
}
