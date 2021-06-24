package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.constants.AnimationConstants;
import com.mygdx.game.constants.ParticleEffectConstants;
import com.mygdx.game.constants.Settings;
import com.mygdx.game.model.Animated;
import com.mygdx.game.model.GameModel;
import com.mygdx.game.model.ParticleHandler;
import com.mygdx.game.model.ParticleHandler.Particle;
import com.mygdx.game.model.entities.EnemiesHandler;
import com.mygdx.game.model.entities.Enemy;
import com.mygdx.game.model.level.RoomHandler;
import com.mygdx.game.model.weapons.Bullet;
import com.mygdx.game.model.weapons.BulletHandler;
import com.mygdx.game.view.animations.Animation;
import com.mygdx.game.view.animations.ParticleEffect;
import com.mygdx.game.view.audio.Sounds;
import com.mygdx.game.view.ui.InterfaceBar;
import com.mygdx.game.view.ui.UserInterface;

public class GameView implements Screen{

	private OrthographicCamera camera;
	private Box2DDebugRenderer debugRenderer;
	private Viewport gamePort;
	
	private SpriteBatch batch,batchUI;
	
	private TiledMapRenderer tiledMapRenderer;
	private Stage stage;

	private ObjectMap<Integer, Animation> animations;
	private ObjectMap<Integer, ParticleEffect> particleEffects;
	private Array<ParticleEffect> activeParticleEffects;
	
	private Sounds sounds;
	
	public GameView() {	
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.position.set(0,0,0);
		camera.update();
		gamePort = new FitViewport(16, 10, camera);
		
		debugRenderer = new Box2DDebugRenderer();
		
		initAnimations();
		batch = new SpriteBatch();
		batchUI = new SpriteBatch();
		tiledMapRenderer = new OrthogonalTiledMapRenderer(RoomHandler.getInstance().getCurrentRoom().getTileMap(), 1 / Settings.PPM);
//		weaponAnimation = new WeaponSlashAnimation();
		sounds = new Sounds();
		
		Pixmap pm = new Pixmap(Gdx.files.internal("cursor.png"));
		Gdx.graphics.setCursor(Gdx.graphics.newCursor(pm, pm.getWidth() / 2, pm.getHeight() / 2));
		pm.dispose();
	}
	
	@Override
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
	
	@Override
	public void resize(int width, int height) {
		gamePort.update(width, height);
	}
	
	public void dispose() {
		debugRenderer.dispose();
//		tiledMap.dispose();
		batch.dispose();
//		sounds.dispose();
		UserInterface.getInstance().dispose();
		batchUI.dispose();
	}
	
	private void initAnimations() {
		animations = new ObjectMap<Integer, Animation>();
		
		animations.put(AnimationConstants.KNIGHT_IDLE_ANIMATION,  new Animation(new TextureRegion(new Texture("animations/knight_idle_spritesheet.png")), 6, 0.5f));
		animations.put(AnimationConstants.KNIGHT_RUN_ANIMATION, new Animation(new TextureRegion(new Texture("animations/knight_run_spritesheet.png")), 6, 0.5f));
		animations.put(AnimationConstants.KNIGHT_INVINCIBLE_IDLE_ANIMATION, new Animation(new TextureRegion(new Texture("animations/knight_invincible_idle_spritesheet.png")), 12, 0.25f));
		animations.put(AnimationConstants.KNIGHT_INVINCIBLE_RUN_ANIMATION, new Animation(new TextureRegion(new Texture("animations/knight_invincible_run_spritesheet.png")), 12, 0.25f));
		
		animations.put(AnimationConstants.FIREBALL_ANIMATION, new Animation(new TextureRegion(new Texture("animations/fireball_anim_spritesheet.png")), 4, 0.2f));
		animations.put(AnimationConstants.SLIMEBALL_ANIMATION, new Animation(new TextureRegion(new Texture("animations/slimeball_anim_spritesheet.png")), 4, 0.2f));
		animations.put(AnimationConstants.LIGHTNINGBOLT_ANIMATION, new Animation(new TextureRegion(new Texture("animations/lightningbolt_anim_spritesheet.png")), 4, 0.1f));
		animations.put(AnimationConstants.ROCK_ANIMATION, new Animation(new TextureRegion(new Texture("animations/rock_anim_spritesheet.png")), 4, 0.2f));
		animations.put(AnimationConstants.BOMB_ANIMATION, new Animation(new TextureRegion(new Texture("animations/bomb_anim_spritesheet.png")),4, 0.2f));
		animations.put(AnimationConstants.DROPLET_ANIMATION, new Animation(new TextureRegion(new Texture("animations/droplet_anim_spritesheet.png")), 4, 0.2f));

		animations.put(AnimationConstants.GOBLIN_IDLE_ANIMATION, new Animation(new TextureRegion(new Texture("animations/goblin_idle_spritesheet.png")), 6, 0.5f));
		animations.put(AnimationConstants.GOBLIN_RUN_ANIMATION, new Animation(new TextureRegion(new Texture("animations/goblin_run_spritesheet.png")), 6, 0.5f));
		animations.put(AnimationConstants.FLYING_CREATURE_FLYING_ANIMATION, new Animation(new TextureRegion(new Texture("animations/fly_anim_spritesheet.png")), 4, 0.5f));
		animations.put(AnimationConstants.SLIME_IDLE_ANIMATION, new Animation(new TextureRegion(new Texture("animations/slime_idle_spritesheet.png")),6, 0.5f));
		
		animations.put(AnimationConstants.HEALTH_POTION_ANIMATION, new Animation(new TextureRegion(new Texture("health_potion.png")),1,1));
		animations.put(AnimationConstants.COIN_ANIMATION, new Animation(new TextureRegion(new Texture("coin.png")),1,1));
		animations.put(AnimationConstants.COIN_BAG_ANIMATION, new Animation(new TextureRegion(new Texture("coin_bag.png")),1,1));
		animations.put(AnimationConstants.FIRE_MAGIC_ANIMATION, new Animation(new TextureRegion(new Texture("fire_magic.png")),1,1));
		animations.put(AnimationConstants.LIGHTNING_MAGIC_ANIMATION, new Animation(new TextureRegion(new Texture("lightning_magic.png")),1,1));
		animations.put(AnimationConstants.ROCK_MAGIC_ANIMATION, new Animation(new TextureRegion(new Texture("rock_magic.png")), 1, 1));
		animations.put(AnimationConstants.EXPLOSION_MAGIC_ANIMATION, new Animation(new TextureRegion(new Texture("explosion_magic.png")),1,1));
		animations.put(AnimationConstants.WATER_MAGIC_ANIMATION, new Animation(new TextureRegion(new Texture("water_magic.png")),1,1));
		
		
		initParticles();
	}
	
