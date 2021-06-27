package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.constants.AnimationConstants;
import com.mygdx.game.constants.ParticleEffectConstants;
import com.mygdx.game.constants.Settings;
import com.mygdx.game.controller.ParticleHandler;
import com.mygdx.game.controller.ParticleHandler.Particle;
import com.mygdx.game.model.GameModel;
import com.mygdx.game.model.collisions.TreasureChest;
import com.mygdx.game.model.entities.EnemiesHandler;
import com.mygdx.game.model.entities.Enemy;
import com.mygdx.game.model.level.RoomHandler;
import com.mygdx.game.model.pickups.Pickup;
import com.mygdx.game.model.weapons.Bullet;
import com.mygdx.game.model.weapons.BulletHandler;
import com.mygdx.game.model.weapons.Magic;
import com.mygdx.game.view.animations.Animated;
import com.mygdx.game.view.animations.Animation;
import com.mygdx.game.view.animations.ParticleEffect;
import com.mygdx.game.view.ui.UserInterface;

public class GameView implements Screen{

	private OrthographicCamera camera;
	private Box2DDebugRenderer debugRenderer;
	private Viewport gamePort;
	
	private SpriteBatch batch,batchUI;
	private TiledMapRenderer tiledMapRenderer;

	private ObjectMap<Integer, Animation> animations;
	private ObjectMap<Integer, ParticleEffect> particleEffects;
	private Array<ParticleEffect> activeParticleEffects;
	private Cursor cursor;
	private ShapeRenderer shapeRenderer;
	private float blackScreenAlpha;
	
	private UserInterface ui;
	
	public GameView() {	
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.position.set(0,0,0);
		camera.update();
		gamePort = new FitViewport(16, 10, camera);
		
		debugRenderer = new Box2DDebugRenderer();
		blackScreenAlpha = 1f;
		
		initAnimations();
		batch = new SpriteBatch();
		batchUI = new SpriteBatch();
		tiledMapRenderer = new OrthogonalTiledMapRenderer(RoomHandler.getInstance().getCurrentRoom().getTileMap(), 1 / Settings.PPM);
		
		//cursore fuori dal menu
		Pixmap pm = new Pixmap(Gdx.files.internal("UI/game_cursor.png"));
		cursor = Gdx.graphics.newCursor(pm, pm.getWidth() / 2, pm.getHeight() / 2);
		pm.dispose();
		
		shapeRenderer = new ShapeRenderer();
		
		ui = new UserInterface();
	}
	
