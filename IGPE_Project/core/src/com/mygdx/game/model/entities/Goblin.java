package com.mygdx.game.model.entities;

import java.util.LinkedList;
import java.util.List;

import org.xguzm.pathfinding.grid.GridCell;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.constants.AnimationConstants;
import com.mygdx.game.model.GameModel;
import com.mygdx.game.model.ai.AStarUtils;
import com.mygdx.game.model.collisions.Collidable;
import com.mygdx.game.model.level.Room;

public class Goblin extends Enemy {

	private List<GridCell> path;
	private List<GridCell> tempPath;
	
	private float timeForStep;
	private float elapsedTimeForStep;
	
	private Vector2 currentPosition;
	private Vector2 nextPosition;
	
	private boolean isRunning;
	
	private float attackCooldown;
	private float attackTimePassed;
	
	private float damage;
	
	public Goblin(Vector2 position, Room home) {
		super(position, 0.4f, 0, 6, 1, home);
		
		currentPosition = new Vector2(getPosition());
		nextPosition = new Vector2(currentPosition);
		
		isRunning = false;
		
		timeForStep = 0.20f;
		elapsedTimeForStep = 0;
		
		attackCooldown = 0.5f;
		attackTimePassed = 0;
		
		damage = 2;
	}
	
	public void update(float deltaTime) {
		
		super.update(deltaTime);
		
		if(currentHealth <= 0) {
			//Se il goblin muore gli altri goblin possono passare sulla cella dove si trovava
			home.getNavigationLayer().getCell((int) currentPosition.x, (int) currentPosition.y).setWalkable(true);
			return;
		}
		
		//Se il goblin è compleatamente passato in una nuova cella ricalcola il percorso per il giocatore
		if(elapsedTimeForStep == 0) {
		
			Vector2 positionToCheck;
			tempPath = AStarUtils.findPathNextToPlayer((int) Math.floor(getPosition().x),(int) Math.floor(getPosition().y));
			
			//In questo caso il goblin è bloccato oppure
			if(tempPath == null || tempPath.size() >= 20 || nextToPlayer()) {
				isRunning = false;
				
				if(nextToPlayer()) {
					attackTimePassed += deltaTime;
					if(attackTimePassed >= attackCooldown) {
						attackTimePassed = 0;
						GameModel.getInstance().getCharacter().takeDamage(damage);
					}
 
					home.getNavigationLayer().getCell((int) currentPosition.x, (int) currentPosition.y).setWalkable(false);
				}
				return;
			}
			else {
				attackTimePassed = 0;
			}
			
			//Implementa il detection range
			if(Vector2.dst2(currentPosition.x, currentPosition.y, GameModel.getInstance().getCharacter().getPosition().x, GameModel.getInstance().getCharacter().getPosition().y) >= 10 * 10) {
				home.getNavigationLayer().getCell((int) currentPosition.x, (int) currentPosition.y).setWalkable(false);
				return;
			}
			
			home.getNavigationLayer().getCell((int) currentPosition.x, (int) currentPosition.y).setWalkable(true);
			path = new LinkedList<GridCell>(tempPath);	
			
			positionToCheck = new Vector2(path.get(0).getX() + 0.5f, path.get(0).getY() + 0.5f);
			
			Array<Vector2> cellsToMakeWalkable = new Array<Vector2>();
			
			//Finchè un goblin si sta dirigendo nella posizione in cui questo goblin vuole andare, ricalcola il percorso fino al giocatore
			//impostando temporaneamente la suddetta cella come non navigabile
			while(EnemiesHandler.isPositionOccupied(positionToCheck)) {
				
				home.getNavigationLayer().getCell((int) positionToCheck.x, (int) positionToCheck.y).setWalkable(false);
				cellsToMakeWalkable.add(new Vector2(positionToCheck.x, positionToCheck.y));
				tempPath = AStarUtils.findPathNextToPlayer((int) Math.floor(getPosition().x),(int) Math.floor(getPosition().y));
				
				//Se non trova nessun percorso, o se deve spostarsi troppo per aggirare gli altri goblin, rimane fermo
				if(tempPath == null || tempPath.size() >= 3) {
					
					home.getNavigationLayer().getCell((int) currentPosition.x, (int) currentPosition.y).setWalkable(false);
					for(Vector2 cell : cellsToMakeWalkable) {
						home.getNavigationLayer().getCell((int) cell.x, (int) cell.y).setWalkable(true);
					}
					
					isRunning = false;				
					return;
				}
				
				path = new LinkedList<GridCell>(tempPath);
				
				positionToCheck = new Vector2(path.get(0).getX() + 0.5f, path.get(0).getY() + 0.5f);
			}
			
			for(Vector2 cell : cellsToMakeWalkable) {
				home.getNavigationLayer().getCell((int) cell.x, (int) cell.y).setWalkable(true);
			}
			
			isRunning = true;
			nextPosition = positionToCheck;
		}
			
		if(isRunning) {
			elapsedTimeForStep += deltaTime;
				
			getDirection().x = nextPosition.x - currentPosition.x;
			if(getDirection().x == -1)
				flippedX = true;
			else
				flippedX = false;
			
			float alpha = calculateAlpha();			
			Vector2 tempPos = new Vector2(currentPosition);
			
			//Sposta ad ogni frame il goblin nella posizione calcolata con l'interpolazione
			//fra currentPosition e nextPosition basandosi sul tempo che è passato da quando ha iniziato a muoversi
			tempPos.lerp(nextPosition, alpha);
			getBody().setTransform(tempPos, getBody().getAngle());
			
			if(alpha == 1) {
				elapsedTimeForStep = 0;
				path.remove(0);
				currentPosition = nextPosition;
			}
		}
	}
	
	//Calcola l'alpha per l'interpolazione
	private float calculateAlpha() {
		if(elapsedTimeForStep >= timeForStep)
			return 1;
		
		return elapsedTimeForStep / timeForStep;
	}
	
	private boolean nextToPlayer() {
		Array<Vector2> possiblePositions = AStarUtils.getPossibleCellsNextToPlayer(GameModel.getInstance().getCharacter().getPosition().x, GameModel.getInstance().getCharacter().getPosition().y, false);
		
		return possiblePositions.contains(new Vector2((int) currentPosition.x, (int) currentPosition.y), false) || currentPosition.equals(new Vector2((int) GameModel.getInstance().getCharacter().getPosition().x + 0.5f, (int) GameModel.getInstance().getCharacter().getPosition().y + 0.5f));			
	}
	
	public Vector2 getNextPosition() {
		return nextPosition;
	}
	
	public Vector2 getCurrentPosition() {
		return currentPosition;
	}

	@Override
	public int getCurrentAnimationId() {
		if(isRunning)
			return AnimationConstants.GOBLIN_RUN_ANIMATION;
		return AnimationConstants.GOBLIN_IDLE_ANIMATION;
	}

	@Override
	public boolean isFlipped() {
		return flippedX;
	}

	@Override
	public void collidesWith(Collidable coll) {}
	
}
