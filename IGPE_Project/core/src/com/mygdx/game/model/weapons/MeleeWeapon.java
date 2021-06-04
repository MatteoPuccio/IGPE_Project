package com.mygdx.game.model.weapons;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.model.GameModel;
import com.mygdx.game.model.entities.EnemiesHandler;
import com.mygdx.game.model.entities.Entity;

public class MeleeWeapon extends Weapon {
	
	public MeleeWeapon(int damage, float range, float cooldown) {
		super(damage,range, cooldown);
	}

	@Override
	public void attack(float deltaTime) {
		Vector2 weaponPosition = GameModel.getInstance().getCharacter().getWeapon().getAttackPoint().sub(GameModel.getInstance().getCharacter().getPosition()).nor();
		for(Entity e : EnemiesHandler.getInstance().getEnemies()) {
			Circle c1 = new Circle(e.getPosition(), e.getRadius());
			Circle c2 = new Circle(GameModel.getInstance().getCharacter().getPosition().mulAdd(weaponPosition, range), range);
			if(Intersector.overlaps(c1, c2)) {
				e.takeDamage(damage);
				System.out.println("attacco per "+ damage);
			}
		}
	}
}
