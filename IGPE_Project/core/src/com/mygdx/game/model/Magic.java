package com.mygdx.game.model;

import com.badlogic.gdx.math.Vector2;

public class Magic extends Weapon {

	private float speed;
	private float timeSinceLastAttack;
	
	public Magic(int damage, float range, float cooldown, Character holder, float speed) {
		super(damage, range, cooldown, holder);
		this.speed = speed;
		
		timeSinceLastAttack = cooldown;
	}

	@Override
	public void attack(float deltaTime) {
		timeSinceLastAttack += deltaTime;
		if(attacking)
		{
			if(timeSinceLastAttack >= cooldown)
			{
				Vector2 tempAttackPoint = new Vector2(attackPoint);
				BulletHandler.getInstance().addBullet(new Bullet(this, holder.getPosition().add(tempAttackPoint.sub(holder.getPosition()).nor().scl(0.5f)), tempAttackPoint.nor()));
				timeSinceLastAttack = 0;
			}
		}
	}
	
	public float getSpeed() {
		return speed;
	}
}
