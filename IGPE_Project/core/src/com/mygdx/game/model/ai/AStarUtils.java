package com.mygdx.game.model.ai;

import java.util.List;

import org.xguzm.pathfinding.gdxbridge.NavigationTiledMapLayer;
import org.xguzm.pathfinding.grid.GridCell;
import org.xguzm.pathfinding.grid.finders.AStarGridFinder;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.model.GameModel;
import com.mygdx.game.model.level.RoomHandler;

public class AStarUtils {
	private static final AStarGridFinder<GridCell> finder = new AStarGridFinder<GridCell>(GridCell.class);
	
	public static final List<GridCell> findPathNextToPlayer(int startX, int startY) {
		
		NavigationTiledMapLayer navLayer = RoomHandler.getInstance().getCurrentRoom().getNavigationLayer();
		
		float playerX = (float) Math.floor(GameModel.getInstance().getCharacter().getPosition().x);
		float playerY = (float) Math.floor(GameModel.getInstance().getCharacter().getPosition().y);
		
		int adjacentX;
		int adjacentY;
		
		Array<Vector2> possiblePositions = new Array<Vector2>();
		
		for(int i = 0; i < 8; ++i)
		{
			Vector2 direction = new Vector2();
			SteeringUtils.angleToVector(direction, i * (float) Math.PI / 4);
			adjacentX = (int) (playerX + direction.x);
			adjacentY =  (int) (playerY + direction.y);
			if(navLayer.getCell(adjacentX, adjacentY).isWalkable())
				possiblePositions.add(new Vector2(adjacentX,adjacentY));
		}
		
		if(possiblePositions.size == 0)
			return null;
		
		Vector2 closestPossiblePosition = new Vector2(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY);
		
		for(Vector2 possiblePosition : possiblePositions) {
			if(Vector2.dst2(playerX, playerY, possiblePosition.x, possiblePosition.y) < Vector2.dst2(playerX, playerY, closestPossiblePosition.x, closestPossiblePosition.y))
				closestPossiblePosition = possiblePosition;
		}
		return finder.findPath(startX, startY, (int) closestPossiblePosition.x, (int) closestPossiblePosition.y, navLayer);
	}
}
