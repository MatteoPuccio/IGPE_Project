package com.mygdx.game.model.level.prefab;

import com.mygdx.game.model.collisions.Gate;
import com.mygdx.game.model.level.Connection;
import com.mygdx.game.model.level.Room;

public class Room6 extends Room {
	public Room6(Connection connection) {
		super(connection,"rooms/r6.tmx", Gate.RIGHT);
		connections[Gate.UP] = new Connection(this, Gate.UP);
	}
}
