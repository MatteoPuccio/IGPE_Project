package com.mygdx.game.model.level;

import com.mygdx.game.model.TiledMapObjectsUtil;
import com.mygdx.game.model.pathfinding.NavTmxMapLoader;

public class Room1 extends Room {

	public Room1() {
		tileMap = new NavTmxMapLoader().load("rooms/r01_w-e.tmx");
		TiledMapObjectsUtil.parse(tileMap);
		navigationLayer = TiledMapObjectsUtil.getNavigationTiledMapLayer(tileMap);
	}
	
}
