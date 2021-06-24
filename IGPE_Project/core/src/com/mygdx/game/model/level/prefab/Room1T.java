package com.mygdx.game.model.level.prefab;

import com.mygdx.game.model.collisions.Gate;
import com.mygdx.game.model.level.Connection;
import com.mygdx.game.model.level.Room;

public class Room1T extends Room {
	
	public Room1T(Connection connection) {
		super(connection,"rooms/rt1.tmx", Gate.LEFT);
	}

}
