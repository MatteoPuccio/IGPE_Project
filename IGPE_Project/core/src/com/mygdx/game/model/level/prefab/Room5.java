package com.mygdx.game.model.level.prefab;

import com.mygdx.game.model.collisions.Gate;
import com.mygdx.game.model.level.Connection;
import com.mygdx.game.model.level.Room;

public class Room5 extends Room{
	public Room5(Connection connection) {
		super(connection,"rooms/r5.tmx", Gate.RIGHT);
		connections[Gate.LEFT] = new Connection(this, Gate.LEFT);
	}
}
