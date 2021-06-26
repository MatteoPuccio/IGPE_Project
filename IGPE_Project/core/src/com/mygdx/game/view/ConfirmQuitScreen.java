package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.GameMain;

public class ConfirmQuitScreen extends DefaultScreen{

	private Label confirmQuit;
	private TextButton yesButton, noButton;
	
	public ConfirmQuitScreen() {
		
		super(0.259f, 0.157f, 0.208f);

        confirmQuit = new Label("Do you want to go back to the main menu?", skin);
        confirmQuit.setColor(new Color(Color.BLACK));
        
        yesButton = new TextButton("YES", skin);
        noButton = new TextButton("NO", skin);
        yesButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				GameMain.getInstance().restart();
			}
		});
		noButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				GameMain.getInstance().pauseScreen();
			}
		});
        stage = new Stage(viewport, batch); 
	}
	
	@Override
	protected void initMainTable() {
		mainTable.add(confirmQuit).colspan(2).top();
		mainTable.row();
		mainTable.add(yesButton);
		mainTable.add(noButton);
	}
	
	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);
		viewport.apply();
		Gdx.input.setInputProcessor(stage);
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

	@Override
	public void dispose() {
		
	}

	
	
}
