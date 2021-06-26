package com.mygdx.game.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.GameMain;
import com.mygdx.game.constants.SoundConstants;
import com.mygdx.game.view.audio.SoundHandler;

public class ConfirmQuitScreen extends DefaultScreen{

	private Label confirmQuit;
	private TextButton yesButton, noButton;
	
	public ConfirmQuitScreen() {
		
		super(0.259f, 0.157f, 0.208f);

        confirmQuit = new Label("Do you want to go back to the main menu?", skin);
        confirmQuit.setColor(new Color(Color.BLACK));
        
        yesButton = new TextButton("YES", skin);
        noButton = new TextButton("NO", skin);
	}

	@Override
	protected void initMainTable() {
		
		yesButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				SoundHandler.getInstance().addSoundToQueue(SoundConstants.MENU_CONFIRM);
				GameMain.getInstance().restart();
			}
		});
		
		noButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				SoundHandler.getInstance().addSoundToQueue(SoundConstants.MENU_BACK);
				System.out.println("quit:" + SoundHandler.getInstance().getQueue().size); 
				GameMain.getInstance().pauseScreen();
			}
		});
		
		mainTable.add(confirmQuit).colspan(2).top();
		mainTable.row();
		mainTable.add(yesButton);
		mainTable.add(noButton);
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
