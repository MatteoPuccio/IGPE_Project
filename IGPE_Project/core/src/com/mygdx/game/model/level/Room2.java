package com.mygdx.game.model.level;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.model.GameModel;
import com.mygdx.game.model.TiledMapObjectsUtil;
import com.mygdx.game.model.ai.NavTmxMapLoader;
import com.mygdx.game.model.entities.EnemiesHandler;
import com.mygdx.game.model.entities.FlyingCreature;
import com.mygdx.game.model.entities.Goblin;
import com.mygdx.game.model.entities.Slime;

public class Room2 extends Room {

	public Room2() {
		tileMap = new NavTmxMapLoader().load("rooms/r02_w-e.tmx");
		TiledMapObjectsUtil.parse(tileMap);
		navigationLayer = TiledMapObjectsUtil.getNavigationTiledMapLayer(tileMap);
	}
	
	@Override
	public void init() {
		Slime slime1 = new Slime(new Vector2(6.5f,13.5f));
		Slime slime2 = new Slime(new Vector2(18.5f, 12.5f));
		
		FlyingCreature flyingCreature1 = new FlyingCreature(new Vector2(20, 30));
		FlyingCreature flyingCreature2 = new FlyingCreature(new Vector2(10, 22));
		FlyingCreature flyingCreature3 = new FlyingCreature(new Vector2(15, 10));
		
		Goblin goblin1 = new Goblin(new Vector2(7.5f, 6.5f));
		Goblin goblin2 = new Goblin(new Vector2(23.5f, 11.5f));
		
		EnemiesHandler.getInstance().addEnemy(slime1);
		EnemiesHandler.getInstance().addEnemy(slime2);
		
		EnemiesHandler.getInstance().addEnemy(flyingCreature1);
		EnemiesHandler.getInstance().addEnemy(flyingCreature2);
		EnemiesHandler.getInstance().addEnemy(flyingCreature3);
		
		EnemiesHandler.getInstance().addEnemy(goblin1);
		EnemiesHandler.getInstance().addEnemy(goblin2);
		
		GameModel.getInstance().getCharacter().getBody().setTransform(new Vector2(21.5f, 21.5f), getRoomIndex());

	}
}
