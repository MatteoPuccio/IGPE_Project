package com.mygdx.game.model.level.prefab;

import com.mygdx.game.model.collisions.Gate;
import com.mygdx.game.model.level.Connection;
import com.mygdx.game.model.level.Room;

public class Room11 extends Room {
	public Room11(Connection connection) {
		super(connection, "rooms/r11.tmx", Gate.RIGHT);
		connections[Gate.DOWN] = new Connection(this, Gate.DOWN);
		connections[Gate.LEFT] = new Connection(this, Gate.LEFT);
		connections[Gate.UP] = new Connection(this, Gate.UP);
	}
}
