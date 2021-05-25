package com.mygdx.game.model;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;

public class MeleeWeapon extends Weapon {
	
	public MeleeWeapon(int damage, float range, float cooldown) {
		super(damage,range, cooldown);
	}

	@Override
	public void attack(float deltaTime) {
		
		for(Entity e : EnemiesHandler.getInstance().getEnemies())
		{
			Circle c1 = new Circle(e.getPosition(), e.getRadius());
			Circle c2 = new Circle(GameModel.getInstance().getCharacter().getPosition(), GameModel.getInstance().getCharacter().getRadius());
			if(Intersector.overlaps(c1, c2))
				e.takeDamage(damage);
		}
	}
	
}
