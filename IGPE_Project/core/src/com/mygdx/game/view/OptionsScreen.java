package com.mygdx.game.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.GameMain;
import com.mygdx.game.constants.Settings;
import com.mygdx.game.constants.SoundConstants;
import com.mygdx.game.view.audio.SoundHandler;

public class OptionsScreen extends DefaultScreen{

	private Label pauseLabel,volumeLabel,difficultyLabel;
	private final Slider volumeSlider;
	private TextButton backButton;
	private SelectBox<String> difficultyBox;
	
	public OptionsScreen() {
		
		super(0.259f, 0.157f, 0.208f);

        pauseLabel = new Label("Options", titleStyle);
        volumeLabel = new Label("Volume",skin);
        difficultyLabel = new Label("Difficulty",skin);
        
        pauseLabel.setColor(new Color(Color.BLACK));
        volumeLabel.setColor(new Color(Color.BLACK));
        difficultyLabel.setColor(new Color(Color.BLACK));
        
        volumeSlider = new Slider(0f, 1f, 0.01f, false, skin);
        backButton = new TextButton("BACK", skin);
        difficultyBox = new SelectBox<String>(skin);
        
        String [] difficultyLevels = new String[] {"Easy","Normal","Hard"};
        difficultyBox.setItems(difficultyLevels);
        difficultyBox.setSelectedIndex(1);
        backButton.addListener(new ClickListener() {
        	@Override
        	public void clicked(InputEvent event, float x, float y) {
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
        
        difficultyBox.addListener(new ChangeListener() {
        	@Override
        	public void changed(ChangeEvent event, Actor actor) {
        		Settings.setDifficulty(difficultyBox.getSelectedIndex());
        	}
        });
	}
	
	protected void initMainTable() {
        volumeSlider.setVisualPercent(Settings.getVolume());
        
        mainTable.add(pauseLabel).colspan(2).center();
        mainTable.row();
        mainTable.add(volumeLabel).colspan(1).left().padRight(100);
        mainTable.add(volumeSlider).colspan(1).right().padLeft(100);
        mainTable.row();
        mainTable.add(difficultyLabel).colspan(1).left().padRight(100);
        mainTable.add(difficultyBox).colspan(1).right().padLeft(100);
        mainTable.row();
        mainTable.add(backButton).colspan(2).center();

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
		super.hide();
	}

	@Override
	public void dispose() {
		super.dispose();
	}
	
}
