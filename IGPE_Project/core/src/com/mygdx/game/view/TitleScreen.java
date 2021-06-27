package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.GameMain;
import com.mygdx.game.constants.SoundConstants;
import com.mygdx.game.controller.SoundHandler;
import com.mygdx.game.model.GameModel;
import com.mygdx.game.view.animations.Animation;

public class TitleScreen extends DefaultScreen{

    private Animation titleScreenAnimation;
    private Array<TextureRegion> titleScreenFrames;
	private Window tutorialWindow;
	private TextButton playButton, optionButton, exitButton;
	private TextButton tutorialButton, exitTutorialButton;
	private Label title;
	private Label maxCoinsLabel;
	private Label maxFloorLabel;
	
	private Texture coinTexture;
	private Texture floorTexture;
	private Image coinImage;
	private Image floorImage;
	
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
	    
	    maxCoinsLabel = new Label("Highest coins obtained:", new LabelStyle(generalFont, Color.BLACK));
	    maxFloorLabel = new Label("Lowest floor reached:", new LabelStyle(generalFont, Color.BLACK));
	    maxFloorLabel.setColor(new Color(Color.BLACK));
	    
	    coinTexture = new Texture("images/coin.png");
	    coinImage = new Image(coinTexture);
	    floorTexture = new Texture("images/floor.png");
	    floorImage = new Image(floorTexture);
	    
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
		
		mainTable.add(title).pad(0,0,80,0).colspan(4);
        mainTable.row();
        mainTable.add(playButton).growX().pad(20, 300, 20, 300).colspan(4);
        mainTable.row();
        mainTable.add(optionButton).growX().pad(20, 300, 20, 300).colspan(4);
        mainTable.row();
        mainTable.add(tutorialButton).growX().pad(20, 300, 20, 300).colspan(4);
        mainTable.row();
        mainTable.add(exitButton).growX().pad(20, 300, 20, 300).colspan(4);
        mainTable.row();
        
        Preferences preferences = Gdx.app.getPreferences("Game preferences");
        maxCoinsLabel.setText("Most coins obtained: " + preferences.getInteger("Max Coins"));
        mainTable.add(maxCoinsLabel).colspan(1);
        mainTable.add(coinImage).colspan(1).size(50).padRight(200);
        
        String prefix;
        if(preferences.getInteger("Max Floor") != 0)
        	prefix = "-";
        else
        	prefix = "";
        maxFloorLabel.setText("Lowest floor reached: " + prefix + preferences.getInteger("Max Floor"));
        mainTable.add(floorImage).colspan(1).size(50).padLeft(200);
        mainTable.add(maxFloorLabel).colspan(1);
        
        tutorialWindow.add(exitTutorialButton).pad(500,0,0,0);
        
        stage.addActor(tutorialWindow);
        
		preferences = Gdx.app.getPreferences("Game preferences");
		if(GameModel.getInstance().getFloor() > preferences.getInteger("Max Floor"))
			preferences.putInteger("Max Floor", GameModel.getInstance().getFloor());
		if(GameModel.getInstance().getCoins() > preferences.getInteger("Max Coins"))
			preferences.putInteger("Max Coins", GameModel.getInstance().getCoins());
		preferences.flush();
		
//		mainTable.debugAll();
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
		coinTexture.dispose();
		floorTexture.dispose();
	}
}