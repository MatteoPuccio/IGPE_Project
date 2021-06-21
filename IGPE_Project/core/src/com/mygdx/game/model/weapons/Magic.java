package com.mygdx.game.model.weapons;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.model.Animated;
import com.mygdx.game.model.BulletHandler;
import com.mygdx.game.model.entities.Enemy;
import com.mygdx.game.model.entities.Entity;

public abstract class Magic extends Weapon implements Animated {

	protected float speed;
	protected float bulletSize;
	private float timeSinceLastAttack;
	private static int manaCapacity = 10;
	protected float currentMana;
	
	public Magic(int damage, float range, float cooldown, float speed, float bulletSize, Entity owner) {
		super(damage, range, cooldown, owner);
		this.speed = speed;
		this.bulletSize = bulletSize;
		
		currentMana = (float)manaCapacity;
		timeSinceLastAttack = cooldown;
	}

	@Override
	public void attack(float deltaTime) {
		timeSinceLastAttack += deltaTime;
		if(attacking && currentMana > 1.0f)
		{
			if(timeSinceLastAttack >= cooldown)
			{
				currentMana--;
				BulletHandler.getInstance().addBullet(createBullet());
				timeSinceLastAttack = 0;
			}
		}
	}
	
	private Bullet createBullet() {
		Vector2 position = new Vector2(owner.getPosition());
		Vector2 tempAttackPoint = new Vector2(attackPoint);
		String bulletUserData = "character bullet";
		if(owner instanceof Enemy)
			bulletUserData = "enemy bullet";
		
		return new Bullet(this, position.add(tempAttackPoint.sub(position).nor().scl(0.5f)), tempAttackPoint.nor(), bulletUserData);
	}
	
	public float getSpeed() {
		return speed;
	}
	
	public float getBulletSize() {
		return bulletSize;
	}
	
	public void rechargeMana(float deltaTime) {
		if(currentMana + deltaTime <= (float)(manaCapacity))
			currentMana += deltaTime;
		else
			currentMana = manaCapacity;
	}
	
	public float getPercentage() {
		return (float)(currentMana/manaCapacity);
	}

}
