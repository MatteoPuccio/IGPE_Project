package com.mygdx.game.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;

public class MeleeWeapon {
	
	private Entity holder;
	private float damage;
	private float hitRange;
	private float hitCooldown;
	public float rotation;
	private boolean hitting;
	public Sprite sprite;
	
	public MeleeWeapon(float damage, float hitRange, float hitCooldown, Entity holder) {
		sprite = new Sprite(new Texture("weapon_sword_2.png"));
		rotation = -90f;
		hitting = false;
		this.damage = damage;
		this.hitRange = hitRange;
		this.hitCooldown = hitCooldown;
		this.holder = holder;
	}
	
	public float getDamage() {
		return damage;
	}
	
	public float getHitRange() {
		return hitRange;
	}
	
	public float getHitCooldown() {
		return hitCooldown;
	}

	public void update(float deltaTime)
	{
		sprite.setX(holder.getPosition().x);
		sprite.setY(holder.getPosition().y);
		if(hitting)
		{
			if(rotation >  90f)
				rotation = -90f;
			rotation += (deltaTime * 400);
			Circle c1,c2;
			for(Entity e : EnemiesHandler.getInstance().getEnemies())
			{
				c1 = new Circle(e.getPosition().x, e.getPosition().y,e.getRadius());
				c2 = new Circle(sprite.getX(),sprite.getY(), hitRange);
				if(Intersector.overlaps(c1, c2))
					e.takeDamage(damage);
			}
		}
	}

	public void startHitting() {
		hitting = true;
		rotation = -90f;
		
	}
	
	public void stopHitting()
	{
		hitting = false;
		rotation = -90f;
		sprite.setAlpha(0f);
	}
}
