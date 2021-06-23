package com.mygdx.game.model.level;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.model.TiledMapObjectsUtil;
import com.mygdx.game.model.ai.NavTmxMapLoader;
import com.mygdx.game.model.entities.Goblin;

public class Room1 extends Room {

	public Room1() {
		tileMap = new NavTmxMapLoader().load("rooms/r01_w-e.tmx");
		TiledMapObjectsUtil.parse(tileMap);
		navigationLayer = TiledMapObjectsUtil.getNavigationTiledMapLayer(tileMap);
	}
	
	@Override
	public void init() {
		Goblin goblin1 = new Goblin(new Vector2(6.5f,6.5f));
//		Goblin goblin2 = new Goblin(new Vector2(8.5f,6.5f));
//		
//		Slime slime = new Slime(new Vector2(6.5f, 6.5f));
//		
//		addEnemy(slime);
		addEnemy(goblin1);
	}
}
