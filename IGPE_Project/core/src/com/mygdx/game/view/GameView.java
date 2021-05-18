package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.mygdx.game.Settings;
import com.mygdx.game.model.GameModel;

public class GameView 
{
	OrthographicCamera camera;
	Box2DDebugRenderer debugRenderer;
	
	Sprite sprite;
	SpriteBatch batch;
	
	public GameView()
	{
		batch = new SpriteBatch();
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth() / 2 / Settings.PPM, Gdx.graphics.getHeight() / 2 / Settings.PPM);
		camera.position.set(0,0,0);
		camera.update();
		
		debugRenderer = new Box2DDebugRenderer();
		
		sprite = new Sprite(new Texture("badlogic.jpg"));
		Vector3 pos = new Vector3(GameModel.getInstance().getCharacter().getPosition(), 0);
		pos = camera.project(pos);
		sprite.setPosition(pos.x, pos.y);
	}
	
	public void render()
	{
		batch.begin();
		sprite.draw(batch);
		batch.end();
		debugRenderer.render(GameModel.getInstance().getWorld(), camera.combined);
	}
}
