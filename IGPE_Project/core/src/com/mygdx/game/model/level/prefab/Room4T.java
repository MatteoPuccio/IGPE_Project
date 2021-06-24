package com.mygdx.game.model.level.prefab;

import com.mygdx.game.model.collisions.Gate;
import com.mygdx.game.model.level.Connection;
import com.mygdx.game.model.level.Room;

public class Room4T extends Room{
	
	public Room4T(Connection connection) {
		super(connection,"rooms/rt4.tmx", Gate.UP);
	}
}
