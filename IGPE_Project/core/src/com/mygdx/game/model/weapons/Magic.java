package com.mygdx.game.model.weapons;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.model.collisions.Collidable;
import com.mygdx.game.model.collisions.Gate;
import com.mygdx.game.model.collisions.Hole;
import com.mygdx.game.model.entities.Character;
import com.mygdx.game.model.entities.EnemiesHandler;
import com.mygdx.game.model.entities.Enemy;
import com.mygdx.game.model.entities.Entity;
import com.mygdx.game.model.pickups.Pickup;

public abstract class Magic {

	private float speed;
	private float bulletSize;
	private float timeSinceLastAttack;
	
	protected Entity owner;
	
	protected Vector2 attackPoint;
	
	protected boolean attacking;
	
	protected float damage;
	protected float cooldown;
	protected float cooldownMultiplier;
	protected float timePassed;
	 
	private float bulletCost;
	
	public Magic(float damage, float cooldown, float speed, float bulletSize, float bulletCost, Entity owner) {
		this.speed = speed;
		this.bulletSize = bulletSize;
		this.bulletCost = bulletCost;
		
		this.damage = damage;
		this.cooldown = cooldown;
		this.owner = owner;
		
		this.timePassed = cooldown;
		
		attacking = false;
		
		timeSinceLastAttack = cooldown;
		
		cooldownMultiplier = 1f;
	}
	
	public void update(float deltaTime) {
		timeSinceLastAttack += deltaTime;
		attack(deltaTime);
	}

	private void attack(float deltaTime) {
		if(attacking && owner.getCurrentMana() >= bulletCost)
		{
			if(timeSinceLastAttack >= cooldown * cooldownMultiplier)
			{
				owner.useMana(bulletCost);
				createBullet();
				timeSinceLastAttack = 0;
			}
		}
	}
	
	protected void createBullet() {
		//Calcola la direzione in cui si dirige il proiettile
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
	
	public void setCooldownMultiplier(float multiplier) {
		cooldownMultiplier = multiplier;
	}
	
	public void setAttackPoint(Vector2 attackPoint) {
		this.attackPoint = attackPoint;
	}
	
	public void setAttacking(boolean attacking) {
		this.attacking = attacking;
	}
	
	public Entity getOwner() {
		return owner;
	}

	//Il metodo chiamato da Bullet quando collide con qualcosa
	//Permette di ridefinire il comportamento per ogni magia
	public final void bulletCollidedWith(Collidable coll, Bullet bullet) {
		
		
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
		
		//I proiettili passano sopra agli Hole, Gate, Pickup e altri proiettili se sono stati sparati dallo stesso tipo di Entity
		//Se due proiettili sparati da tipi di Entities diversi collidono vengono invece entrambi distrutti
		else if(!(coll instanceof Hole) && !(coll instanceof Gate) && !sameOwner(coll) && !(coll instanceof Pickup)) {
			BulletHandler.getInstance().removeBullet(bullet);
			bulletDestroyedEffect(coll, bullet);
		}
			
	}
	
	private boolean sameOwner(Collidable coll) {
		
		if(coll instanceof Bullet) {
			
			Bullet temp = (Bullet) coll;
			return(temp.getParent().getOwner() instanceof Character && owner instanceof Character) || (temp.getParent().getOwner() instanceof Enemy && owner instanceof Enemy);	
		}
		
		return false;
	}
	
	//Permette di definire un comportamento speciale che possono avere i Bullet quando generati da questa magia
	protected void bulletDestroyedEffect(Collidable coll, Bullet bullet) {}
	
	public abstract int getBulletAnimationId();
	
	public abstract int getRespectivePickupAnimationId();

}
