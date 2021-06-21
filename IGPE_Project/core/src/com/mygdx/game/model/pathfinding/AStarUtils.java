package com.mygdx.game.model.pathfinding;

import org.xguzm.pathfinding.grid.GridCell;
import org.xguzm.pathfinding.grid.finders.AStarGridFinder;

public class AStarUtils {
	public static final AStarGridFinder<GridCell> finder = new AStarGridFinder<GridCell>(GridCell.class);
}
