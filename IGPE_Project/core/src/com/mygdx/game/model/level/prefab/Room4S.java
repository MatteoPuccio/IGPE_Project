package com.mygdx.game.model.level.prefab;

import com.mygdx.game.model.collisions.Gate;
import com.mygdx.game.model.level.Connection;
import com.mygdx.game.model.level.Room;

public class Room4S extends Room{

	public Room4S() {
		super("rooms/rs4.tmx");
		connections[Gate.DOWN] = new Connection(this,Gate.DOWN);
	}
}
