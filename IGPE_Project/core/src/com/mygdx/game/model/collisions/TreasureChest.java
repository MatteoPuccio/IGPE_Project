package com.mygdx.game.model.collisions;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.mygdx.game.constants.AnimationConstants;
import com.mygdx.game.model.Animated;
import com.mygdx.game.model.GameModel;
import com.mygdx.game.model.entities.Character;
import com.mygdx.game.model.level.RoomHandler;

public class TreasureChest implements Collidable, Animated {

	private Body body;
	
	public final static int CLOSED = 0;
	public final static int OPEN = 1;
	
	private int state;
	
	public TreasureChest(Vector2 position) {
		BodyDef bDef = new BodyDef();
		bDef.position.set(position);
		bDef.type = BodyType.StaticBody;
		
		body = GameModel.getInstance().getWorld().createBody(bDef);
		
		PolygonShape shape = new PolygonShape();
		
		Vector2[] vertices = new Vector2[] {
				new Vector2(- 0.5f, 0.5f),
				new Vector2(0.5f, 0.5f),
				new Vector2(0.5f, - 0.10f),
				new Vector2(- 0.5f, - 0.10f)
				
		};
		
		shape.set(vertices);
		
		body.createFixture(shape, 1);
		shape.dispose();
		
		body.setUserData(this);
		
		state = CLOSED;
	}
	
	@Override
	public void collidesWith(Collidable coll) {

		if(coll instanceof Character && state == CLOSED) {
			state = OPEN;
			RoomHandler.getInstance().getCurrentRoom().setPowerupSpawnPosition(body.getPosition());;
		}
		
	}
	
	public Body getBody() {
		return body;
	}

	@Override
	public int getCurrentAnimationId() {
		if(state == CLOSED)
			return AnimationConstants.CHEST_CLOSED;
		return AnimationConstants.CHEST_OPEN;
	}

	@Override
	public boolean isFlipped() {
		return false;
	}

	@Override
	public Vector2 getAnimationPosition() {
		return body.getPosition();
	}

	@Override
	public float getAnimationWidth() {
		return 1;
	}

	@Override
	public float getAnimationHeigth() {
		return 1;
	}

	@Override
	public float getRotation() {
		return 0;
	}

}
