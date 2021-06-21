package com.mygdx.game.model.entities;

import java.util.LinkedList;

import java.util.List;

import org.xguzm.pathfinding.grid.GridCell;
import org.xguzm.pathfinding.grid.finders.GridFinderOptions;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.model.GameModel;
import com.mygdx.game.model.ai.AStarUtils;
import com.mygdx.game.model.level.RoomHandler;
import com.mygdx.game.model.weapons.MeleeWeapon;

public class Goblin extends Enemy {

	private List<GridCell> path;
	private List<GridCell> tempPath;
	
	private float timeForStep =0.5f;
	private float timePassed = 0;
	private Vector2 currentPosition;
	private Vector2 nextPosition;
	
	private boolean isRunning;
	
	private MeleeWeapon goblinDagger;
	
	public Goblin(Vector2 position) {
		super(position, 0.3f, true, 0);
		body.setUserData("goblin");
		
		home = RoomHandler.getInstance().getCurrentRoom();
		
		currentPosition = new Vector2(getPosition());
		nextPosition = new Vector2(currentPosition);
		
		GridFinderOptions opt = new GridFinderOptions();
		opt.allowDiagonal = true;
		
		isRunning = false;
		
		goblinDagger = new MeleeWeapon(1, 1f, 1, this);
	}
	
	public void update(float deltaTime) {
		super.update(deltaTime);
		
		if(timePassed == 0) {
			
			tempPath = AStarUtils.findPathNextToPlayer((int) Math.floor(getPosition().x),(int) Math.floor(getPosition().y));
			
			if(tempPath == null || tempPath.size() == 0) {
				isRunning = false;
				
				goblinDagger.setAttacking(true);
				goblinDagger.setAttackPoint(GameModel.getInstance().getCharacter().getPosition());
				goblinDagger.attack(deltaTime);
				
				return;
			}
			
			goblinDagger.setAttacking(false);
			path = new LinkedList<GridCell>(tempPath);
			Vector2 tempPos = new Vector2(path.get(0).x + 0.5f, path.get(0).y + 0.5f);
			if(EnemiesHandler.getInstance().isPositionOccupied(tempPos)) {
				isRunning = false;
			}
			else {
				isRunning = true;
				nextPosition = tempPos;
			}
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
	
	public Vector2 getNextPosition() {
		return nextPosition;
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
