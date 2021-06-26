package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.GameMain;
import com.mygdx.game.constants.Settings;
import com.mygdx.game.view.audio.Sounds;

public class PauseScreen implements Screen{
	
	private SpriteBatch batch;
    protected Stage stage;
    private Viewport viewport;
    private OrthographicCamera camera;
    private TextureAtlas atlas;
    protected Skin skin;
    private BitmapFont titleFont;
    private LabelStyle titleStyle;
	private Table mainTable;
	
	private Label optionsLabel,volumeLabel;
	private final Slider volumeSlider;
	private TextButton backButton, menuButton;
	
	public PauseScreen() {
		
		atlas = new TextureAtlas("skin/skin.atlas");
	    skin = new Skin(Gdx.files.internal("skin/skin.json"), atlas);
	    skin.getFont("boldFont").getData().setScale(2f,2f);
		
	    titleFont = new BitmapFont(Gdx.files.internal("skin/AncientModernTales.fnt"));
	    titleFont.getData().scale(0.7f);
	    titleStyle = new Label.LabelStyle(titleFont, Color.BLACK);
	    
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
        viewport = new FitViewport(Gdx.graphics.getWidth(),Gdx.graphics.getHeight(), camera);
        viewport.apply();
        camera.position.set(0, 0, 0);
        camera.update();

        optionsLabel = new Label("Pause", titleStyle);
        volumeLabel = new Label("Volume",skin);
        
        optionsLabel.setColor(new Color(Color.BLACK));
        volumeLabel.setColor(new Color(Color.BLACK));
        
        volumeSlider = new Slider(0f, 1f, 0.01f, false, skin);
        backButton = new TextButton("BACK", skin);
        menuButton = new TextButton("MENU", skin);
        stage = new Stage(viewport, batch); 
        
        backButton.addListener(new ClickListener() {
        	@Override
        	public void clicked(InputEvent event, float x, float y) {
        		GameMain.getInstance().unpause();
        	}
        });
        
        menuButton.addListener(new ClickListener() {
        	@Override
        	public void clicked(InputEvent event, float x, float y) {
        		GameMain.getInstance().confirmQuitScreen();
        	}
        });
        
        volumeSlider.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				Settings.setVolume(volumeSlider.getValue());
			}
        });
        
	}
	
	@Override
	public void show() {
		if(mainTable != null)
			mainTable.clear();
		mainTable = new Table();
        mainTable.setFillParent(true);
        mainTable.center();
        
        volumeSlider.setVisualPercent(Settings.getVolume());
        
        
        mainTable.add(optionsLabel).colspan(2).center();
        mainTable.row();
        mainTable.add(volumeLabel).colspan(1).left().padRight(100);
        mainTable.add(volumeSlider).colspan(1).right().padLeft(100);
        mainTable.row();
        mainTable.add(backButton).colspan(2).center();
        mainTable.row();
        mainTable.add(menuButton).colspan(2).center();
//        mainTable.debugAll();
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
		stage.clear();
	}

	@Override
	public void dispose() {
		atlas.dispose();
		batch.dispose();
		stage.dispose();
	}
	
}