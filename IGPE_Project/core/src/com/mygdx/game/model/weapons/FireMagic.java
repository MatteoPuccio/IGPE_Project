package com.mygdx.game.model.weapons;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.constants.AnimationConstants;
import com.mygdx.game.model.entities.Entity;

public class FireMagic extends Magic {

	private float bulletSpreadAngle = 10;
	
	public FireMagic(Entity owner) {
		super(1, 0.5f, 10, 0.1f, 4, owner);
	}
	
	@Override
	protected void createBullet() {
		Vector2 position = new Vector2(owner.getPosition());
		Vector2 direction = new Vector2(attackPoint);
		direction.sub(position);
		direction.nor();
		
		BulletHandler.getInstance().addBullet(new Bullet(this, position, direction));
		
		Vector2 leftDirection = new Vector2(direction);
		Vector2 rightDirection = new Vector2(direction);
		
		leftDirection.rotateDeg(10);
		rightDirection.rotateDeg(-10);
		
		BulletHandler.getInstance().addBullet(new Bullet(this, position, leftDirection));
		BulletHandler.getInstance().addBullet(new Bullet(this, position, rightDirection));

	}

	@Override
	public int getBulletAnimationId() {
		return AnimationConstants.FIREBALL_ANIMATION;
	}

}
