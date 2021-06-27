package com.mygdx.game.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.GameMain;
import com.mygdx.game.constants.Settings;
import com.mygdx.game.constants.SoundConstants;
import com.mygdx.game.controller.SoundHandler;

public class PauseScreen extends DefaultScreen{

	private Label optionsLabel,volumeLabel, musicLabel;
	private final Slider volumeSlider, musicSlider;
	private TextButton backButton, menuButton;
	
	public PauseScreen() {
		
		super(0.259f, 0.157f, 0.208f);
		
        optionsLabel = new Label("Pause", titleStyle);
        volumeLabel = new Label("Sound Effects Volume", new LabelStyle(generalFont, Color.BLACK));
        musicLabel = new Label("Ambience Volume", new LabelStyle(generalFont, Color.BLACK));
        
        optionsLabel.setColor(new Color(Color.BLACK));
        volumeLabel.setColor(new Color(Color.BLACK));
        musicLabel.setColor(new Color(Color.BLACK));
        
        volumeSlider = new Slider(0f, 1f, 0.01f, false, skin);
        musicSlider = new Slider(0f, 1f, 0.01f, false, skin);
        backButton = new TextButton("BACK", skin);
        menuButton = new TextButton("MENU", skin);
        
        backButton.addListener(new ClickListener() {
        	@Override
        	public void clicked(InputEvent event, float x, float y) {
        		SoundHandler.getInstance().addSoundToQueue(SoundConstants.MENU_BACK);
        		Sounds.getInstance().playMusic();
        		GameMain.getInstance().unpause();
        	}
        });
        
        menuButton.addListener(new ClickListener() {
        	@Override
        	public void clicked(InputEvent event, float x, float y) {
        		SoundHandler.getInstance().addSoundToQueue(SoundConstants.MENU_BACK);
        		GameMain.getInstance().confirmQuitScreen();
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
	}

	@Override
	protected void initMainTable() {
		mainTable.setTransform(true);
	    mainTable.setOrigin(mainTable.getWidth() / 2, mainTable.getHeight() / 2);
	    mainTable.setScale(2);
		
        volumeSlider.setVisualPercent(Settings.getVolume());
        musicSlider.setVisualPercent(Settings.getMusicVolume());
        
        mainTable.add(optionsLabel).colspan(2).center();
        mainTable.row();
        mainTable.add(volumeLabel).colspan(1).left().padRight(100);
        mainTable.add(volumeSlider).colspan(1).right().padLeft(100);
        mainTable.row();
        mainTable.add(musicLabel).colspan(1).left().padRight(100);
        mainTable.add(musicSlider).colspan(1).right().padLeft(100);
        mainTable.row();
        mainTable.add(backButton).colspan(2).center();
        mainTable.row();
        mainTable.add(menuButton).colspan(2).center();
	}	
}