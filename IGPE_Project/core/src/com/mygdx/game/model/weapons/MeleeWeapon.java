package com.mygdx.game.model.weapons;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Settings;
import com.mygdx.game.model.EnemiesHandler;
import com.mygdx.game.model.GameModel;
import com.mygdx.game.model.entities.Entity;

public class MeleeWeapon extends Weapon {
	
	public MeleeWeapon(int damage, float range, float cooldown) {
		super(damage,range, cooldown);
	}

	@Override
	public void attack(float deltaTime) {
		
		for(Entity e : EnemiesHandler.getInstance().getEnemies())
		{
			Vector2 weaponDirection = GameModel.getInstance().getCharacter().getPosition().sub(attackPoint);
			System.out.println(weaponDirection);
			System.out.println(GameModel.getInstance().getCharacter().getPosition());
			Circle c1 = new Circle(e.getPosition(), e.getRadius());
			Circle c2 = new Circle(weaponDirection, range);
			if(Intersector.overlaps(c1, c2)) {
				e.takeDamage(damage);
			}
		}
	}
}
