package com.mygdx.game.model;

import org.xguzm.pathfinding.gdxbridge.NavigationTiledMapLayer;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.mygdx.game.Settings;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Shape;

public class TiledMapObjectsUtil {
	public static void parse(TiledMap tilemap) {
		MapObjects objects = tilemap.getLayers().get("Collisions").getObjects();
		MapObjects voidObjects = tilemap.getLayers().get("Void").getObjects();
		MapObjects gatesObjects = tilemap.getLayers().get("Gates").getObjects();
				
		parseObjects(tilemap, objects);
		
		parseVoid(tilemap, voidObjects);
		
		parseGates(tilemap, gatesObjects);
		
	}
	
	public static NavigationTiledMapLayer getNavigationTiledMapLayer(TiledMap tilemap) {
		NavigationTiledMapLayer navigationLayer = (NavigationTiledMapLayer) tilemap.getLayers().get("navigation");
		return navigationLayer;
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
	
	private static void parseObjects(TiledMap tilemap, MapObjects objects) {
		
		for(MapObject object : objects)
		{
			Shape shape = null;
			if(object instanceof PolygonMapObject)
				shape = createPolygon((PolygonMapObject) object);
			
			Body body;
			BodyDef bDef = new BodyDef();
			bDef.type = BodyType.StaticBody;
			body = GameModel.getInstance().getWorld().createBody(bDef);
			body.setUserData("wall");
			body.createFixture(shape, 1f);
			shape.dispose();
		}
		
	}
	
	private static void parseVoid(TiledMap tilemap, MapObjects voidObjects) {
		
		for(MapObject object : voidObjects)
		{
			Shape shape = null;
			if(object instanceof PolygonMapObject)
				shape = createPolygon((PolygonMapObject) object);
			
			Body body;
			BodyDef bDef = new BodyDef();
			bDef.type = BodyType.StaticBody;
			body = GameModel.getInstance().getWorld().createBody(bDef);
			body.setUserData("void");
			body.createFixture(shape, 1f);
			shape.dispose();
		}
		
	}
	
	private static void parseGates(TiledMap tilemap, MapObjects gatesObjects) {
		
		for(MapObject object : gatesObjects)
		{
			Shape shape = null;
			if(object instanceof PolygonMapObject)
				shape = createPolygon((PolygonMapObject) object);
			
			Body body;
			BodyDef bDef = new BodyDef();
			bDef.type = BodyType.StaticBody;
			body = GameModel.getInstance().getWorld().createBody(bDef);
			body.setUserData("gate");
			FixtureDef gateFixture = new FixtureDef();
			gateFixture.shape = shape;
			gateFixture.density = 1f;
			gateFixture.isSensor = true;
			body.createFixture(gateFixture);
			shape.dispose();
		}
		
	}
}	
