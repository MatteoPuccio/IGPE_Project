package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Settings;
import com.mygdx.game.model.GameModel;

public class GameView 
{
	private OrthographicCamera camera;
	private Box2DDebugRenderer debugRenderer;
	private Viewport gamePort;
	
	private Sprite sprite;
	private SpriteBatch batch;
	
	private TiledMap tiledMap;
	private TiledMapRenderer tiledMapRenderer;
	
	public GameView()
	{
		batch = new SpriteBatch();
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth() / Settings.PPM / 4, Gdx.graphics.getHeight() / Settings.PPM / 4);
		camera.position.set(0,0,0);
		camera.update();
		gamePort = new FitViewport(16, 9, camera);
		
		debugRenderer = new Box2DDebugRenderer();
		
		tiledMap = new TmxMapLoader().load("0x72_16x16DungeonTileset_walls.v1.tmx");
		tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap, 1f / Settings.PPM);
		
		sprite = new Sprite(new Texture("badlogic.jpg"));
		Vector3 pos = new Vector3(GameModel.getInstance().getCharacter().getPosition(), 0);
		pos = camera.project(pos);
		sprite.setPosition(pos.x, pos.y);
	}
	
	public void render()
	{
//		batch.begin();
//		sprite.draw(batch);
//		batch.end();
		updateCamera();
		
		tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();
        
		debugRenderer.render(GameModel.getInstance().getWorld(), camera.combined);
	}
	
	private void updateCamera() {
		Vector2 pos = GameModel.getInstance().getCharacter().getPosition();
		camera.position.set(pos.x, pos.y, 0);
		camera.update();
	}
	
	public void resize(int width, int height) {
		gamePort.update(width, height);
	}
}
