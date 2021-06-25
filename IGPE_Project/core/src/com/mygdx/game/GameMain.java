package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.Pixmap;
import com.mygdx.game.constants.Settings;
import com.mygdx.game.constants.SoundConstants;
import com.mygdx.game.controller.GameController;
import com.mygdx.game.model.GameModel;
import com.mygdx.game.view.DeathScreen;
import com.mygdx.game.view.OptionsScreen;
import com.mygdx.game.view.TitleScreen;
import com.mygdx.game.view.audio.SoundHandler;
import com.mygdx.game.view.audio.Sounds;

public class GameMain extends Game{
	
	private static GameMain instance = null;
	private GameController controller;
	private TitleScreen titleScreen;
	private	OptionsScreen optionsScreen;
	private DeathScreen deathScreen;
	private Cursor cursor;
	private int state, previousState;
	
	public static GameMain getInstance() {
		if(instance == null)
			instance = new GameMain();
		return instance;
	}
	
	@Override
	public void create() {
		state = Settings.TITLE_SCREEN;
		GameModel.getInstance().reset();
		controller = new GameController();
		titleScreen = new TitleScreen();
		optionsScreen = new OptionsScreen();
		deathScreen = new DeathScreen();
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
		case Settings.TITLE_SCREEN:
			titleScreen.render(deltaTime);
			break;
		case Settings.RUNNING:
			controller.update(deltaTime);
			break;
		case Settings.OPTIONS:
			optionsScreen.render(deltaTime);
			break;
		case Settings.DEAD:
			deathScreen.render(Gdx.graphics.getDeltaTime());
			break;
		}
	}

	@Override
	public void dispose() {
		controller.dispose();
		titleScreen.dispose();
		optionsScreen.dispose();
		deathScreen.dispose();
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
		state = Settings.RUNNING;
		SoundHandler.getInstance().addSoundToQueue(SoundConstants.MENU_CONFIRM);
		setScreen(controller.getView());
		Gdx.input.setInputProcessor(controller);
	}

	public int getPreviousState() {
		return previousState;
	}

	public void options() {
		Gdx.graphics.setCursor(cursor);
		previousState = state;
		SoundHandler.getInstance().addSoundToQueue(SoundConstants.MENU_CONFIRM);
		state = Settings.OPTIONS;
		setScreen(optionsScreen);
	}
	
	public void backOptions() {
		state = previousState;
		if(state == Settings.RUNNING) {
			Gdx.input.setInputProcessor(controller);
			setScreen(controller.getView());
		}
		else
			setScreen(titleScreen);
		SoundHandler.getInstance().addSoundToQueue(SoundConstants.MENU_BACK);
	}

	public void death() {
		state = Settings.DEAD;
		Gdx.graphics.setCursor(cursor);
		setScreen(deathScreen);
	}
	
	public void restart() {
		state = Settings.TITLE_SCREEN;
		SoundHandler.getInstance().addSoundToQueue(SoundConstants.MENU_BACK);
		controller.reset();
		setScreen(titleScreen);
		
	}
}