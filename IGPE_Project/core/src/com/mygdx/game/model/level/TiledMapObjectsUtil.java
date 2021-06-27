package com.mygdx.game.model.level;

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
import com.mygdx.game.model.GameModel;
import com.mygdx.game.model.collisions.Gate;
import com.mygdx.game.model.collisions.Hole;
import com.mygdx.game.model.collisions.Solid;
import com.mygdx.game.model.collisions.TreasureChest;
import com.mygdx.game.model.entities.Enemy;
import com.mygdx.game.model.entities.FlyingCreature;
import com.mygdx.game.model.entities.Goblin;
import com.mygdx.game.model.entities.Slime;

/**
 *  Una classe di supporto per trasformare i vari oggetti presenti in una Tilemap in oggetti
 *  utili per il funzionamento del gioco. Ogni oggetto di una tilemap è contenuto in un layer, e la classe
 *  trasforma gli oggetti in base al layer in cui si trovano
 */
public class TiledMapObjectsUtil {
/**
 * I layer presenti nella tilemap sono:
 * navigation: un insieme di tile di due tipi, usato per il pathfinding dei nemici
 * Gates: oggetti che rappresentano le uscite delle varie stanze
 * SpawnPoints: oggetti che indicano la posizione in cui il personaggio si deve trovare quando entra nella stanza da un certo gate
 * Collisions: i confini della mappa di una stanza
 * Enemies: i punti di spawn per i nemici
 * Void: le aree vuote di una mappa dove gli le Entity (tranne le flying creatures) e i Pickup non dovrebbero passare
 * Treasure: oggetti che indicano la posizione delle casse che contengono i powerup del gioco
 */
	
	public static NavigationTiledMapLayer getNavigationTiledMapLayer(TiledMap tilemap) {
		NavigationTiledMapLayer navigationLayer = (NavigationTiledMapLayer) tilemap.getLayers().get("navigation");
		return navigationLayer;
	}

//	Crea gli oggetti Gate da inserire all'interno di ciascuna stanza
	public static Array<Gate> parseGates(TiledMap tilemap) {
		MapObjects gatesObjects = tilemap.getLayers().get("Gates").getObjects();
		Array<Gate> gates = new Array<Gate>();
		MapLayer spawnLayers = tilemap.getLayers().get("SpawnPoints");
		spawnLayers.setVisible(false);
		MapObjects spawnPoints = spawnLayers.getObjects();
		for(MapObject object : gatesObjects) {
			Shape shape = null;
			if(object instanceof PolygonMapObject)
				shape = createPolygon((PolygonMapObject) object);
			
			
			//In base al nome dato ai gates nella tile map si risale alla loro direzione
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
			for(MapObject spawn : spawnPoints) {
				if(spawn instanceof TiledMapTileMapObject) {
					TiledMapTileMapObject spawnTile = (TiledMapTileMapObject) spawn;
					TiledMapTile tile = spawnTile.getTile();
					
					//Nella tilemap gli spawnpoints hanno una custom property "spawnpoint" per capire a quale gate appartengono
					if(((String) tile.getProperties().get("spawnpoint")).equals(objectName)) {
						spawnPosition.x = spawnTile.getX() / Settings.PPM + 0.5f;	//+0.5f piazza gli spawnpoint al centro di una casella anzichè in basso a sinistra
						spawnPosition.y = spawnTile.getY() / Settings.PPM + 0.5f;
					}
				}
			}
			gates.add(new Gate(createBody(shape, true), direction, spawnPosition));
		}
		return gates;
	}

//	Crea gli oggetti Solid da inserire in ciascuna stanza
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

//	Crea gli oggetti Enemy da inserire in ciascuna stanza
	public static Array<Enemy> parseEnemies(TiledMap tilemap, Room home){
		Array<Enemy> enemies = new Array<Enemy>();
		
		MapLayer enemiesLayer = tilemap.getLayers().get("Enemies");
		
		//Non tutte le stanze contengono nemici
		if(enemiesLayer == null)
			return enemies;
		
		enemiesLayer.setVisible(false);
		
		MapObjects enemiesObjects = enemiesLayer.getObjects();
		
		for(MapObject object : enemiesObjects) {
			if(object instanceof TiledMapTileMapObject) {
				TiledMapTileMapObject tileObject = (TiledMapTileMapObject) object;
				TiledMapTile tile = tileObject.getTile();
				
				//Nella tilemap gli enemies hanno una custom property "type" per capire il tipo di nemico
				switch ((String) tile.getProperties().get("type")) {
				case "slime":
					enemies.add(new Slime(new Vector2(tileObject.getX() / Settings.PPM + 0.5f, tileObject.getY() / Settings.PPM + 0.5f), home));	//+0.5f piazza i nemici al centro di una casella anzichè in basso a sinistra
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
	
//	Crea gli oggetti Hole da inserire in ciascuna stanza
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
	
//	Crea gli oggetti TreasureChest da inserire in ciascuna stanza
	public static Array<TreasureChest> parseTreasureChests(TiledMap tilemap){
		
		Array<TreasureChest> treasureChests = new Array<TreasureChest>();
		
		MapLayer treasureLayer = tilemap.getLayers().get("Treasure");
		if(treasureLayer == null)
			return treasureChests;
		treasureLayer.setVisible(false);
		MapObjects treasureObjects = treasureLayer.getObjects();
		
		for(MapObject object : treasureObjects) {
			if(object instanceof TiledMapTileMapObject) {
				TiledMapTileMapObject tileObject = (TiledMapTileMapObject) object;
				treasureChests.add(new TreasureChest(new Vector2(tileObject.getX() / Settings.PPM + 0.5f, tileObject.getY() / Settings.PPM + 0.5f)));	//+0.5f piazza le treasure chests al centro di una casella anzichè in basso a sinistra
				treasureChests.get(treasureChests.size - 1).getBody().setActive(false);
			}
		}
		
		return treasureChests;
		
	}
	
//	Trasforma le coordinate dei punti di un poligono di una tilemap in una ChainShape, mantenendo le proporzioni 
//	della tilemap ma applicandole alle dimensioni del World
	private static ChainShape createPolygon(PolygonMapObject polygon) {
		
		//Nella tilemap una coppia di vertici rappresenta una linea, perciò per ogni due linee continue il secondo vertice della prima appare un'altra volta
		//come primo vertice della seconda.
		//getTransformedVertices() restituisce i vertici eliminando questa proprietà
		float[] vertices = polygon.getPolygon().getTransformedVertices();
		
		Vector2[] worldVertices = new Vector2[vertices.length / 2];
		for(int i=0;i<worldVertices.length;++i) {
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
