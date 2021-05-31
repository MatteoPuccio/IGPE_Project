package com.mygdx.game.model.entities;

import com.badlogic.gdx.math.Vector2;
<<<<<<< HEAD
=======
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.model.EnemiesHandler;
>>>>>>> branch 'main' of https://github.com/MatteoPuccio/IGPE_Project.git
import com.mygdx.game.model.GameModel;

public abstract class Enemy extends Entity {

	public Enemy(Vector2 position, float radius) {
		super(position, radius);
		body.setUserData("enemy");
	}
	
	@Override
	public void takeDamage(float damage) {
		health-=damage;
		if(health <= 0)
		{
			GameModel.getInstance().addBodyToDispose(body);
			EnemiesHandler.getInstance().removeEnemy(this);
		}
	}

}
