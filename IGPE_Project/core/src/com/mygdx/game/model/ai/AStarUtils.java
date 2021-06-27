package com.mygdx.game.model.ai;

import java.util.List;

import org.xguzm.pathfinding.grid.GridCell;
import org.xguzm.pathfinding.grid.finders.AStarGridFinder;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.model.GameModel;
import com.mygdx.game.model.entities.EnemiesHandler;
import com.mygdx.game.model.level.RoomHandler;

public class AStarUtils {
	private static final AStarGridFinder<GridCell> finder = new AStarGridFinder<GridCell>(GridCell.class);
	
	public static final List<GridCell> findPathNextToPlayer(int startX, int startY) {
		
		float playerX = (float) Math.floor(GameModel.getInstance().getCharacter().getPosition().x);
		float playerY = (float) Math.floor(GameModel.getInstance().getCharacter().getPosition().y);
		
		Array<Vector2> possiblePositions = getPossibleCellsNextToPlayer(playerX, playerY, true);
		
		Vector2 closestPossiblePosition = new Vector2(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY);
		
		for(Vector2 possiblePosition : possiblePositions) {

			if(Vector2.dst2(playerX, playerY, possiblePosition.x, possiblePosition.y) < Vector2.dst2(playerX, playerY, closestPossiblePosition.x, closestPossiblePosition.y) && !EnemiesHandler.isPositionOccupied(possiblePosition))
				closestPossiblePosition = possiblePosition;
		}
	
		if(closestPossiblePosition.equals(new Vector2(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)))
			return null;
		
		return finder.findPath(startX, startY, (int) closestPossiblePosition.x, (int) closestPossiblePosition.y, RoomHandler.getInstance().getCurrentRoom().getNavigationLayer());
	}
	
	//Restituisce tutte le celle immediatamente vicine al giocatore
	//Se checkWalkable = true le celle devono essere navigabili dal pathfinding (la navigabilità è stabilita dal navigation layer delle stanze)
	public static Array<Vector2> getPossibleCellsNextToPlayer(float playerX, float playerY, boolean checkWalkable){
		int adjacentX;
		int adjacentY;
		
		Array<Vector2> possiblePositions = new Array<Vector2>();
		
		for(int i = 0; i < 8; ++i)
		{
			Vector2 direction = new Vector2();
			
			//Trova i vettori corrispondenti agli angoli tra la cella del giocatore e le 8 vicine e li salva in direction
			SteeringUtils.angleToVector(direction, i * (float) Math.PI / 4);
			adjacentX = (int) (playerX + Math.round(direction.x));
			adjacentY =  (int) (playerY + Math.round(direction.y));
			
			if(checkWalkable) {
				if(RoomHandler.getInstance().getCurrentRoom().getNavigationLayer().getCell(adjacentX, adjacentY) != null && RoomHandler.getInstance().getCurrentRoom().getNavigationLayer().getCell(adjacentX, adjacentY).isWalkable())
					possiblePositions.add(new Vector2(adjacentX,adjacentY));
			}
			else
				possiblePositions.add(new Vector2(adjacentX,adjacentY));
		}
		
		return possiblePositions;
	}
}
