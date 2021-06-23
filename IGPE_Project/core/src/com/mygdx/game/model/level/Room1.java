package com.mygdx.game.model.level;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.model.TiledMapObjectsUtil;
import com.mygdx.game.model.ai.NavTmxMapLoader;
import com.mygdx.game.model.entities.FlyingCreature;
import com.mygdx.game.model.entities.Goblin;

public class Room1 extends Room {

	public Room1() {
		tileMap = new NavTmxMapLoader().load("rooms/r01_w-e.tmx");
		TiledMapObjectsUtil.parse(tileMap);
		navigationLayer = TiledMapObjectsUtil.getNavigationTiledMapLayer(tileMap);
	}
	
	@Override
	public void init() {
		
		addEnemy(new Goblin(new Vector2(7.5f,6.5f)));
		addEnemy(new Goblin(new Vector2(7.5f,9.5f)));
		addEnemy(new Goblin(new Vector2(24.5f, 6.5f)));
		addEnemy(new Goblin(new Vector2(24.5f, 9.5f)));
		
		addEnemy(new FlyingCreature(new Vector2(16.5f ,5f)));
		addEnemy(new FlyingCreature(new Vector2(16.5f, 10f)));
	}
}
