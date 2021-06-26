package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.utils.BufferUtils;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.constants.ScreenConstants;
import com.mygdx.game.constants.SoundConstants;
import com.mygdx.game.controller.GameController;
import com.mygdx.game.model.GameModel;
import com.mygdx.game.view.ConfirmQuitScreen;
import com.mygdx.game.view.DeathScreen;
import com.mygdx.game.view.MagicChangeScreen;
import com.mygdx.game.view.OptionsScreen;
import com.mygdx.game.view.PauseScreen;
import com.mygdx.game.view.TitleScreen;
import com.mygdx.game.view.audio.SoundHandler;
import com.mygdx.game.view.audio.Sounds;

public class GameMain extends Game{
	
	private static GameMain instance = null;
	private GameController controller;
	private TitleScreen titleScreen;
	private	OptionsScreen optionsScreen;
	private DeathScreen deathScreen;
	private PauseScreen pauseScreen;
	private ConfirmQuitScreen confirmQuitScreen;
	private MagicChangeScreen magicChangeScreen;
	private Cursor cursor;
	private int state;
	
	public static GameMain getInstance() {
		if(instance == null)
			instance = new GameMain();
		return instance;
	}
	
	@Override
	public void create() {
		state = ScreenConstants.TITLE_SCREEN;
		GameModel.getInstance().reset();
		controller = new GameController();
		titleScreen = new TitleScreen();
		optionsScreen = new OptionsScreen();
		deathScreen = new DeathScreen();
		pauseScreen = new PauseScreen();
		confirmQuitScreen = new ConfirmQuitScreen();
		magicChangeScreen = new MagicChangeScreen(controller.getView());
		
		Pixmap pm = new Pixmap(Gdx.files.internal("menu_cursor.png"));
		cursor = Gdx.graphics.newCursor(pm, 0, 0);
		Gdx.graphics.setCursor(cursor);
		pm.dispose();
		setScreen(titleScreen);
	}

	@Override
	public void render() {
		float deltaTime = Math.min(1 / 30f, Gdx.graphics.getDeltaTime());
		
		switch(state) {
		case ScreenConstants.TITLE_SCREEN:
			titleScreen.render(deltaTime);
			break;
		case ScreenConstants.RUNNING:
			controller.update(deltaTime);
			break;
		case ScreenConstants.OPTIONS:
			optionsScreen.render(deltaTime);
			break;
		case ScreenConstants.PAUSE:
			pauseScreen.render(deltaTime);
			break;
		case ScreenConstants.DEAD:
			deathScreen.render(deltaTime);
			break;
		case ScreenConstants.CONFIRM_QUIT:
			confirmQuitScreen.render(deltaTime);
			break;
		case ScreenConstants.MAGIC_CHANGE:
			magicChangeScreen.render(deltaTime);
			break;
		}
	}

	@Override
	public void dispose() {
		controller.dispose();
		Sounds.getInstance().dispose();
		titleScreen.dispose();
		optionsScreen.dispose();
		deathScreen.dispose();
		pauseScreen.dispose();
		confirmQuitScreen.dispose();
		cursor.dispose();
	}
	
	@Override
	public void resize(int width, int height) {
		this.screen.resize(width, height);
	}
	
	
	public GameController getController() {
		return controller;
	}

	public void start() {
		state = ScreenConstants.RUNNING;
		setScreen(controller.getView());
		Gdx.input.setInputProcessor(controller);
	}

	public void options() {
		Gdx.graphics.setCursor(cursor);
		state = ScreenConstants.OPTIONS;
		setScreen(optionsScreen);
	}
	
	public void backToTitle() {
		state = ScreenConstants.TITLE_SCREEN;
		setScreen(titleScreen);
	}

	public void death() {
		state = ScreenConstants.DEAD;
		Gdx.graphics.setCursor(cursor);
		setScreen(deathScreen);
	}
	
	public void restart() {
		state = ScreenConstants.TITLE_SCREEN;
		controller.reset();
		setScreen(titleScreen);
	}

	public void unpause() {
		state = ScreenConstants.RUNNING;
		Gdx.input.setInputProcessor(controller);
		setScreen(controller.getView());
	}

	public void pauseScreen() {
		state = ScreenConstants.PAUSE;
		Gdx.graphics.setCursor(cursor);
		setScreen(pauseScreen);
	}
	
	public void confirmQuitScreen() {
		state = ScreenConstants.CONFIRM_QUIT;
		setScreen(confirmQuitScreen);
	}
	
	public void changeMagicPrompt() {
		state = ScreenConstants.MAGIC_CHANGE;
		magicChangeScreen.setNewMagicId(GameModel.getInstance().getCharacter().getPickedUpMagic().getRespectivePickupAnimationId());
		magicChangeScreen.setFirstMagicId(GameModel.getInstance().getCharacter().getFirstMagic().getRespectivePickupAnimationId());
		magicChangeScreen.setSecondMagicId(GameModel.getInstance().getCharacter().getSecondMagic().getRespectivePickupAnimationId());
		setScreen(magicChangeScreen);
	}
}