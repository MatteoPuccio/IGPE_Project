package com.mygdx.game.model;

import com.badlogic.gdx.math.Vector2;

public class Magic extends Weapon {

	private float speed;
	private float timeSinceLastAttack;
	private static int manaCapacity = 10;
	private float currentMana;
	
	
	public Magic(int damage, float range, float cooldown, Character holder, float speed) {
		super(damage, range, cooldown, holder);
		this.speed = speed;
		
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
				Vector2 tempAttackPoint = new Vector2(attackPoint);
				BulletHandler.getInstance().addBullet(new Bullet(this, holder.getPosition().add(tempAttackPoint.sub(holder.getPosition()).nor().scl(0.5f)), tempAttackPoint.nor()));
				timeSinceLastAttack = 0;
			}
		}
	}
	
	public float getSpeed() {
		return speed;
	}
	
	public void rechargeMana(float deltaTime)
	{
		if(currentMana + deltaTime <= (float)(manaCapacity))
			currentMana += deltaTime;
		else
			currentMana = manaCapacity;
	}
	
	public float getPercentage()
	{
		return (float)(currentMana/manaCapacity);
	}
}
