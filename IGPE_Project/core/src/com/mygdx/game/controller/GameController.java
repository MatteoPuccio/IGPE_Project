package com.mygdx.game.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.Settings;
import com.mygdx.game.model.GameModel;
import com.mygdx.game.model.Magic;
import com.mygdx.game.model.MeleeWeapon;
import com.mygdx.game.model.Weapon;
import com.mygdx.game.view.GameView;

public class GameController implements InputProcessor 
{
	private GameView view;
	private boolean settingAttackPoint;
	
	public GameController() {
		Gdx.input.setInputProcessor(this);
		view = new GameView();
		settingAttackPoint = false;
	}
	
	public void update(float deltaTime) {
		GameModel.getInstance().update(deltaTime);
		if(settingAttackPoint)
		{
			setWeaponAttackPoint();
		}
		view.render(deltaTime);
	}
	
	public void dispose() {
		GameModel.getInstance().dispose();
		view.dispose();
	}
	
	private void setWeaponAttackPoint() {
		Vector3 pointClicked = view.getCamera().unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
		GameModel.getInstance().getCharacter().getWeapon().setAttackPoint(new Vector2(pointClicked.x,pointClicked.y));
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
		case Keys.NUM_1:
			GameModel.getInstance().getCharacter().setWeapon(new Magic(0, 10, 0.2f, 10));
			break;
		case Keys.NUM_2:
			GameModel.getInstance().getCharacter().setWeapon(new MeleeWeapon(1,2.0f,1.0f));
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
		setWeaponAttackPoint();
		settingAttackPoint = true;
		GameModel.getInstance().getCharacter().getWeapon().setAttacking(true);
		view.getSounds().fire.play(0.1f);
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		GameModel.getInstance().getCharacter().getWeapon().setAttacking(false);
		settingAttackPoint = false;
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
