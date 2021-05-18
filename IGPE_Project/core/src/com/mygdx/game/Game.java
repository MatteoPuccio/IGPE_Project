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
		controller = new GameController();
		
	}

	@Override
	public void render() {
		GameModel.getInstance().getWorld().step(Gdx.graphics.getDeltaTime(), 6, 2);
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);	
		
		controller.getView().render();
	}

	@Override
	public void dispose() {
		GameModel.getInstance().getWorld().dispose();
	}
	
}
