package com.mygdx.game.model.ai;

import org.xguzm.pathfinding.gdxbridge.NavigationTiledMapLayer;
import org.xguzm.pathfinding.grid.GridCell;

import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileSets;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.XmlReader.Element;

//Questa classe � una copia della classe in gdxbridge https://github.com/xaguzman/pathfinding/tree/master/gdx-bridge
//E' stato necessario copiarla nel progetto in quanto il metodo loadTileLayer era apparentemente deprecato e non aveva il parametro
//MapLayers parentLayers, perci� non veniva chiamato dalla superclasse e non funzionava correttamente

public class NavTmxMapLoader extends TmxMapLoader {

	public String navigationLayerName;
	public String navigationProperty;
	public String navigationClosedValue;
	
	public NavTmxMapLoader(){
		this("navigation", "walkable", "0");
	}

	public NavTmxMapLoader(FileHandleResolver resolver){
		this(resolver, "navigation", "walkable", "0");
	}
	
	public NavTmxMapLoader(String navigationLayerName, String navigationProperty, String navigationClosedValue) {
		this(new InternalFileHandleResolver(), navigationLayerName, navigationProperty, navigationClosedValue);
	}
	
	public NavTmxMapLoader(FileHandleResolver handler, String navigationLayerName, String navigationProperty, String navigationClosedValue){
		super(handler);
		this.navigationLayerName = navigationLayerName;
		this.navigationProperty = navigationProperty;
		this.navigationClosedValue = navigationClosedValue;
	}
		
	@Override
	protected void loadTileLayer(TiledMap map, MapLayers parentLayers, Element element) {
		String layerName = element.getAttribute("name", null);
		if ( navigationLayerName.equals(layerName)){
			loadNavigationLayer(map, element, layerName);
		}
		else{
			super.loadTileLayer(map, parentLayers, element);
		}
	}
	
	private void loadNavigationLayer(TiledMap map, Element element, String layerName){
		int width = element.getIntAttribute("width", 0);
		int height = element.getIntAttribute("height", 0);
		
		int[] ids = getTileIds(element, width, height);
		TiledMapTileSets tilesets = map.getTileSets();
		GridCell[][] nodes = new GridCell[width][height];
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int id = ids[y * width + x];
				TiledMapTile tile = tilesets.getTile(id & ~MASK_CLEAR);
				
				GridCell cell = new GridCell(x, height - 1 - y, false);
				if (tile != null) {
					MapProperties tileProp = tile.getProperties();
					
					String walkableProp = tileProp.get(navigationProperty, navigationClosedValue, String.class);
					cell.setWalkable( !walkableProp.equals(navigationClosedValue) );
				}
				nodes[cell.getX()][cell.getY()] = cell;
			}
		}
		
		NavigationTiledMapLayer layer = new NavigationTiledMapLayer(nodes);
		layer.setName(layerName);
		layer.setVisible(false);

		Element properties = element.getChildByName("properties");
		if (properties != null) {
			loadProperties(layer.getProperties(), properties);
		}
		map.getLayers().add(layer);
	}
}