package com.mygdx.game.model.level.prefab;

import com.mygdx.game.model.collisions.Gate;
import com.mygdx.game.model.level.Connection;
import com.mygdx.game.model.level.Room;

public class Room7 extends Room {

	public Room7(Connection connection) {
		super(connection, "rooms/r7.tmx", Gate.LEFT);
		connections[Gate.UP] = new Connection(this, Gate.UP);
	}
}
