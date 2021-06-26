package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.GameMain;

public class DeathScreen extends DefaultScreen{
	
	private Label deathLabel;
	private TextButton backButton, quitButton;
	
	public DeathScreen() {
		super(0.259f, 0.157f, 0.208f);

        deathLabel = new Label("You Died", titleStyle);
                
        backButton = new TextButton("TITLE SCREEN", skin);
        quitButton = new TextButton("QUIT", skin);
        
        backButton.addListener(new ClickListener() {
        	@Override
        	public void clicked(InputEvent event, float x, float y) {
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
        stage = new Stage(viewport, batch);
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
		stage.dispose();
	}

	@Override
	public void dispose() {
		atlas.dispose();
		batch.dispose();
		stage.dispose();
	}

	@Override
	protected void initMainTable() {
		mainTable.add(deathLabel).colspan(2);
        mainTable.row();
        mainTable.add(backButton).growX().pad(20, 300, 20, 300);
        mainTable.row();
        mainTable.add(quitButton).growX().pad(20, 300, 20, 300);		
	}

}
