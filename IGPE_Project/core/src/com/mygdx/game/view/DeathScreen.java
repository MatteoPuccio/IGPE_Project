package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.GameMain;
import com.mygdx.game.constants.Settings;
import com.mygdx.game.constants.SoundConstants;
import com.mygdx.game.controller.SoundHandler;

public class DeathScreen extends DefaultScreen{
	
	private Label deathLabel;
	private TextButton backButton, quitButton;
	private boolean fullscreen;
	
	public DeathScreen() {
		super(0.259f, 0.157f, 0.208f);

        deathLabel = new Label("You Died", titleStyle);
                
        backButton = new TextButton("TITLE SCREEN", skin);
        quitButton = new TextButton("QUIT", skin);
       
        fullscreen = false;
        if(Settings.getDisplayState() == Settings.FULLSCREEN)
        	fullscreen = true;
        
        backButton.addListener(new ClickListener() {
        	@Override
        	public void clicked(InputEvent event, float x, float y) {
        		GameMain.getInstance().restart();
        		SoundHandler.getInstance().addSoundToQueue(SoundConstants.MENU_CONFIRM);
        	}
        });
        
        quitButton.addListener(new ClickListener() {
        	@Override
        	public void clicked(InputEvent event, float x, float y) {
        		Gdx.app.exit();
        	}
        });
        
        stage = new Stage(viewport, batch);
	}

	@Override
	protected void initMainTable() {
		if(!fullscreen) {
			mainTable.setTransform(true);
		    mainTable.setOrigin(mainTable.getWidth() / 2, mainTable.getHeight() / 2);
		    mainTable.setScale(1.5f);
		}
		
		mainTable.add(deathLabel);
        mainTable.row();
        mainTable.add(backButton).fillX().pad(20, 0, 20, 0);
        mainTable.row();
        mainTable.add(quitButton).fillX().pad(20, 0, 20, 0);
	}

}
