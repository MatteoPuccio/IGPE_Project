package com.mygdx.game.model.level.prefab;

import com.mygdx.game.model.collisions.Gate;
import com.mygdx.game.model.level.Connection;
import com.mygdx.game.model.level.Room;

public class Room2T extends Room {
	
	public Room2T(Connection connection) {
		super(connection,"rooms/rt2.tmx", Gate.DOWN);
	}
}
