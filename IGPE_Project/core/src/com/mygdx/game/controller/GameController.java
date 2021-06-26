package com.mygdx.game.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.GameMain;
import com.mygdx.game.constants.Settings;
import com.mygdx.game.model.GameModel;
import com.mygdx.game.model.level.RoomHandler;
import com.mygdx.game.view.GameView;

public class GameController implements InputProcessor {
	
	private GameView view;
	private boolean leftClickPressed;
	private boolean rightClickPressed;
	
	public GameController() {
		Gdx.input.setInputProcessor(this);
		view = new GameView();
		leftClickPressed = false;
		rightClickPressed = false;
	}
	
	public void update(float deltaTime) {
		
		RoomHandler.getInstance().getCurrentRoom().updateTeleportTime(deltaTime);
		if(RoomHandler.getInstance().canTeleport()) {
			GameModel.getInstance().update(deltaTime);
		
			if(leftClickPressed) {
				Vector3 pointClicked = view.getGamePort().unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
				GameModel.getInstance().getCharacter().setFirstMagicAttacking(true);
				GameModel.getInstance().getCharacter().setAttackPoint(new Vector2(pointClicked.x,pointClicked.y));
			}
		
			else	
				GameModel.getInstance().getCharacter().setFirstMagicAttacking(false);
		
			if(rightClickPressed && !leftClickPressed) {
				Vector3 pointClicked = view.getGamePort().unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
				GameModel.getInstance().getCharacter().setSecondMagicAttacking(true);
				GameModel.getInstance().getCharacter().setAttackPoint(new Vector2(pointClicked.x,pointClicked.y));
			}
			else
				GameModel.getInstance().getCharacter().setSecondMagicAttacking(false);
		}
		else
			GameModel.getInstance().getCharacter().stopMoving();
		
		if(RoomHandler.getInstance().changeMap()) {
			view.changeMap(RoomHandler.getInstance().getCurrentRoom().getTileMap());
		}
		
		view.setBlackScreen(RoomHandler.getInstance().getCurrentRoom().getElapsedTeleportTime());
		if(RoomHandler.getInstance().getCurrentRoom().getElapsedTeleportTime() < RoomHandler.getInstance().getCurrentRoom().getTeleportTime())
			view.render(deltaTime, false);
		else
			view.render(deltaTime,true);
	}
	
	public void dispose() {
		GameModel.getInstance().dispose();
		view.dispose();
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
		case Keys.ESCAPE:
			GameMain.getInstance().pauseScreen();
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
		if(button == Buttons.LEFT)
			leftClickPressed = true;
		
		if(button  == Buttons.RIGHT) 
			rightClickPressed = true;
		
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		if(button == Buttons.LEFT) 
			leftClickPressed = false;
			
		if(button == Buttons.RIGHT)
			rightClickPressed = false;
		
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(float amountX, float amountY) {
		return false;
	}
	
	public void reset() {
		leftClickPressed = false;
		rightClickPressed = false;
		GameModel.getInstance().reset();
	}
	
}
