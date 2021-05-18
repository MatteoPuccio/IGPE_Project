package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.mygdx.game.controller.GameController;
import com.mygdx.game.model.GameModel;

public class Game extends ApplicationAdapter {
	
	OrthographicCamera camera;
	Box2DDebugRenderer debugRenderer;
	GameController controller;
	Texture txt;
	SpriteBatch batch;
	
	@Override
	public void create() {
		controller = new GameController();
		txt = new Texture("badlogic.jpg");
		batch = new SpriteBatch();
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
		camera.position.set(0,0,0);
		camera.update();
		debugRenderer = new Box2DDebugRenderer();
	}

	@Override
	public void render() {
		GameModel.getInstance().getWorld().step(Gdx.graphics.getDeltaTime(), 6, 2);
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
		batch.draw(txt, Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() /2);
		batch.end();
		debugRenderer.render(GameModel.getInstance().getWorld(), camera.combined);
	}

	@Override
	public void dispose() {
		GameModel.getInstance().getWorld().dispose();
	}
}
