package com.mygdx.game.model.level;

import com.mygdx.game.model.TiledMapObjectsUtil;
import com.mygdx.game.model.ai.NavTmxMapLoader;

public class Room1 extends Room {

	public Room1() {
		tileMap = new NavTmxMapLoader().load("rooms/r1.tmx");
		TiledMapObjectsUtil.parse(tileMap);
		navigationLayer = TiledMapObjectsUtil.getNavigationTiledMapLayer(tileMap);
		
		enemies = TiledMapObjectsUtil.parseEnemies(tileMap, this);
	}
	
	@Override
	public void init() {

	}
}
