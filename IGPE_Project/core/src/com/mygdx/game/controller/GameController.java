package com.mygdx.game.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.mygdx.game.model.GameModel;
import com.badlogic.gdx.InputProcessor;

public class GameController implements InputProcessor 
{

	public GameController() 
	{
		Gdx.input.setInputProcessor(this);
	}
	@Override
	public boolean keyDown(int keycode) 
	{
		switch (keycode)
	    {
		case Keys.LEFT:
			GameModel.getInstance().getCharacter().setLeftMove(true);
			break;
		case Keys.RIGHT:
			GameModel.getInstance().getCharacter().setRightMove(true);
			break;
		case Keys.UP:
			GameModel.getInstance().getCharacter().setUpMove(true);
			break;
		case Keys.DOWN:
			GameModel.getInstance().getCharacter().setDownMove(true);
			break;
	    }
		return true;
	    
	}

	@Override
	public boolean keyUp(int keycode) {
		switch (keycode)
	    {
		case Keys.LEFT:
			GameModel.getInstance().getCharacter().setLeftMove(false);
			break;
		case Keys.RIGHT:
			GameModel.getInstance().getCharacter().setRightMove(false);
			break;
		case Keys.UP:
			GameModel.getInstance().getCharacter().setUpMove(false);
			break;
		case Keys.DOWN:
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
	
}
