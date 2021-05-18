package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.mygdx.game.model.GameModel;

public class GameView 
{
	OrthographicCamera camera;
	Box2DDebugRenderer debugRenderer;
	
	Texture txt;
	SpriteBatch batch;
	
	public GameView()
	{
		txt = new Texture("badlogic.jpg");
		batch = new SpriteBatch();
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
		camera.position.set(0,0,0);
		camera.update();
		
		debugRenderer = new Box2DDebugRenderer();
	}
	
	public void render()
	{
		batch.begin();
		batch.draw(txt, Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() /2);
		batch.end();
		debugRenderer.render(GameModel.getInstance().getWorld(), camera.combined);
	}
}
