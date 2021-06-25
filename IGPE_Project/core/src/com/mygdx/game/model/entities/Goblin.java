package com.mygdx.game.model.entities;

import java.util.LinkedList;
import java.util.List;

import org.xguzm.pathfinding.grid.GridCell;
import org.xguzm.pathfinding.grid.finders.GridFinderOptions;

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
		
		GridFinderOptions opt = new GridFinderOptions();
		opt.allowDiagonal = true;
		
		isRunning = false;
		
		timeForStep = 0.20f;
		elapsedTimeForStep = 0;
		
		attackCooldown = 0.5f;
		attackTimePassed = 0;
		
		damage = 1;
	}
	
	public void update(float deltaTime) {
		
		super.update(deltaTime);
		
		if(currentHealth <= 0) {
			home.getNavigationLayer().getCell((int) currentPosition.x, (int) currentPosition.y).setWalkable(true);
			return;
		}
		
		if(elapsedTimeForStep == 0) {
		
			Vector2 positionToCheck;
			tempPath = AStarUtils.findPathNextToPlayer((int) Math.floor(getPosition().x),(int) Math.floor(getPosition().y));
			
			if(tempPath == null || tempPath.size() == 0 || nextToPlayer()) {
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
			
			if(Vector2.dst2(currentPosition.x, currentPosition.y, GameModel.getInstance().getCharacter().getPosition().x, GameModel.getInstance().getCharacter().getPosition().y) >= 10 * 10) {
				home.getNavigationLayer().getCell((int) currentPosition.x, (int) currentPosition.y).setWalkable(false);
				return;
			}	
			
			home.getNavigationLayer().getCell((int) currentPosition.x, (int) currentPosition.y).setWalkable(true);
			path = new LinkedList<GridCell>(tempPath);	
			
			positionToCheck = new Vector2(path.get(0).getX() + 0.5f, path.get(0).getY() + 0.5f);
			
			Array<Vector2> cellsToMakeWalkable = new Array<Vector2>();
			while(EnemiesHandler.isPositionOccupied(positionToCheck)) {
				
				home.getNavigationLayer().getCell((int) positionToCheck.x, (int) positionToCheck.y).setWalkable(false);
				cellsToMakeWalkable.add(new Vector2(positionToCheck.x, positionToCheck.y));
				tempPath = AStarUtils.findPathNextToPlayer((int) Math.floor(getPosition().x),(int) Math.floor(getPosition().y));
				
				if(tempPath == null || tempPath.size() == 0) {
					
					for(Vector2 cell : cellsToMakeWalkable) {
						home.getNavigationLayer().getCell((int) cell.x, (int) cell.y).setWalkable(true);
					}
					
					home.getNavigationLayer().getCell((int) currentPosition.x, (int) currentPosition.y).setWalkable(false);
					
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
				
			direction.x = nextPosition.x - currentPosition.x;
			if(direction.x == -1)
				flippedX = true;
			else
				flippedX = false;
			
			float alpha = calculateAlpha();			
			Vector2 tempPos = new Vector2(currentPosition);
			tempPos.lerp(nextPosition, alpha);
			body.setTransform(tempPos, body.getAngle());
			
			if(alpha == 1) {
				elapsedTimeForStep = 0;
				path.remove(0);
				currentPosition = nextPosition;
			}
		}
	}
	
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
	public void collidesWith(Collidable coll) {
		// TODO Auto-generated method stub
		
	}
	
}
