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
import com.mygdx.game.model.collisions.Hole;
import com.mygdx.game.model.collisions.Solid;
import com.mygdx.game.model.entities.Enemy;
import com.mygdx.game.model.entities.FlyingCreature;
import com.mygdx.game.model.entities.Goblin;
import com.mygdx.game.model.entities.Slime;
import com.mygdx.game.model.level.Room;

public class TiledMapObjectsUtil {
	public static void parse(TiledMap tilemap) {
		MapObjects objects = tilemap.getLayers().get("Collisions").getObjects();
		MapObjects voidObjects = tilemap.getLayers().get("Void").getObjects();
		MapObjects gatesObjects = tilemap.getLayers().get("Gates").getObjects();
		
		parseSolid(tilemap, objects);
		parseGates(tilemap, gatesObjects);
		parseHoles(tilemap, voidObjects);
		
	}
	
	public static NavigationTiledMapLayer getNavigationTiledMapLayer(TiledMap tilemap) {
		NavigationTiledMapLayer navigationLayer = (NavigationTiledMapLayer) tilemap.getLayers().get("navigation");
		return navigationLayer;
	}
	
	private static void parseGates(TiledMap tilemap, MapObjects objects) {
		for(MapObject object : objects)
		{
			Shape shape = null;
			if(object instanceof PolygonMapObject)
				shape = createPolygon((PolygonMapObject) object);
			
			createBody(shape, true);
		}
	}
		
	private static void parseSolid(TiledMap tilemap, MapObjects objects) {
		
		for(MapObject object : objects)
		{
			Shape shape = null;
			if(object instanceof PolygonMapObject)
				shape = createPolygon((PolygonMapObject) object);
			
			new Solid(createBody(shape, false));
		}
	}
	
	public static Array<Enemy> parseEnemies(TiledMap tiledMap, Room home){
		Array<Enemy> enemies = new Array<Enemy>();
		
		MapLayer enemiesLayer = tiledMap.getLayers().get("Enemies");
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
				
			}
			
		}
		
		return enemies;
	}
	
	private static void parseHoles(TiledMap tilemap, MapObjects objects) {
		
		for(MapObject object : objects)
		{
			Shape shape = null;
			if(object instanceof PolygonMapObject)
				shape = createPolygon((PolygonMapObject) object);
			
			new Hole(createBody(shape, false));
		}
		
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
		if (isSensor)
			f.isSensor = true; 
		
		body.createFixture(f);
		
		shape.dispose();
		return body;
	}
}	
