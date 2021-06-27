package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.GameMain;
import com.mygdx.game.constants.SoundConstants;
import com.mygdx.game.controller.SoundHandler;
import com.mygdx.game.view.animations.Animation;

public class TitleScreen extends DefaultScreen{

    private Animation titleScreenAnimation;
    private Array<TextureRegion> titleScreenFrames;
	private Window tutorialWindow;
	private TextButton playButton, optionButton, exitButton;
	private TextButton tutorialButton, exitTutorialButton;
	private Label title;
	
	private Label leftClickLabel, rightClickLabel, wasdLabel;
	private Image leftClick,rightClick,wasd;
	private Texture leftClickTexture, rightClickTexture, wasdTexture;
	
	public TitleScreen() {
	    
		super(0, 0, 0);
	    
	    //crea animazione per il title screen 
	    titleScreenFrames = new Array<TextureRegion>();
	    for(int i = 0; i < 6; ++i) {
	    	titleScreenFrames.add(new TextureRegion(new Texture("title_screen/title-screen" + i + ".png")));
	    }
	    
	    titleScreenAnimation = new Animation(titleScreenFrames, 6, 0.5f);
        title = new Label("No Way To Go But Down",titleStyle);
        title.setColor(new Color(Color.BLACK));
        
        playButton = new TextButton("PLAY", skin);
        exitButton = new TextButton("EXIT", skin);
        optionButton = new TextButton("OPTIONS", skin);
	    tutorialButton = new TextButton("HOW TO PLAY", skin);
	    exitTutorialButton = new TextButton("CLOSE", skin);
	    
	    tutorialWindow = new Window("How To Play", skin);
	    
        playButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	SoundHandler.getInstance().addSoundToQueue(SoundConstants.MENU_CONFIRM);
            	GameMain.getInstance().start();
            }
        });
        
        optionButton.addListener(new ClickListener(){
        	@Override
        	public void clicked(InputEvent event, float x, float y) {
        		SoundHandler.getInstance().addSoundToQueue(SoundConstants.MENU_CONFIRM);
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
        		SoundHandler.getInstance().addSoundToQueue(SoundConstants.MENU_CONFIRM);
        		mainTable.setVisible(false);
        		tutorialWindow.setVisible(true);
        	}
        });
        
        exitTutorialButton.addListener(new ClickListener() {
        	@Override
        	public void clicked(InputEvent event, float x, float y) {
        		SoundHandler.getInstance().addSoundToQueue(SoundConstants.MENU_BACK);
        		mainTable.setVisible(true);
        		tutorialWindow.setVisible(false);
        	}
        });
        
        //inizializzazione tutorialWindow
        leftClickLabel = new Label("Primary Fire", skin);
        rightClickLabel = new Label("Secondary Fire", skin);
        wasdLabel = new Label("Move", skin);
        
        leftClickLabel.setColor(Color.BLACK);
        rightClickLabel.setColor(Color.BLACK);
        wasdLabel.setColor(Color.BLACK); 
        
        leftClickTexture = new Texture("tutorial/mouse-left.png");
        rightClickTexture = new Texture("tutorial/mouse-right.png");
        wasdTexture = new Texture("tutorial/wasd.png");
        
        
        leftClick = new Image(leftClickTexture);
        rightClick = new Image(rightClickTexture);
        wasd = new Image(wasdTexture);
        
	}
	
	@Override
	protected void initMainTable() {
		tutorialWindow.setVisible(false);
		tutorialWindow.setMovable(false);
		tutorialWindow.setFillParent(true);
		
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
        
        tutorialWindow.add(leftClick);
        tutorialWindow.add(leftClickLabel);
        tutorialWindow.row();
        tutorialWindow.add(rightClick);
        tutorialWindow.add(rightClickLabel);
        tutorialWindow.row();
        tutorialWindow.add(wasd);
        tutorialWindow.add(wasdLabel);
        tutorialWindow.row();
        tutorialWindow.add(exitTutorialButton).colspan(2);

        stage.addActor(tutorialWindow);
	}

	@Override
	protected void draw(float delta) {
		batch.begin();
		titleScreenAnimation.update(delta);
	    batch.draw(titleScreenAnimation.getFrame(), 0,0);
	    batch.end();
	}

	@Override
	public void dispose() {
		super.dispose();
		for(int i = 0; i < titleScreenFrames.size;++i) {
			titleScreenFrames.get(i).getTexture().dispose();
		}
		titleScreenFrames.clear();
	}
	
	@Override
	public void hide() {
		super.hide();
		tutorialWindow.clear();
	}
}