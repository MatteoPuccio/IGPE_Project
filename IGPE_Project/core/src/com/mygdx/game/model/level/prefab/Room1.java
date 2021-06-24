package com.mygdx.game.model.level.prefab;

import com.mygdx.game.model.collisions.Gate;
import com.mygdx.game.model.level.Connection;
import com.mygdx.game.model.level.Room;

public class Room1 extends Room {
	
	public Room1(Connection connection) {
		super(connection,"rooms/r1.tmx", Gate.LEFT);
		connections[Gate.RIGHT] = new Connection(this, Gate.RIGHT);
	}
	
}
