package com.mygdx.game.model.level.prefab;

import com.mygdx.game.model.collisions.Gate;
import com.mygdx.game.model.level.Connection;
import com.mygdx.game.model.level.Room;

public class Room1F extends Room{
	public Room1F(Connection connection) {
		super(connection, "rooms/rf1.tmx", Gate.DOWN);
	}
}
