package com.mygdx.game.model.weapons;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.model.Animated;
import com.mygdx.game.model.collisions.Collidable;
import com.mygdx.game.model.collisions.Hole;
import com.mygdx.game.model.entities.Character;
import com.mygdx.game.model.entities.EnemiesHandler;
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
		
		BulletHandler.getInstance().addBullet(new Bullet(this, position, direction));
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

	public final void bulletCollidedWith(Collidable coll, Bullet bullet) {
				
		System.out.println("bullet collided");
		
		if(coll instanceof Enemy) {
			
			if(owner instanceof Character) {
				Enemy temp = (Enemy) coll;
				EnemiesHandler.hitEnemy(temp, damage);
				BulletHandler.getInstance().removeBullet(bullet);
				bulletDestroyedEffect(coll, bullet);
			}
			
		}
		
		else if(coll instanceof Character) {
			
			if(owner instanceof Enemy) {
				Character temp = (Character) coll;
				temp.takeDamage(damage);
				BulletHandler.getInstance().removeBullet(bullet);
				bulletDestroyedEffect(coll, bullet);
			}
				
		}
			
		else if(!(coll instanceof Hole)) {
			BulletHandler.getInstance().removeBullet(bullet);
			bulletDestroyedEffect(coll, bullet);
		}
			
	}
	
	protected void bulletDestroyedEffect(Collidable coll, Bullet bullet) {
		
	}

}
