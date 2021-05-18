package com.mygdx.game.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.mygdx.game.model.GameModel;
import com.mygdx.game.view.GameView;

public class GameController implements InputProcessor 
{
	private GameView view;
	public GameController() 
	{
		Gdx.input.setInputProcessor(this);
		view = new GameView();
	}
	@Override
	public boolean keyDown(int keycode) 
	{
		switch (keycode)
	    {
		case Keys.A:
			GameModel.getInstance().getCharacter().setLeftMove(true);
			break;
		case Keys.D:
			GameModel.getInstance().getCharacter().setRightMove(true);
			break;
		case Keys.W:
			GameModel.getInstance().getCharacter().setUpMove(true);
			break;
		case Keys.S:
			GameModel.getInstance().getCharacter().setDownMove(true);
			break;
	    }
		return true;
	    
	}

	@Override
	public boolean keyUp(int keycode) {
		switch (keycode)
	    {
		case Keys.A:
			GameModel.getInstance().getCharacter().setLeftMove(false);
			break;
		case Keys.D:
			GameModel.getInstance().getCharacter().setRightMove(false);
			break;
		case Keys.W:
			GameModel.getInstance().getCharacter().setUpMove(false);
			break;
		case Keys.S:
			GameModel.getInstance().getCharacter().setDownMove(false);
			break;
	    }
		return true;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
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
	
	public GameView getView()
	{
		return view;
	}
	
	
}
