package com.mygdx.game.view;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.GameMain;
import com.mygdx.game.constants.Settings;
import com.mygdx.game.constants.SoundConstants;
import com.mygdx.game.controller.SoundHandler;
import com.mygdx.game.view.audio.Sounds;

public class ConfirmQuitScreen extends DefaultScreen{

	private Label confirmQuit;
	private TextButton yesButton, noButton;
	private boolean fullscreen;
	
	public ConfirmQuitScreen() {
		
		super(0.259f, 0.157f, 0.208f);

        confirmQuit = new Label("Do you want to go back to the main menu?", new LabelStyle(generalFont, Color.BLACK));
        
        yesButton = new TextButton("YES", skin);
        noButton = new TextButton("NO", skin);
        
        fullscreen = false;
        if(Settings.getDisplayState() == Settings.FULLSCREEN)
        	fullscreen = true;
        
        yesButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				SoundHandler.getInstance().addSoundToQueue(SoundConstants.MENU_CONFIRM);
				Sounds.getInstance().stopMusic();
				GameMain.getInstance().restart();
			}
		});
		noButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				SoundHandler.getInstance().addSoundToQueue(SoundConstants.MENU_BACK);
				GameMain.getInstance().pauseScreen();
			}
		});
        stage = new Stage(viewport, batch); 
	}
	
	@Override
	protected void initMainTable() {
		//raddoppia dimensione della table
		
		if(!fullscreen) {
			mainTable.setTransform(true);
		    mainTable.setOrigin(mainTable.getWidth() / 2, mainTable.getHeight() / 2);
		    mainTable.setScale(1.5f);
		}
	    
		mainTable.add(confirmQuit).colspan(2).top();
		mainTable.row();
		mainTable.add(yesButton).padTop(100);
		mainTable.add(noButton).padTop(100);
	}
	
	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);
		viewport.apply();
	}
}
