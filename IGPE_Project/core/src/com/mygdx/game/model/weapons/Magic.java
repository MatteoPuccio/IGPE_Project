package com.mygdx.game.model.weapons;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.model.Animated;
import com.mygdx.game.model.BulletHandler;
import com.mygdx.game.model.entities.Enemy;
import com.mygdx.game.model.entities.Entity;

public abstract class Magic extends Weapon implements Animated {

	private float speed;
	private float bulletSize;
	private float timeSinceLastAttack;
	 
	private int bulletCost;
	
	public Magic(int damage, float cooldown, float speed, float bulletSize, int bulletCost, Entity owner) {
		super(damage, cooldown, owner);
		this.speed = speed;
		this.bulletSize = bulletSize;
		this.bulletCost = bulletCost;
		
		timeSinceLastAttack = cooldown;
	}

	@Override
	public void attack(float deltaTime) {
		timeSinceLastAttack += deltaTime;
		if(attacking && owner.getCurrentMana() > bulletCost)
		{
			if(timeSinceLastAttack >= cooldown)
			{
				owner.useMana(bulletCost);
				createBullet();
				timeSinceLastAttack = 0;
			}
		}
	}
	
	protected void createBullet() {
		Vector2 position = new Vector2(owner.getPosition());
		Vector2 direction = new Vector2(attackPoint);
		direction.sub(position);
		direction.nor();
		String bulletUserData = "character bullet";
		if(owner instanceof Enemy)
			bulletUserData = "enemy bullet";
		
		BulletHandler.getInstance().addBullet(new Bullet(this, position, direction, bulletUserData));
	}
	
	public float getSpeed() {
		return speed;
	}
	
	public float getBulletSize() {
		return bulletSize;
	}

}
