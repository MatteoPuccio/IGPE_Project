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
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.mygdx.game.Settings;
import com.mygdx.game.model.collisions.Hole;
import com.mygdx.game.model.collisions.Solid;

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
