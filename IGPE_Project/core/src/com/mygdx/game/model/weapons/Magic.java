package com.mygdx.game.model.weapons;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.model.Animated;
import com.mygdx.game.model.BulletHandler;
import com.mygdx.game.model.entities.Enemy;
import com.mygdx.game.model.entities.Entity;

public abstract class Magic implements Animated {

	private float speed;
	private float bulletSize;
	private float timeSinceLastAttack;
	
	protected Entity owner;
	
	protected Vector2 attackPoint;
	
	protected boolean attacking;
	
	protected int damage;
	protected float cooldown;
	protected float timePassed;
	 
	private int bulletCost;
	
	public Magic(int damage, float cooldown, float speed, float bulletSize, int bulletCost, Entity owner) {
		this.speed = speed;
		this.bulletSize = bulletSize;
		this.bulletCost = bulletCost;
		
		this.damage = damage;
		this.cooldown = cooldown;
		this.owner = owner;
		
		this.timePassed = cooldown;
		
		attacking = false;
		
		timeSinceLastAttack = cooldown;
	}

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
	
	public int getDamage() {
		return damage;
	}
	
	public float getCooldown() {
		return cooldown;
	}
	
	public void setAttackPoint(Vector2 attackPoint) {
		this.attackPoint = attackPoint;
	}
	
	public Vector2 getAttackPoint() {
		return attackPoint;
	}
	
	public void setAttacking(boolean attacking) {
		this.attacking = attacking;
	}
	
	public boolean isAttacking() {
		return attacking;
	}

}
