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
import com.mygdx.game.model.Animated;
import com.mygdx.game.model.BulletHandler;
import com.mygdx.game.model.GameModel;
import com.mygdx.game.model.TiledMapObjectsUtil;
import com.mygdx.game.model.entities.EnemiesHandler;
import com.mygdx.game.model.entities.Enemy;
import com.mygdx.game.model.weapons.Bullet;
import com.mygdx.game.view.animations.Animation;
import com.mygdx.game.view.animations.WeaponSlashAnimation;
import com.mygdx.game.view.audio.Sounds;
import com.mygdx.game.view.ui.InterfaceBar;
import com.mygdx.game.view.ui.UserInterface;

public class GameView {

	private OrthographicCamera camera;
	private Box2DDebugRenderer debugRenderer;
	private Viewport gamePort;
	
	private SpriteBatch batch,batchUI;
	
	private TiledMap tiledMap;
	private TiledMapRenderer tiledMapRenderer;
	
	private HashMap<String, Animation> animations;
	float angle = 0;
	private Sounds sounds;
	private WeaponSlashAnimation weaponAnimation;
	public GameView() {	
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.position.set(0,0,0);
		camera.update();
		gamePort = new FitViewport(16, 9, camera);
		
		debugRenderer = new Box2DDebugRenderer();
		
		initAnimations();
		batch = new SpriteBatch();
		batchUI = new SpriteBatch();
		tiledMap = new TmxMapLoader().load("rooms/r01_w-e.tmx");
		TiledMapObjectsUtil.parseTiledObjectsLayer(tiledMap);
		tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap, 1 / Settings.PPM);
		weaponAnimation = new WeaponSlashAnimation();
		sounds = new Sounds();
	}
	
	public void render(float deltaTime) {
		updateCamera();
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();
        
        UserInterface.getInstance().update();
        
        
        
		batch.begin();	
		batch.setProjectionMatrix(camera.combined);		
		updateAnimations(deltaTime);
		if(weaponAnimation.isPlaying())
			swingAnimation(deltaTime);
		batch.end();
		
		batchUI.begin();
        drawInterfaceBar(UserInterface.getInstance().manaBar);
        batchUI.end();
        
		debugRenderer.render(GameModel.getInstance().getWorld(), camera.combined);
	}
	
	private void drawInterfaceBar(InterfaceBar bar) {
		Texture background = bar.background;
		
		TextureRegion barFilled = bar.barFilled;
		
		Vector2 position = bar.position;
		Vector2 barPosition = bar.barPosition;
		
		batchUI.draw(background, position.x,position.y,background.getWidth(),background.getHeight());
		batchUI.draw(barFilled,barPosition.x,barPosition.y,barFilled.getRegionWidth(),barFilled.getRegionHeight());	
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
//		sounds.dispose();
		UserInterface.getInstance().dispose();
		batchUI.dispose();
	}
	
	private void initAnimations() {
		animations = new HashMap<String, Animation>();
		
		animations.put("knight idle animation",  new Animation(new TextureRegion(new Texture("knight_idle_spritesheet.png")), 6, 0.5f));
		animations.put("knight run animation", new Animation(new TextureRegion(new Texture("knight_run_spritesheet.png")), 6, 0.5f));
		animations.put("fireball animation", new Animation(new TextureRegion(new Texture("fireball_anim_spritesheet.png")), 4, 0.1f));
		animations.put("slime idle animation", new Animation(new TextureRegion(new Texture("slime_idle_spritesheet.png")),6, 0.5f));
	}
	
	private void updateAnimations(float deltaTime) {
		animate(GameModel.getInstance().getCharacter(), deltaTime);
		
		for(Bullet b : BulletHandler.getInstance().getBullets())
		{
			animate(b, deltaTime);
		}
		
		for(Enemy e : EnemiesHandler.getInstance().getEnemies())
		{
			animate(e, deltaTime);
		}
	}
	
	private void animate(Animated a, float deltaTime) {
		float x = a.getAnimationPosition().x;
		float y = a.getAnimationPosition().y;
		TextureRegion currentFrame = animations.get(a.getCurrentAnimationString()).getFrame();
		float w =  currentFrame.getRegionWidth() / Settings.PPM * a.getAnimationWidth() * 2;
		float h = currentFrame.getRegionHeight() / Settings.PPM * a.getAnimationHeigth() * 2;
		
		int flip = 1;
		if(a.isFlipped())
			flip = -1;
		
		batch.draw(currentFrame, x - (w / 2 * flip), y - h / 2, 0, 0, w, h, flip, 1, 0);
		animations.get(a.getCurrentAnimationString()).update(deltaTime);
	}
	
	public OrthographicCamera getCamera() {
		return camera;
	}
	
	public Sounds getSounds() {
		return sounds;
	}
	
	public void swingAnimation(float deltaTime) {
		weaponAnimation.playSwingAnimation(deltaTime);
		float x = weaponAnimation.getPosition().x;
		float y = weaponAnimation.getPosition().y;
		float w =  weaponAnimation.getTexture().getRegionWidth() / Settings.PPM * GameModel.getInstance().getCharacter().getRadius() * 2;
		float h = weaponAnimation.getTexture().getRegionHeight() / Settings.PPM * GameModel.getInstance().getCharacter().getRadius() * 2;

		batch.draw(weaponAnimation.getTexture(), x, y,0,0, w, h,1,1,weaponAnimation.getAngle() - 90f);
	}
	
	public WeaponSlashAnimation getWeaponAnimation() {
		return weaponAnimation;
	}
	
	public void changeMap(TiledMap map) {
		tiledMap.dispose();
		tiledMap = map;
		TiledMapObjectsUtil.parseTiledObjectsLayer(tiledMap);
		tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap, 1 / Settings.PPM);
	}
}


