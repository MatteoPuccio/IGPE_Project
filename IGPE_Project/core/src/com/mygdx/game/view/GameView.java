package com.mygdx.game.view;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Settings;
import com.mygdx.game.model.GameModel;
import com.mygdx.game.model.TiledMapObjectsUtil;

public class GameView {
	private OrthographicCamera camera;
	private Box2DDebugRenderer debugRenderer;
	private Viewport gamePort;
	
	private SpriteBatch batch;
	
	private TiledMap tiledMap;
	private TiledMapRenderer tiledMapRenderer;
	
	private HashMap<String, Animation> animations;
	
	public GameView() {	
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.position.set(0,0,0);
		camera.update();
		gamePort = new FitViewport(16, 9, camera);
		
		debugRenderer = new Box2DDebugRenderer();
		
		initAnimations();
		batch = new SpriteBatch();
		
		tiledMap = new TmxMapLoader().load("0x72_16x16DungeonTileset_walls.v1.tmx");
		TiledMapObjectsUtil.parseTiledObjectsLayer(tiledMap);
		tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap, 1 / Settings.PPM);
	}
	
	public void render(float deltaTime) {
		updateCamera();
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();
        
		batch.begin();
		batch.setProjectionMatrix(camera.combined);
		updateAnimations(deltaTime);
		batch.end();
        
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
	
	public void dispose() {
		debugRenderer.dispose();
		tiledMap.dispose();
		batch.dispose();
	}
	
	private void initAnimations() {
		animations = new HashMap<String, Animation>();
		
		animations.put("knight idle animation",  new Animation(new TextureRegion(new Texture("knight_idle_spritesheet.png")), 6, 0.5f));
		animations.put("knight run animation", new Animation(new TextureRegion(new Texture("knight_run_spritesheet.png")), 6, 0.5f));
	}
	
	private void updateAnimations(float deltaTime) {
		float x = GameModel.getInstance().getCharacter().getPosition().x;
		float y = GameModel.getInstance().getCharacter().getPosition().y;
		TextureRegion currentFrame = animations.get(GameModel.getInstance().getCharacter().getCurrentAnimationString()).getFrame();
		float w =  currentFrame.getRegionWidth() / Settings.PPM;
		float h = currentFrame.getRegionHeight() / Settings.PPM;
		
		int flip = 1;
		if(GameModel.getInstance().getCharacter().isFlipped())
			flip = -1;
		batch.draw(currentFrame, x - (w / 2 * flip), y - h / 2, 0, 0, w, h, flip, 1, 0);
		animations.get(GameModel.getInstance().getCharacter().getCurrentAnimationString()).update(deltaTime);
	}
	
	public OrthographicCamera getCamera() {
		return camera;
	}
}


