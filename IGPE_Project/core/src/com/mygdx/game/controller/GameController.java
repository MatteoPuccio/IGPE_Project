package com.mygdx.game.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.mygdx.game.Settings;
import com.mygdx.game.model.GameModel;
import com.mygdx.game.view.GameView;

public class GameController implements InputProcessor 
{
	private GameView view;
	
	public GameController() {
		Gdx.input.setInputProcessor(this);
		view = new GameView();
	}
	@Override
	public boolean keyDown(int keycode) {
		
		int direction = -1;
		
		switch (keycode)
	    {
		case Keys.A:
			direction = Settings.LEFT;
			break;
		case Keys.D:
			direction = Settings.RIGHT;
			break;
		case Keys.W:
			direction = Settings.UP;
			break;
		case Keys.S:
			direction = Settings.DOWN;
			break;
	    }
		GameModel.getInstance().getCharacter().setMove(direction, true);
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		
		int direction = -1;
		
		switch (keycode)
	    {
		case Keys.A:
			direction = Settings.LEFT;
			break;
		case Keys.D:
			direction = Settings.RIGHT;
			break;
		case Keys.W:
			direction = Settings.UP;
			break;
		case Keys.S:
			direction = Settings.DOWN;
			break;
	    }
		GameModel.getInstance().getCharacter().setMove(direction, false);
		return true;
	}
	
	public GameView getView(){
		return view;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		GameModel.getInstance().getGun().shoot();
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(float amountX, float amountY) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
