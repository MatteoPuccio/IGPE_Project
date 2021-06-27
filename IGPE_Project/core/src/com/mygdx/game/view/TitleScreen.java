package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.GameMain;
import com.mygdx.game.constants.SoundConstants;
import com.mygdx.game.view.animations.Animation;
import com.mygdx.game.view.audio.SoundHandler;

public class TitleScreen extends DefaultScreen{

    private Animation titleScreenAnimation;
    private Array<TextureRegion> titleScreenFrames;
	private Window tutorialWindow;
	private TextButton playButton, optionButton, exitButton;
	private TextButton tutorialButton, exitTutorialButton;
	private Label title;
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
        
        tutorialWindow.add(exitTutorialButton).pad(500,0,0,0);
        
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
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		super.dispose();
		for(int i = 0; i < titleScreenFrames.size;++i) {
			titleScreenFrames.get(i).getTexture().dispose();
		}
		titleScreenFrames.clear();
	}
}