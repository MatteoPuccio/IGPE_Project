package com.mygdx.game.model.weapons;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.model.GameModel;
import com.mygdx.game.model.entities.EnemiesHandler;
import com.mygdx.game.model.entities.Enemy;
import com.mygdx.game.model.entities.Entity;

public class MeleeWeapon extends Weapon {
	
	protected float range;
	protected boolean canAttack;
	
	public MeleeWeapon(int damage, float range, float cooldown, Entity owner) {
		super(damage, cooldown, owner);
		this.range = range;
		canAttack = true;
	}

	@Override
	public void attack(float deltaTime) {
		timePassed += deltaTime;
		canAttack = false;
		
		if(timePassed >= cooldown && attacking) {
			timePassed = 0;
			canAttack = true;
			Vector2 weaponPosition = new Vector2(attackPoint);
			weaponPosition.sub(GameModel.getInstance().getCharacter().getPosition()).nor();
			
			if(owner instanceof Enemy) {
				Circle c1 = new Circle(GameModel.getInstance().getCharacter().getPosition(), GameModel.getInstance().getCharacter().getRadius());
				Circle c2 = new Circle(owner.getPosition().mulAdd(weaponPosition, range), range);
				if(Intersector.overlaps(c1, c2))
					GameModel.getInstance().getCharacter().takeDamage(damage);
			}
			else {
				for(Enemy e : EnemiesHandler.getInstance().getEnemies()) {
					Circle c1 = new Circle(e.getPosition(), e.getRadius());
					Circle c2 = new Circle(owner.getPosition().mulAdd(weaponPosition, range), range);
					if(Intersector.overlaps(c1, c2)) {
						e.takeDamage(damage);
					}
				}
			}
		}
	}
	
	public boolean canAttack() {
		return canAttack;
	}
}
