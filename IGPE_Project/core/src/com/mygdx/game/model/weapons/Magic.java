package com.mygdx.game.model.weapons;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.model.GameModel;
import com.mygdx.game.model.handlers.BulletHandler;

public class Magic extends Weapon {

	private float speed;
	private float timeSinceLastAttack;
	private static int manaCapacity = 100;
	private float currentMana;
	
	
	public Magic(int damage, float range, float cooldown, float speed) {
		super(damage, range, cooldown);
		this.speed = speed;
		
		currentMana = (float)manaCapacity;
		timeSinceLastAttack = cooldown;
	}

	@Override
	public void attack(float deltaTime) {
		timeSinceLastAttack += deltaTime;
		Vector2 position = GameModel.getInstance().getCharacter().getPosition();
		if(attacking && currentMana > 1.0f)
		{
			if(timeSinceLastAttack >= cooldown)
			{
				currentMana--;
				Vector2 tempAttackPoint = new Vector2(attackPoint);
				BulletHandler.getInstance().addBullet(new Bullet(this, position.add(tempAttackPoint.sub(position).nor().scl(0.5f)), tempAttackPoint.nor()));
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
