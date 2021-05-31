package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.mygdx.game.controller.GameController;
import com.mygdx.game.model.GameModel;

public class Game extends ApplicationAdapter {
	
	GameController controller;
	
	@Override
	public void create() {
		GameModel.getInstance().initEntities();
		controller = new GameController();
	}

	@Override
	public void render() {
		controller.update(Gdx.graphics.getDeltaTime());
	}

	@Override
	public void dispose() {
		controller.dispose();
	}
	
	@Override
	public void resize(int width, int height) {
		controller.getView().resize(width, height);
	}
	
}
