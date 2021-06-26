package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.GameMain;
import com.mygdx.game.constants.SoundConstants;
import com.mygdx.game.view.audio.SoundHandler;
import com.mygdx.game.view.audio.Sounds;

public class DeathScreen extends DefaultScreen{
	
	private Label deathLabel;
	private TextButton backButton, quitButton;
	
	public DeathScreen() {
		
		super(0.259f, 0.157f, 0.208f);

        deathLabel = new Label("You Died", titleStyle);
                
        backButton = new TextButton("TITLE SCREEN", skin);
        quitButton = new TextButton("QUIT", skin);
        
        stage = new Stage(viewport, batch);
	}

	@Override
	protected void initMainTable() {
		
        backButton.addListener(new ClickListener() {
        	@Override
        	public void clicked(InputEvent event, float x, float y) {
        		SoundHandler.getInstance().addSoundToQueue(SoundConstants.MENU_CONFIRM);
        		GameMain.getInstance().restart();
        	}
        });
        
        quitButton.addListener(new ClickListener() {
        	@Override
        	public void clicked(InputEvent event, float x, float y) {
        		SoundHandler.getInstance().addSoundToQueue(SoundConstants.MENU_BACK);
        		Gdx.app.exit();
        	}
        });
        
        mainTable.add(deathLabel).colspan(2);
        mainTable.row();
        mainTable.add(backButton).growX().pad(20, 300, 20, 300);
        mainTable.row();
        mainTable.add(quitButton).growX().pad(20, 300, 20, 300);
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
		// TODO Auto-generated method stub
		
	}
}
