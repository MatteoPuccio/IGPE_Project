package com.mygdx.game.model.level;

import com.mygdx.game.model.TiledMapObjectsUtil;
import com.mygdx.game.model.ai.NavTmxMapLoader;

public class Room3 extends Room {

	public Room3() {
		
		tileMap = new NavTmxMapLoader().load("rooms/r3.tmx");
		TiledMapObjectsUtil.parse(tileMap);
		navigationLayer = TiledMapObjectsUtil.getNavigationTiledMapLayer(tileMap);
		
		enemies = TiledMapObjectsUtil.parseEnemies(tileMap, this);		
	}
	
}
