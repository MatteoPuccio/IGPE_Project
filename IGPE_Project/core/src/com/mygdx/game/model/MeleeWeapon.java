package com.mygdx.game.model;

public class MeleeWeapon extends Weapon {
	
	public MeleeWeapon(int damage, float range, float cooldown, Character holder, float hitCooldown) {
		super(damage,range, cooldown, holder);
	}

	@Override
	public void attack(float deltaTime) {
//		for(Entity e : EnemiesHandler.getInstance().getEnemies())
//		{
//			Circle c1 = new Circle(e.getPosition(), e.());
//			Circle c2 = new Circle(holder.getPosition().add(holder.getDirection().nor()), hitRange);
//			if(Intersector.overlaps(c1, c2))
//				e.takeDamage(damage);
//		}
	}
	
}
