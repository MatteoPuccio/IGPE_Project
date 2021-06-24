package com.mygdx.game.model.level.prefab;

import com.mygdx.game.model.collisions.Gate;
import com.mygdx.game.model.level.Connection;
import com.mygdx.game.model.level.Room;

public class Room2 extends Room {
	
	public Room2(Connection connection) {
		super(connection,"rooms/r2.tmx", Gate.LEFT);
		connections[Gate.RIGHT] = new Connection(this, Gate.RIGHT);
	}
	
}
