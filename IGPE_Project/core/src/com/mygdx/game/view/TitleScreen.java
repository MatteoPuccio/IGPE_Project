package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.GameMain;
import com.mygdx.game.view.animations.Animation;
import com.mygdx.game.view.audio.Sounds;

public class TitleScreen implements Screen {
	
	private SpriteBatch batch;
    protected Stage stage;
    private Viewport viewport;
    private OrthographicCamera camera;
    private TextureAtlas atlas;
    
    private Animation titleScreenAnimation;
    private Array<TextureRegion> titleScreenFrames;
    private Skin skin;
    private BitmapFont titleFont;
    private LabelStyle titleStyle;
	private Table mainTable;
	private Window tutorialWindow;
	private TextButton playButton, optionButton, exitButton;
	private TextButton tutorialButton, exitTutorialButton;
	private Label title;
	public TitleScreen() {
		atlas = new TextureAtlas("skin/skin.atlas");
	    skin = new Skin(Gdx.files.internal("skin/skin.json"), atlas);
	    skin.getFont("boldFont").getData().setScale(2f,2f);
	    
	    titleFont = new BitmapFont(Gdx.files.internal("skin/AncientModernTales.fnt"));
	    titleFont.getData().scale(0.8f);
	    titleStyle = new LabelStyle(titleFont, Color.WHITE);
	    
	    //crea animazione per il title screen 
	    titleScreenFrames = new Array<TextureRegion>();
	    for(int i = 0; i < 6; ++i) {
	    	titleScreenFrames.add(new TextureRegion(new Texture("title_screen/title-screen" + i + ".png")));
	    }
	    titleScreenAnimation = new Animation(titleScreenFrames, 6, 0.5f);
	    
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
        viewport = new FitViewport(Gdx.graphics.getWidth(),Gdx.graphics.getHeight(), camera);
        viewport.apply();

        playButton = new TextButton("PLAY", skin);
        exitButton = new TextButton("EXIT", skin);
        optionButton = new TextButton("OPTIONS", skin);
        tutorialButton = new TextButton("HOW TO PLAY", skin);
        exitTutorialButton = new TextButton("CLOSE", skin);
        
        playButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	GameMain.getInstance().start();
            }
        });
        
        optionButton.addListener(new ClickListener(){
        	@Override
        	public void clicked(InputEvent event, float x, float y) {
        		GameMain.getInstance().options();
        	}
        });
        
        exitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
        
        tutorialButton.addListener(new ClickListener() {
        	@Override
        	public void clicked(InputEvent event, float x, float y) {
        		mainTable.setVisible(false);
        		tutorialWindow.setVisible(true);
        	}
        });
        
        exitTutorialButton.addListener(new ClickListener() {
        	@Override
        	public void clicked(InputEvent event, float x, float y) {
        		mainTable.setVisible(true);
        		tutorialWindow.setVisible(false);
        	}
        });
        
        camera.position.set(0, 0, 0);
        camera.update();

        stage = new Stage(viewport, batch);
        
	}

	@Override
	public void show() {
		stage = new Stage(viewport, batch);
		tutorialWindow = new Window("How To Play", skin);
		
		mainTable = new Table();
        mainTable.setFillParent(true);
        mainTable.center();

        tutorialWindow.setFillParent(true);
        tutorialWindow.setVisible(false);
        tutorialWindow.setResizable(false);
        tutorialWindow.setMovable(false);
        
        title = new Label("No Way To Go But Down",titleStyle);
        title.setColor(new Color(Color.BLACK));
        
        tutorialWindow.add(exitTutorialButton).padTop(500);
        
        mainTable.add(title).pad(0,0,100,0);
        mainTable.row();
        mainTable.add(playButton).growX().pad(20, 300, 20, 300);
        mainTable.row();
        mainTable.add(optionButton).growX().pad(20, 300, 20, 300);
        mainTable.row();
        mainTable.add(tutorialButton).growX().pad(20, 300, 20, 300);
        mainTable.row();
        mainTable.add(exitButton).growX().pad(20, 300, 20, 300);
        mainTable.row();
        
        stage.addActor(tutorialWindow);
        stage.addActor(mainTable);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        Sounds.getInstance().update();
        
        batch.begin();
        titleScreenAnimation.update(delta);
		batch.draw(titleScreenAnimation.getFrame(), 0,0);
		batch.end();
        
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
		mainTable.clear();
		tutorialWindow.clear();
		stage.clear();
	}

	@Override
	public void dispose() {
		skin.dispose();
		atlas.dispose();
		stage.dispose();
		batch.dispose();
		titleFont.dispose();
		for(int i = 0; i < titleScreenFrames.size;++i) {
			titleScreenFrames.get(i).getTexture().dispose();
		}
		titleScreenFrames.clear();
	}
}