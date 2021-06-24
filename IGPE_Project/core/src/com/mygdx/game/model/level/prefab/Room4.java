package com.mygdx.game.model.level.prefab;

import com.mygdx.game.model.collisions.Gate;
import com.mygdx.game.model.level.Connection;
import com.mygdx.game.model.level.Room;

public class Room4 extends Room{
	public Room4(Connection connection) {
		super(connection,"rooms/r4.tmx", Gate.DOWN);
		connections[Gate.UP] = new Connection(this, Gate.UP);
	}
}
