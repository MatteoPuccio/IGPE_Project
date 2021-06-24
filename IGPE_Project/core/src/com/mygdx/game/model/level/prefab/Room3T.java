package com.mygdx.game.model.level.prefab;

import com.mygdx.game.model.collisions.Gate;
import com.mygdx.game.model.level.Connection;
import com.mygdx.game.model.level.Room;

public class Room3T extends Room {
	
	public Room3T(Connection connection) {
		super(connection,"rooms/rt3.tmx", Gate.RIGHT);
	}
}