	private void initParticles() {
		particleEffects = new ObjectMap<Integer, ParticleEffect>();
		
		particleEffects.put(ParticleEffectConstants.ENEMY_DEATH_EXPLOSION, new ParticleEffect(new TextureRegion(new Texture("animations/particles/enemy_afterdead_explosion_anim_spritesheet.png")), 4, 0.25f));
		particleEffects.put(ParticleEffectConstants.HIT, new ParticleEffect(new TextureRegion(new Texture("animations/particles/hit_effect_anim_spritesheet.png")), 3, 0.15f));
		particleEffects.put(ParticleEffectConstants.EXPLOSION, new ParticleEffect(new TextureRegion(new Texture("animations/particles/explosion_anim_spritesheet.png")), 7, 0.4f));
		
		activeParticleEffects = new Array<ParticleEffect>(false, 40);
	}
	
	private void updateAnimations(float deltaTime) {
		
		for(Bullet b : BulletHandler.getInstance().getBullets())
		{
			animate(b, deltaTime);
		}
		
		for(Enemy e : EnemiesHandler.getEnemies())
		{
			animate(e, deltaTime);
		}
		
		animate(GameModel.getInstance().getCharacter(), deltaTime);
		
		updateParticleEffects(deltaTime);
		
		for(Integer i : animations.keys()) {
			animations.get(i).update(deltaTime);
		}
	}
	
	private void updateParticleEffects(float deltaTime) {
		
		while(!ParticleHandler.getInstance().getParticles().isEmpty()) {
			Particle temp = ParticleHandler.getInstance().getParticles().pop();
			activeParticleEffects.add(new ParticleEffect(particleEffects.get(temp.getParticleId()), temp.getPosition(), temp.getWidth(), temp.getHeigth()));
		}
		
		for(int i = 0; i < activeParticleEffects.size; ++i) {
			if(activeParticleEffects.get(i).isDonePlaying()) {
				ParticleEffect temp = activeParticleEffects.get(i);
				activeParticleEffects.set(i, activeParticleEffects.get(activeParticleEffects.size - 1));
				activeParticleEffects.set(activeParticleEffects.size - 1, temp);
				activeParticleEffects.pop();
				--i;
			}
			else {
				animate(activeParticleEffects.get(i), deltaTime);
			}
		}
	}
	
	private void animate(Animated a, float deltaTime) {
		float x = a.getAnimationPosition().x;
		float y = a.getAnimationPosition().y;
		TextureRegion currentFrame = animations.get(a.getCurrentAnimationId()).getFrame();
		float w = a.getAnimationWidth();
		float h = a.getAnimationHeigth();
		
		int flip = 1;
		if(a.isFlipped())
			flip = -1;
		
		batch.draw(currentFrame, x - w / 2, y - h / 2, w / 2 , h / 2, w, h, flip, 1, a.getRotation());
	}
	
	private void animate(ParticleEffect p, float deltaTime) {
		float x = p.getEffectPosition().x;
		float y = p.getEffectPosition().y;
		TextureRegion currentFrame = p.getFrame();
		float w = p.getWidth();
		float h = p.getHeigth();
		
		batch.draw(currentFrame, x - w / 2, y - h / 2, w, h);
		p.update(deltaTime);
	}
	
	public OrthographicCamera getCamera() {
		return camera;
	}
	
	public Sounds getSounds() {
		return sounds;
	}

	public Viewport getGamePort() {
		return gamePort;
	}
	
//	public void swingAnimation(float deltaTime) {
//		weaponAnimation.playSwingAnimation(deltaTime);
//		float x = weaponAnimation.getPosition().x;
//		float y = weaponAnimation.getPosition().y;
//		float w =  weaponAnimation.getTexture().getRegionWidth() / Settings.PPM * GameModel.getInstance().getCharacter().getRadius() * 2;
//		float h = weaponAnimation.getTexture().getRegionHeight() / Settings.PPM * GameModel.getInstance().getCharacter().getRadius() * 2;
//
//		batch.draw(weaponAnimation.getTexture(), x, y,0,0, w, h, 1, 1, weaponAnimation.getAngle() - 90f);
//	}
//	
//	public WeaponSlashAnimation getWeaponAnimation() {
//		return weaponAnimation;
//	}
	
	public void changeMap(TiledMap map) {
	}

	@Override
	public void show() {
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		
	}
}


