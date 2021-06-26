package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.GameMain;
import com.mygdx.game.view.audio.Sounds;

public class ConfirmQuitScreen implements Screen{

	private SpriteBatch batch;
    protected Stage stage;
    private Viewport viewport;
    private OrthographicCamera camera;
    private TextureAtlas atlas;
    protected Skin skin;
	private Table mainTable;
	
	private Label confirmQuit;
	private TextButton yesButton, noButton;
	
	public ConfirmQuitScreen() {
		atlas = new TextureAtlas("skin/skin.atlas");
	    skin = new Skin(Gdx.files.internal("skin/skin.json"), atlas);
	    skin.getFont("boldFont").getData().setScale(2f,2f);
	    
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
        viewport = new FitViewport(Gdx.graphics.getWidth(),Gdx.graphics.getHeight(), camera);
        viewport.apply();
        camera.position.set(0, 0, 0);
        camera.update();

        confirmQuit = new Label("Do you want to go back to the main menu?", skin);
        confirmQuit.setColor(new Color(Color.BLACK));
        
        yesButton = new TextButton("YES", skin);
        noButton = new TextButton("NO", skin);
        yesButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				GameMain.getInstance().restart();
			}
		});
		noButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				GameMain.getInstance().pauseScreen();
			}
		});
		mainTable.add(confirmQuit).colspan(2).top();
		mainTable.row();
		mainTable.add(yesButton);
		mainTable.add(noButton);
		stage.addActor(mainTable);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.259f, 0.157f, 0.208f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		Sounds.getInstance().update();
        stage.act();
        stage.draw();	
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);
		viewport.apply();
		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
	
}
