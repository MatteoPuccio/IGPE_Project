package com.mygdx.game.model;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.mygdx.game.Settings;

public class MeleeWeapon extends Weapon {
	
	public MeleeWeapon(int damage, float range, float cooldown) {
		super(damage,range, cooldown);
	}

	@Override
	public void attack(float deltaTime) {
		
		for(Entity e : EnemiesHandler.getInstance().getEnemies())
		{
			int flip = 1;
			if(GameModel.getInstance().getCharacter().isFlipped())
				flip = -1;
			Circle c1 = new Circle(e.getPosition(), e.getRadius());
			Circle c2 = new Circle(GameModel.getInstance().getCharacter().getPosition().x + (range*flip),GameModel.getInstance().getCharacter().getPosition().y, GameModel.getInstance().getCharacter().getRadius());
			if(Intersector.overlaps(c1, c2)) {
				e.takeDamage(damage);
				System.out.println("attacco per "+ damage);
			}
		}
	}
}
