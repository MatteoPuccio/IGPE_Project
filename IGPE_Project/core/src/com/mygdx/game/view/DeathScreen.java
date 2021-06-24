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
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.GameMain;

public class DeathScreen implements Screen{
	
	private SpriteBatch batch;
    protected Stage stage;
    private Viewport viewport;
    private OrthographicCamera camera;
    private TextureAtlas atlas;
    protected Skin skin;
    private BitmapFont titleFont;
    private LabelStyle titleStyle;
	private Table mainTable;
	
	private Label deathLabel;
	private TextButton backButton, quitButton;
	
	public DeathScreen() {
		atlas = new TextureAtlas("skin/skin.atlas");
	    skin = new Skin(Gdx.files.internal("skin/skin.json"), atlas);
	    skin.getFont("boldFont").getData().setScale(2f,2f);
		
	    titleFont = new BitmapFont(Gdx.files.internal("skin/AncientModernTales.fnt"));
	    titleFont.getData().scale(1.5f);
	    titleStyle = new Label.LabelStyle(titleFont, Color.BLACK);
	    
	    batch = new SpriteBatch();
		camera = new OrthographicCamera();
        viewport = new FitViewport(Gdx.graphics.getWidth(),Gdx.graphics.getHeight(), camera);
        viewport.apply();
        camera.position.set(0, 0, 0);
        camera.update();

        deathLabel = new Label("You Died", titleStyle);
                
        backButton = new TextButton("TITLE SCREEN", skin);
        quitButton = new TextButton("QUIT", skin);
        
        stage = new Stage(viewport, batch);
	}
	
	@Override
	public void show() {
		mainTable = new Table();
        mainTable.setFillParent(true);
        mainTable.center();
        
        backButton.addListener(new ClickListener() {
        	@Override
        	public void clicked(InputEvent event, float x, float y) {
        		GameMain.getInstance().restart();
        	}
        });
        
        quitButton.addListener(new ClickListener() {
        	@Override
        	public void clicked(InputEvent event, float x, float y) {
        		Gdx.app.exit();
        	}
        });
        
        mainTable.add(deathLabel).colspan(2);
        mainTable.row();
        mainTable.add(backButton).growX().pad(20, 300, 20, 300);
        mainTable.row();
        mainTable.add(quitButton).growX().pad(20, 300, 20, 300);
        stage.addActor(mainTable); 
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.259f, 0.157f, 0.208f, 1f);
    	Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
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

		atlas.dispose();
		batch.dispose();
		stage.dispose();
	}

}
