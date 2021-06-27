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
import com.mygdx.game.view.audio.Sounds;

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
		
		//Prima di iniziare ad aggiornare la logica controlla se il fade in quando si entra in una stanza è terminato
		RoomHandler.getInstance().getCurrentRoom().updateTeleportTime(deltaTime);
		if(RoomHandler.getInstance().canTeleport()) {
			GameModel.getInstance().update(deltaTime);
		
			if(leftClickPressed) {
				//trasforma le coordinate di schermo in coordinate del mondo
				Vector3 pointClicked = view.getGamePort().unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
				GameModel.getInstance().getCharacter().setFirstMagicAttacking(true);
				GameModel.getInstance().getCharacter().setAttackPoint(new Vector2(pointClicked.x,pointClicked.y));
			}
		
			else	
				GameModel.getInstance().getCharacter().setFirstMagicAttacking(false);
		
			//Si può usare solo una magia alla volta
			if(rightClickPressed && !leftClickPressed) {
				//trasforma le coordinate di schermo in coordinate del mondo
				Vector3 pointClicked = view.getGamePort().unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
				GameModel.getInstance().getCharacter().setSecondMagicAttacking(true);
				GameModel.getInstance().getCharacter().setAttackPoint(new Vector2(pointClicked.x,pointClicked.y));
			}
			else
				GameModel.getInstance().getCharacter().setSecondMagicAttacking(false);
		}
		else
			GameModel.getInstance().getCharacter().stopMoving();
		
		if(RoomHandler.getInstance().changeMap()) 
			view.changeMap(RoomHandler.getInstance().getCurrentRoom().getTileMap());
		
		//Chiama il fade in quando si entra in una nuova stanza
		view.setBlackScreen(RoomHandler.getInstance().getCurrentRoom().getElapsedTeleportTime());
		
		//Se il fade in è ancora in corso non vengono aggiornate le animazioni
		if(!RoomHandler.getInstance().canTeleport())
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
		
		switch (keycode) {
		
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
			if(RoomHandler.getInstance().canTeleport()) {
				Sounds.getInstance().pauseMusic();
				GameMain.getInstance().pauseScreen();
			}
			break;
	    }
		GameModel.getInstance().getCharacter().setMove(direction, true);
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		
		int direction = -1;
		
		switch (keycode) {
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
	
	public void reset() {
		leftClickPressed = false;
		rightClickPressed = false;
		GameModel.getInstance().reset();
	}
	
	//Simula key e touch up perchè questi non vengono rilevati se il controller non è l'input processor di Gdx.input
	public void removeInputs() {
		keyUp(Keys.A);
		keyUp(Keys.D);
		keyUp(Keys.W);
		keyUp(Keys.S);
		
		touchUp(0, 0, 0, Buttons.LEFT);
		touchUp(0, 0, 0, Buttons.RIGHT);
	}
	
	@Override
	public boolean keyTyped(char character) {return false;}
	
	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {return false;}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {return false;}

	@Override
	public boolean scrolled(float amountX, float amountY) {return false;}
	
}
