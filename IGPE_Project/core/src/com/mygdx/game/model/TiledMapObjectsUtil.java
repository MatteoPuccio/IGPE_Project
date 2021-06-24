package com.mygdx.game.model;

import org.xguzm.pathfinding.gdxbridge.NavigationTiledMapLayer;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.constants.Settings;
import com.mygdx.game.model.collisions.Gate;
import com.mygdx.game.model.collisions.Hole;
import com.mygdx.game.model.collisions.Solid;
import com.mygdx.game.model.entities.Enemy;
import com.mygdx.game.model.entities.FlyingCreature;
import com.mygdx.game.model.entities.Goblin;
import com.mygdx.game.model.entities.Slime;
import com.mygdx.game.model.level.Room;

public class TiledMapObjectsUtil {
	
	public static NavigationTiledMapLayer getNavigationTiledMapLayer(TiledMap tilemap) {
		NavigationTiledMapLayer navigationLayer = (NavigationTiledMapLayer) tilemap.getLayers().get("navigation");
		return navigationLayer;
	}
	
	public static Array<Gate> parseGates(TiledMap tilemap) {
		MapObjects gatesObjects = tilemap.getLayers().get("Gates").getObjects();
		Array<Gate> gates = new Array<Gate>();
		MapLayer spawnLayers = tilemap.getLayers().get("SpawnPoints");
		spawnLayers.setVisible(false);
		MapObjects spawnPoints = spawnLayers.getObjects();
		for(MapObject object : gatesObjects)
		{
			Shape shape = null;
			if(object instanceof PolygonMapObject)
				shape = createPolygon((PolygonMapObject) object);
			
			String objectName = object.getName();
			int direction = 0;
			switch (objectName) {
			case "e":
				direction = Gate.RIGHT;
				break;
			case "w":
				direction = Gate.LEFT;
				break;
			case "s":
				direction = Gate.DOWN;
				break;
			case "n":
				direction = Gate.UP;
				break;
			case "floor":
				direction = Gate.END;
				break;
			}
			Vector2 spawnPosition = new Vector2();
			for(MapObject spawn:spawnPoints) {
				if(spawn instanceof TiledMapTileMapObject) {
					TiledMapTileMapObject spawnTile = (TiledMapTileMapObject) spawn;
					TiledMapTile tile = spawnTile.getTile();
					if(((String) tile.getProperties().get("spawnpoint")).equals(objectName)) {
						spawnPosition.x = spawnTile.getX()/Settings.PPM + 0.5f;
						spawnPosition.y = spawnTile.getY()/Settings.PPM + 0.5f;
					}
				}
			}
			gates.add(new Gate(createBody(shape, true), direction, spawnPosition));
		}
		return gates;
	}
	
	public static Array<Solid> parseSolid(TiledMap tilemap) {
		MapObjects solidObjects = tilemap.getLayers().get("Collisions").getObjects();
		Array<Solid> solids = new Array<Solid>();
		
		for(MapObject object : solidObjects)
		{
			Shape shape = null;
			if(object instanceof PolygonMapObject)
				shape = createPolygon((PolygonMapObject) object);
			
			solids.add(new Solid(createBody(shape, false))); 
		}
		return solids;
	}
	
	public static Array<Enemy> parseEnemies(TiledMap tiledMap, Room home){
		Array<Enemy> enemies = new Array<Enemy>();
		
		MapLayer enemiesLayer = tiledMap.getLayers().get("Enemies");
		if(enemiesLayer == null)
			return enemies;
		enemiesLayer.setVisible(false);
		MapObjects enemiesObjects = enemiesLayer.getObjects();
		
		for(MapObject object : enemiesObjects) {
			if(object instanceof TiledMapTileMapObject) {
				TiledMapTileMapObject tileObject = (TiledMapTileMapObject) object;
				TiledMapTile tile = tileObject.getTile();
				switch ((String) tile.getProperties().get("type")) {
				case "slime":
					enemies.add(new Slime(new Vector2(tileObject.getX() / Settings.PPM + 0.5f, tileObject.getY() / Settings.PPM + 0.5f), home));
					break;
				case "flying creature":
					enemies.add(new FlyingCreature(new Vector2(tileObject.getX() / Settings.PPM + 0.5f, tileObject.getY() / Settings.PPM + 0.5f), home));
					break;
				case "goblin":
					enemies.add(new Goblin(new Vector2(tileObject.getX() / Settings.PPM + 0.5f, tileObject.getY() / Settings.PPM + 0.5f), home));
					break;
				}
				enemies.get(enemies.size-1).getBody().setActive(false);
			}
		}
		
		return enemies;
	}
	
	public static Array<Hole> parseHoles(TiledMap tilemap) {
		MapObjects holeObjects = tilemap.getLayers().get("Void").getObjects();
		Array<Hole> holes = new Array<Hole>();
		
		for(MapObject object : holeObjects)
		{
			Shape shape = null;
			if(object instanceof PolygonMapObject)
				shape = createPolygon((PolygonMapObject) object);
			
			holes.add(new Hole(createBody(shape, false)));
		}
		return holes;
	}
	
	private static ChainShape createPolygon(PolygonMapObject polygon) {
		
		float[] vertices = polygon.getPolygon().getTransformedVertices();
		Vector2[] worldVertices = new Vector2[vertices.length / 2];
		for(int i=0;i<worldVertices.length;++i)
		{
			worldVertices[i] = new Vector2(vertices[i * 2] / Settings.PPM, vertices[i * 2 + 1] / Settings.PPM);
		}
		ChainShape cs = new ChainShape();
		cs.createLoop(worldVertices);
		
		return cs;
		
	}
	
	private static Body createBody(Shape shape, boolean isSensor) {
		Body body;
		BodyDef bDef = new BodyDef();
		bDef.type = BodyType.StaticBody;
		body = GameModel.getInstance().getWorld().createBody(bDef);
		
		FixtureDef f = new FixtureDef();
		f.shape = shape;
		f.density = 1f;
		f.isSensor = isSensor; 
		
		body.createFixture(f);
		
		shape.dispose();
		body.setActive(false);
		return body;
	}
}	
