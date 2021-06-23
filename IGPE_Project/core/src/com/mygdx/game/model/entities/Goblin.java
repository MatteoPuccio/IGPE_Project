package com.mygdx.game.model.entities;

import java.util.LinkedList;
import java.util.List;

import org.xguzm.pathfinding.grid.GridCell;
import org.xguzm.pathfinding.grid.finders.GridFinderOptions;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.model.GameModel;
import com.mygdx.game.model.ai.AStarUtils;
import com.mygdx.game.model.level.RoomHandler;

public class Goblin extends Enemy {

	private List<GridCell> path;
	private List<GridCell> tempPath;
	
	private float timeForStep = 0.3f;
	private float timePassed = 0;
	private Vector2 currentPosition;
	private Vector2 nextPosition;
	
	private boolean isRunning;
	
	private float attackCooldown;
	private float attackTimePassed;
	
	public Goblin(Vector2 position) {
		super(position, 0.4f, 0, 6, 1);
		
		home = RoomHandler.getInstance().getCurrentRoom();
		
		currentPosition = new Vector2(getPosition());
		nextPosition = new Vector2(currentPosition);
		
		GridFinderOptions opt = new GridFinderOptions();
		opt.allowDiagonal = true;
		
		isRunning = false;
		
		attackCooldown = 1;
		attackTimePassed = 0;
	}
	
	public void update(float deltaTime) {
		super.update(deltaTime);
		
		if(timePassed == 0) {
		
			Vector2 tempPos;
			tempPath = AStarUtils.findPathNextToPlayer((int) Math.floor(getPosition().x),(int) Math.floor(getPosition().y));
			
			if(tempPath == null || tempPath.size() == 0 || nextToPlayer()) {
				isRunning = false;
				
				if(nextToPlayer()) {
					attackTimePassed += deltaTime;
					if(attackTimePassed >= attackCooldown) {
						attackTimePassed = 0;
						GameModel.getInstance().getCharacter().takeDamage(1);
					}
					home.getNavigationLayer().getCell((int) currentPosition.x, (int) currentPosition.y).setWalkable(false);
				}
				else {
					attackTimePassed = 0;
				}
				return;
			}
			home.getNavigationLayer().getCell((int) currentPosition.x, (int) currentPosition.y).setWalkable(true);
			path = new LinkedList<GridCell>(tempPath);	
			
			tempPos = new Vector2(path.get(0).getX() + 0.5f, path.get(0).getY() + 0.5f);
			
			Array<Vector2> cellsToMakeWalkable = new Array<Vector2>();
			while(EnemiesHandler.isPositionOccupied(tempPos)) {
				
				home.getNavigationLayer().getCell((int) tempPos.x, (int) tempPos.y).setWalkable(false);
				cellsToMakeWalkable.add(new Vector2(tempPos.x, tempPos.y));
				tempPath = AStarUtils.findPathNextToPlayer((int) Math.floor(getPosition().x),(int) Math.floor(getPosition().y));
				
				if(tempPath == null || tempPath.size() == 0) {
					isRunning = false;				
					return;
				}
				
				path = new LinkedList<GridCell>(tempPath);
				
				tempPos = new Vector2(path.get(0).getX() + 0.5f, path.get(0).getY() + 0.5f);
			}
			
			for(Vector2 cell : cellsToMakeWalkable) {
				home.getNavigationLayer().getCell((int) cell.x, (int) cell.y).setWalkable(true);
			}
			
			isRunning = true;
			nextPosition = tempPos;
		}
			
		if(isRunning) {
			timePassed += deltaTime;
				
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
				timePassed = 0;
				path.remove(0);
				currentPosition = nextPosition;
			}
		}
	}
	
	private float calculateAlpha() {
		if(timePassed >= timeForStep)
			return 1;
		
		return timePassed / timeForStep;
	}
	
	private boolean nextToPlayer() {
		Array<Vector2> possiblePositions = AStarUtils.getPossibleCellsNextToPlayer(GameModel.getInstance().getCharacter().getPosition().x, GameModel.getInstance().getCharacter().getPosition().y, false);
		
		return possiblePositions.contains(new Vector2((int) currentPosition.x, (int) currentPosition.y), false);			
	}
	
	public Vector2 getNextPosition() {
		return nextPosition;
	}
	
	public Vector2 getCurrentPosition() {
		return currentPosition;
	}

	@Override
	public String getCurrentAnimationString() {
		if(isRunning)
			return "goblin run animation";
		return "goblin idle animation";
	}

	@Override
	public boolean isFlipped() {
		return flippedX;
	}

	@Override
	public Vector2 getAnimationPosition() {
		return body.getPosition();
	}

	@Override
	public float getAnimationWidth() {
		return radius;
	}

	@Override
	public float getAnimationHeigth() {
		return radius;
	}

	@Override
	public float getRotation() {
		return 0;
	}
	
}
