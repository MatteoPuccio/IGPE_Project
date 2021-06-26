package com.mygdx.game.model.collisions;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.game.model.GameModel;
import com.mygdx.game.model.entities.Character;
import com.mygdx.game.model.level.Connection;
import com.mygdx.game.model.level.Room;
import com.mygdx.game.model.level.RoomHandler;
public class Gate implements Collidable {
	
	private Body body;
	private int direction;
	
	public static final int LEFT = 0;
	public static final int RIGHT = 1;
	public static final int UP = 2;
	public static final int DOWN = 3;
	public static final int END = 4;
	
	Vector2 spawnPoint;
	
	public Gate(Body body, int direction, Vector2 spawnPoint) {
		this.body = body;
		this.spawnPoint = new Vector2(spawnPoint);
		body.setUserData(this);
		this.direction = direction;
	}

	@Override
	public void collidesWith(Collidable coll) {
		//se il player collide con un Gate
		if(coll instanceof Character) {
			//se la direzione del gate è END (scala nella tilemap) crea un nuovo floor
			if(direction == END) {
				GameModel.getInstance().setNewFloor();
			}
			else if(RoomHandler.getInstance().canTeleport()) {		
				Connection [] connections = RoomHandler.getInstance().getCurrentRoom().getConnections();
				Room otherRoom = RoomHandler.getInstance().switchRoom(direction);	//prendi la direzione e la stanza dalla quale si è appena entrati
				int otherDirection = connections[direction].getOtherPoint(direction);
				Gate otherGate = otherRoom.getGate(otherDirection);		//prendi il gate dal quale si è appena entrati
				float angle = GameModel.getInstance().getCharacter().getBody().getAngle();
				//sposta il personaggio allo spawnpoint del rispettivo gate
				GameModel.getInstance().setCharacterTransform(otherGate.getSpawnPoint(), angle);
			}
		}
	}
	
	private Vector2 getSpawnPoint() {
		return spawnPoint;
	}

	public Body getBody() {
		return body;
	}
	
	public int getDirection() {
		return direction;
	}
}
