package com.mygdx.game.model.weapons;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.model.BulletHandler;
import com.mygdx.game.model.entities.Enemy;
import com.mygdx.game.model.entities.Entity;

public class FireMagic extends Magic {

	public FireMagic(Entity owner) {
		super(1, 0.5f, 10, 0.1f, 2, owner);
	}
	
	@Override
	protected void createBullet() {
		Vector2 position = new Vector2(owner.getPosition());
		Vector2 direction = new Vector2(attackPoint);
		direction.sub(position);
		direction.nor();
		String bulletUserData = "character bullet";
		if(owner instanceof Enemy)
			bulletUserData = "enemy bullet";
		
		BulletHandler.getInstance().addBullet(new Bullet(this, position, direction, bulletUserData));
		
		Vector2 leftDirection = new Vector2(direction);
		Vector2 rightDirection = new Vector2(direction);
		
		leftDirection.rotateDeg(20);
		rightDirection.rotateDeg(-20);
		
		BulletHandler.getInstance().addBullet(new Bullet(this, position, leftDirection, bulletUserData));
		BulletHandler.getInstance().addBullet(new Bullet(this, position, rightDirection, bulletUserData));

	}

	@Override
	public String getCurrentAnimationString() {
		return "fireball animation";
	}

	@Override
	public boolean isFlipped() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Vector2 getAnimationPosition() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public float getAnimationWidth() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getAnimationHeigth() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getRotation() {
		return 0;
	}

}
