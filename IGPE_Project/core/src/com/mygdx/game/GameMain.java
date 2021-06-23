package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.mygdx.game.controller.GameController;
import com.mygdx.game.model.GameModel;
import com.mygdx.game.view.DeathScreen;
import com.mygdx.game.view.OptionsScreen;
import com.mygdx.game.view.TitleScreen;

public class GameMain extends Game{
	
	private static GameMain instance = null;
	private GameController controller;
	private TitleScreen titleScreen;
	private	OptionsScreen optionsScreen;
	private DeathScreen deathScreen;
	private int state, previousState;
	
	public static GameMain getInstance() {
		if(instance == null)
			instance = new GameMain();
		return instance;
	}
	
	@Override
	public void create() {
		state = Settings.TITLE_SCREEN;
		GameModel.getInstance().init();
		controller = new GameController();
		titleScreen = new TitleScreen();
		optionsScreen = new OptionsScreen();
		deathScreen = new DeathScreen();
		setScreen(titleScreen);
	}

	@Override
	public void render() {
		switch(state) {
		case Settings.TITLE_SCREEN:
			titleScreen.render(Gdx.graphics.getDeltaTime());
			break;
		case Settings.RUNNING:
			controller.update(Gdx.graphics.getDeltaTime());
			break;
		case Settings.OPTIONS:
			optionsScreen.render(Gdx.graphics.getDeltaTime());
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
		controller.getView().getSounds().menu_confirm.play(Settings.getVolume());
		setScreen(controller.getView());
		Gdx.input.setInputProcessor(controller);
	}

	public int getPreviousState() {
		return previousState;
	}

	public void options() {
		previousState = state;
		controller.getView().getSounds().menu_confirm.play(Settings.getVolume());
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
		controller.getView().getSounds().menu_back.play(Settings.getVolume());
	}

	public void death() {
		state = Settings.DEAD;
		setScreen(deathScreen);
	}
	
	public void restart() {
		state = Settings.TITLE_SCREEN;
		controller.getView().getSounds().menu_back.play(Settings.getVolume());
		GameModel.getInstance().init();
		setScreen(titleScreen);
		
	}
}