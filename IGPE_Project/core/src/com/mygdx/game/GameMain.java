package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.mygdx.game.constants.Settings;
import com.mygdx.game.controller.GameController;
import com.mygdx.game.model.GameModel;
import com.mygdx.game.view.OptionsScreen;
import com.mygdx.game.view.TitleScreen;

public class GameMain extends Game{
	
	private GameController controller;
	private TitleScreen titleScreen;
	private	OptionsScreen optionsScreen;
	private int state, previousState;
	
	@Override
	public void create() {
		state = Settings.TITLE_SCREEN;
		GameModel.getInstance().init();
		controller = new GameController(this);
		titleScreen = new TitleScreen(this);
		optionsScreen = new OptionsScreen(this);
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
		}
	}

	@Override
	public void dispose() {
		controller.dispose();
		titleScreen.dispose();
		optionsScreen.dispose();
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
}