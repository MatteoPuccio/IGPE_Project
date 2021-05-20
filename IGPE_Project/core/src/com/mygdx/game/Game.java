package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.mygdx.game.controller.GameController;
import com.mygdx.game.model.GameModel;

public class Game extends ApplicationAdapter {
	
	GameController controller;
	
	@Override
	public void create() {
		GameModel.getInstance();
		controller = new GameController();
	}

	@Override
	public void render() {
		GameModel.getInstance().getWorld().step(Gdx.graphics.getDeltaTime(), 6, 2);
		GameModel.getInstance().getCharacter().move(Gdx.graphics.getDeltaTime());
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);	
		
		controller.getView().render(Gdx.graphics.getDeltaTime());
	}

	@Override
	public void dispose() {
		GameModel.getInstance().dispose();
		controller.getView().dispose();
	}
	
	@Override
	public void resize(int width, int height) {
		controller.getView().resize(width, height);
	}
	
}