	public void render(float deltaTime, boolean updateAnimations) {
		updateCamera();
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();
        
        ui.update();
        
		batch.begin();	
		batch.setProjectionMatrix(camera.combined);
		if(updateAnimations)
			updateAnimations(deltaTime);
		renderAnimations(deltaTime);

		batch.end();
		
		batchUI.begin();
        drawUI();
        batchUI.end();
        
        //implementazione semplice di effetto fade-in (disegna un rettangolo nero sullo schermo)
        if(blackScreenAlpha > 0) {
        	shapeRenderer.setColor(0f,0f,0f,blackScreenAlpha);
			Gdx.gl.glEnable(GL20.GL_BLEND);
			Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
			shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
			shapeRenderer.rect(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			shapeRenderer.end();
			Gdx.gl.glDisable(GL20.GL_BLEND);
        }
  
        //il debugRenderer mostra l'outline dei bodies (attivi e non attivi)
        //debugRenderer.render(GameModel.getInstance().getWorld(), camera.combined);
	}
	
	private void drawUI() {
		ui.getManaBar().draw(batchUI);
		ui.getHealthBar().draw(batchUI);
		
		//disegna le magie equipaggiate nella HUD
		ui.getFirstEquippedMagic().draw(animations.get(GameModel.getInstance().getCharacter().getFirstMagic().getRespectivePickupAnimationId()).getFrame().getTexture(), batchUI);
		Magic magic = GameModel.getInstance().getCharacter().getSecondMagic();
		if(magic == null)
			ui.getSecondEquippedMagic().draw(batchUI);
		else
			ui.getSecondEquippedMagic().draw(animations.get(magic.getRespectivePickupAnimationId()).getFrame().getTexture(), batchUI);
		ui.getCoinsLabel().draw(batchUI, "" + GameModel.getInstance().getCoins());
		String prefix;
		if(GameModel.getInstance().getFloor() != 0)
			prefix = "-";
		else
			prefix = "";
		ui.getFloorLabel().draw(batchUI, prefix + GameModel.getInstance().getFloor());
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
		cursor.dispose();
		batch.dispose();
		shapeRenderer.dispose();
		ui.dispose();
		batchUI.dispose();
		
		for(Integer i : animations.keys())
			animations.get(i).dispose();
		
		for(Integer i : particleEffects.keys())
			particleEffects.get(i).dispose();
	}
	
	//a ogni animazione è associato un id di AnimationConstants
	private void initAnimations() {
		animations = new ObjectMap<Integer, Animation>();

		animations.put(AnimationConstants.KNIGHT_IDLE_ANIMATION,  new Animation("animations/knight_idle_spritesheet.png", 6, 0.5f));
		animations.put(AnimationConstants.KNIGHT_RUN_ANIMATION, new Animation("animations/knight_run_spritesheet.png", 6, 0.5f));
		animations.put(AnimationConstants.KNIGHT_INVINCIBLE_IDLE_ANIMATION, new Animation("animations/knight_invincible_idle_spritesheet.png", 12, 0.25f));
		animations.put(AnimationConstants.KNIGHT_INVINCIBLE_RUN_ANIMATION, new Animation("animations/knight_invincible_run_spritesheet.png", 12, 0.25f));
		
		animations.put(AnimationConstants.FIREBALL_ANIMATION, new Animation("animations/fireball_anim_spritesheet.png", 4, 0.2f));
		animations.put(AnimationConstants.SLIMEBALL_ANIMATION, new Animation("animations/slimeball_anim_spritesheet.png", 4, 0.2f));
		animations.put(AnimationConstants.LIGHTNINGBOLT_ANIMATION, new Animation("animations/lightningbolt_anim_spritesheet.png", 4, 0.1f));
		animations.put(AnimationConstants.ROCK_ANIMATION, new Animation("animations/rock_anim_spritesheet.png", 4, 0.2f));
		animations.put(AnimationConstants.BOMB_ANIMATION, new Animation("animations/bomb_anim_spritesheet.png",4, 0.2f));
		animations.put(AnimationConstants.DROPLET_ANIMATION, new Animation("animations/droplet_anim_spritesheet.png", 4, 0.2f));
		
		animations.put(AnimationConstants.GOBLIN_IDLE_ANIMATION, new Animation("animations/goblin_idle_spritesheet.png", 6, 0.5f));
		animations.put(AnimationConstants.GOBLIN_RUN_ANIMATION, new Animation("animations/goblin_run_spritesheet.png", 6, 0.5f));
		animations.put(AnimationConstants.FLYING_CREATURE_FLYING_ANIMATION, new Animation("animations/fly_anim_spritesheet.png", 4, 0.5f));
		animations.put(AnimationConstants.SLIME_IDLE_ANIMATION, new Animation("animations/slime_idle_spritesheet.png",6, 0.5f));
		
		animations.put(AnimationConstants.HEALTH_POTION_ANIMATION, new Animation("images/health_potion.png",1,1));
		animations.put(AnimationConstants.COIN_ANIMATION, new Animation("images/coin.png",1,1));
		animations.put(AnimationConstants.COIN_BAG_ANIMATION, new Animation("images/coin_bag.png",1,1));
		animations.put(AnimationConstants.MANA_RECHARGE_POWERUP_ANIMATION,new Animation("images/mana_recharge_powerup.png",1,1));
		animations.put(AnimationConstants.SPEED_POWERUP_ANIMATION, new Animation("images/speed_powerup.png",1,1));
		animations.put(AnimationConstants.INVINCIBILITY_POWERUP_ANIMATION, new Animation("images/invincibility_powerup.png",1,1));
		animations.put(AnimationConstants.MAGIC_COOLDOWN_POWERUP_ANIMATION, new Animation("images/magic_cooldown_powerup.png",1,1));
		animations.put(AnimationConstants.FIRE_MAGIC_ANIMATION, new Animation("images/fire_magic.png",1,1));
		animations.put(AnimationConstants.LIGHTNING_MAGIC_ANIMATION, new Animation("images/lightning_magic.png",1,1));
		animations.put(AnimationConstants.ROCK_MAGIC_ANIMATION, new Animation("images/rock_magic.png", 1, 1));
		animations.put(AnimationConstants.EXPLOSION_MAGIC_ANIMATION, new Animation("images/explosion_magic.png",1,1));
		animations.put(AnimationConstants.WATER_MAGIC_ANIMATION, new Animation("images/water_magic.png",1,1));
		
		animations.put(AnimationConstants.CHEST_CLOSED_ANIMATION, new Animation("animations/chest_closed_spritesheet.png",8,1));
		animations.put(AnimationConstants.CHEST_OPEN_ANIMATION, new Animation("images/chest_open.png",1,1));
		
		
		initParticles();
	}
	
	private void initParticles() {
		particleEffects = new ObjectMap<Integer, ParticleEffect>();
		
		particleEffects.put(ParticleEffectConstants.ENEMY_DEATH_EXPLOSION, new ParticleEffect("animations/particles/enemy_afterdead_explosion_anim_spritesheet.png", 4, 0.25f));
		particleEffects.put(ParticleEffectConstants.HIT, new ParticleEffect("animations/particles/hit_effect_anim_spritesheet.png", 3, 0.15f));
		particleEffects.put(ParticleEffectConstants.EXPLOSION, new ParticleEffect("animations/particles/explosion_anim_spritesheet.png", 7, 0.4f));
		
		activeParticleEffects = new Array<ParticleEffect>(false, 40);
	}
	
	private void renderAnimations(float deltaTime) {
		
		for(TreasureChest t : RoomHandler.getInstance().getCurrentRoom().getTreasureChests())
		{
			animate(t, deltaTime);
		}
		
		for(Enemy e : EnemiesHandler.getEnemies())
		{
			animate(e, deltaTime);
		}
		
		for(Pickup p : RoomHandler.getInstance().getCurrentRoom().getPickups())
		{
			animate(p, deltaTime);
		}
		
		for(Pickup p : RoomHandler.getInstance().getCurrentRoom().getPowerups())
		{
			animate(p, deltaTime);
		}
		
		for(Bullet b : BulletHandler.getInstance().getBullets())
		{
			animate(b, deltaTime);
		}
		
		animate(GameModel.getInstance().getCharacter(), deltaTime);
		
		renderParticleEffects(deltaTime);
	}
	
	private void renderParticleEffects(float deltaTime) {
		while(!ParticleHandler.getInstance().getParticles().isEmpty()) {
			Particle temp = ParticleHandler.getInstance().getParticles().pop();
			activeParticleEffects.add(new ParticleEffect(particleEffects.get(temp.getParticleId()), temp.getPosition(), temp.getWidth(), temp.getHeigth()));
		}
		
		if(ParticleHandler.getInstance().isCleared())
			activeParticleEffects.clear();
		
		for(int i = 0; i < activeParticleEffects.size; ++i) {
			if(activeParticleEffects.get(i).isDonePlaying()) {
				activeParticleEffects.removeIndex(i);
				--i;
			}
			else {
				animate(activeParticleEffects.get(i), deltaTime);
			}
		}
	}
	
	private void updateAnimations(float deltaTime) {
		updateParticleEffects(deltaTime);
		
		for(Integer i : animations.keys()) {
			animations.get(i).update(deltaTime);
		}
	}
	
	private void updateParticleEffects(float deltaTime) {
		
		for(ParticleEffect particleEffect : activeParticleEffects)
			particleEffect.update(deltaTime);

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
	}
	
	public OrthographicCamera getCamera() {
		return camera;
	}

	public Viewport getGamePort() {
		return gamePort;
	}
	

	public Texture getAnimationFrame(int animationId) {
		return animations.get(animationId).getFrame().getTexture();
	}
	
	public void changeMap(TiledMap map) {
		tiledMapRenderer = new OrthogonalTiledMapRenderer(map, 1 / Settings.PPM);
	}

	@Override
	public void show() {
		Gdx.graphics.setCursor(cursor);
		blackScreenAlpha = 1f;
	}

	public void setBlackScreen(float elapsedTeleportTime) {
		//opacità del rettangolo disegnato per fare effetto fade-in
		blackScreenAlpha = RoomHandler.getInstance().getCurrentRoom().getTeleportTime() - elapsedTeleportTime;
		if(blackScreenAlpha <= 0f)
			blackScreenAlpha = 0f;
	}

	@Override
	public void render(float delta) {}

	@Override
	public void pause() {}

	@Override
	public void resume() {}

	@Override
	public void hide() {}
}


