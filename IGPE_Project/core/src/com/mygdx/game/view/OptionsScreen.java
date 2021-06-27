package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.GameMain;
import com.mygdx.game.constants.Settings;
import com.mygdx.game.constants.SoundConstants;
import com.mygdx.game.controller.SoundHandler;

public class OptionsScreen extends DefaultScreen{

	private Label pauseLabel,volumeLabel, musicLabel, difficultyLabel, displayModeLabel;
	private final Slider volumeSlider, musicSlider;
	private TextButton backButton;
	private SelectBox<String> displayModeBox;
	private SelectBox<String> difficultyBox;
	
	private final static int FULLSCREEN = 0;
	private final static int WINDOWED = 1;
	
	private int state;
	
	private int previousWidth, previousHeigth;
	
	public OptionsScreen() {
		
		super(0.259f, 0.157f, 0.208f);

		//state è inizialmente windowed così che il change listener di displayModeBox rilevi il change e metta fullscreen
		state = WINDOWED;

        pauseLabel = new Label("Options", titleStyle);
        volumeLabel = new Label("Sound Effects Volume", new LabelStyle(generalFont, Color.BLACK));
        musicLabel = new Label("Ambience Volume", new LabelStyle(generalFont, Color.BLACK);
        difficultyLabel = new Label("Difficulty", new LabelStyle(generalFont, Color.BLACK));
        displayModeLabel = new Label("Display mode", new LabelStyle(generalFont, Color.BLACK));
          
        volumeSlider = new Slider(0f, 1f, 0.01f, false, skin);
        musicSlider = new Slider(0f, 1f, 0.01f, false, skin);
        backButton = new TextButton("BACK", skin);
        difficultyBox = new SelectBox<String>(skin);
        displayModeBox = new SelectBox<String>(skin);
        
        previousWidth = 1024;
        previousHeigth = 768;
        
        
        backButton.addListener(new ClickListener() {
        	@Override
        	public void clicked(InputEvent event, float x, float y) {
        		SoundHandler.getInstance().addSoundToQueue(SoundConstants.MENU_BACK);
        		GameMain.getInstance().backToTitle();
        	}
        });
        
        volumeSlider.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				Settings.setVolume(volumeSlider.getValue());
			}
        });
        
        volumeSlider.addListener(new ClickListener() {
        	
        	@Override
        	public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
        		SoundHandler.getInstance().addSoundToQueue(SoundConstants.MENU_ERROR);
        	}
        	
        	@Override
        	public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        		return true;
        	}
        	
        });
        
        musicSlider.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				Settings.setMusicVolume(musicSlider.getValue());
			}
        });
        
        musicSlider.addListener(new ClickListener() {
        	
        	@Override
        	public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
        		SoundHandler.getInstance().addSoundToQueue(SoundConstants.MENU_ERROR);
        	}
        	
        	@Override
        	public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        		return true;
        	}
        	
        });
        
        
        difficultyBox.addListener(new ChangeListener() {
        	@Override
        	public void changed(ChangeEvent event, Actor actor) {
        		Settings.setDifficulty(difficultyBox.getSelectedIndex());
        	}
        });
        
        displayModeBox.addListener(new ChangeListener() {
        	@Override
        	public void changed(ChangeEvent event, Actor actor) {
        		if(displayModeBox.getSelected().equals("Fullscreen") && state != FULLSCREEN) {
        			previousWidth = Gdx.graphics.getWidth();
        			previousHeigth = Gdx.graphics.getHeight();
        			Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
        			state = FULLSCREEN;
        		}
        		
        		else if(displayModeBox.getSelected().equals("Windowed") && state != WINDOWED) {
        			Gdx.graphics.setWindowedMode(previousWidth, previousHeigth);
        			state = WINDOWED;
        		}
     
        	}
        });
        
        String [] difficultyLevels = new String[] {"Easy","Normal","Hard"};
        difficultyBox.setItems(difficultyLevels);
        difficultyBox.setSelectedIndex(1);
        
        String [] displayModes = new String [] {"Fullscreen", "Windowed"};
        displayModeBox.setItems(displayModes);
        displayModeBox.setSelected("Fullscreen");
	}

	@Override
	protected void initMainTable() {
		
        volumeSlider.setVisualPercent(Settings.getVolume());
        musicSlider.setVisualPercent(Settings.getMusicVolume());
        
        mainTable.add(pauseLabel).colspan(2).center();
        mainTable.row();
        mainTable.add(volumeLabel).colspan(1).left().padRight(100);
        mainTable.add(volumeSlider).colspan(1).right().padLeft(100);
        mainTable.row();
        mainTable.add(musicLabel).colspan(1).left().padRight(100);
        mainTable.add(musicSlider).colspan(1).right().padLeft(100);
        mainTable.row();
        mainTable.add(difficultyLabel).colspan(1).left().padRight(100);
        mainTable.add(difficultyBox).colspan(1).right().padLeft(100);
        mainTable.row();
        mainTable.add(displayModeLabel).colspan(1).left().padRight(100);
        mainTable.add(displayModeBox).colspan(1).right().padLeft(100);
        mainTable.row();
        mainTable.add(backButton).colspan(2).center();
        
	}
}
