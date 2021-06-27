package com.mygdx.game.model.entities;

import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.ai.steer.SteeringAcceleration;
import com.badlogic.gdx.ai.steer.SteeringBehavior;
import com.badlogic.gdx.ai.utils.Location;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.mygdx.game.model.GameModel;
import com.mygdx.game.model.ai.SteeringUtils;
import com.mygdx.game.model.collisions.Collidable;
import com.mygdx.game.view.animations.Animated;

//Un entity � qualsiasi oggetto rappresenti un essere vivente
public abstract class Entity implements Animated, Steerable<Vector2>, Collidable {

	private Vector2 direction;
	
	private float maxHealth;
	protected float currentHealth;

	private Body body;
	private float radius;
	
	private int manaPool;
	protected float currentMana;
	protected float manaRechargeMultiplier;
	
	protected boolean flippedX;
	
	private float maxLinearSpeed, maxLinearAcceleration;
	
	protected SteeringBehavior<Vector2> behavior;
	private SteeringAcceleration<Vector2> steerOutput;
	
	private boolean tagged;
	
	public Entity(Vector2 position, float radius, boolean isSensor, int manaPool, int maxHealth, float manaRechargeMultiplier) {
		this.radius = radius;
		
		this.maxHealth = maxHealth;
		this.currentHealth = maxHealth;
		
		this.manaPool = manaPool;
		currentMana = manaPool;

		this.manaRechargeMultiplier = manaRechargeMultiplier;
		
		body = createBody(position, isSensor);
		direction = new Vector2(0,0);
		flippedX = false;
		
		maxLinearSpeed = 7;
		maxLinearAcceleration = 100;
		
		tagged = false;
		
		steerOutput = new SteeringAcceleration<Vector2>(new Vector2());
	}
	
	private Body createBody(Vector2 position, boolean isSensor) {
		BodyDef bDef = new BodyDef();
		bDef.type = BodyType.DynamicBody;
		bDef.position.set(position);
		bDef.fixedRotation = true;
		
		Body b = GameModel.getInstance().getWorld().createBody(bDef);
		
		FixtureDef fDef = new FixtureDef();
		CircleShape circle = new CircleShape();
		circle.setRadius(radius);
		fDef.shape = circle;
		fDef.density = 1;
		fDef.isSensor = isSensor;
		
		b.createFixture(fDef);
		b.setUserData(this);
		
		circle.dispose();
		return b;
	}
	
	public void update(float deltaTime) {
		if(behavior != null) {
			behavior.calculateSteering(steerOutput);
			applySteering(deltaTime);
		}
		
		rechargeMana(deltaTime);
	}
	
	private void applySteering(float deltaTime) {
		
		if(!steerOutput.linear.isZero()) {
			Vector2 force = steerOutput.linear.scl(deltaTime);
			body.applyForceToCenter(force, true);
	
			//Impone il cap sulla velocit�
			Vector2 velocity = body.getLinearVelocity();
			float currentSpeedSquare = velocity.len2();
			if(currentSpeedSquare > maxLinearSpeed * maxLinearSpeed)
				body.setLinearVelocity(velocity.scl(maxLinearSpeed / (float) Math.sqrt(currentSpeedSquare)));
		}
	}
	
	public void useMana(float usedMana) {
		currentMana -= usedMana;
	}
	
	private void rechargeMana(float deltaTime) {
		if(currentMana + deltaTime * manaRechargeMultiplier <= manaPool)
			currentMana += deltaTime * manaRechargeMultiplier;
		else
			currentMana = manaPool;
	}
	
	public Vector2 getDirection() {
		return direction;
	}
	
	public float getMaxHealth() {
		return maxHealth;
	}
	
	public Vector2 getPosition() {
		return body.getPosition();
	}
	
	public Body getBody() {
		return body;
	}
	
	public float getRadius() {
		return radius;
	}
	
	public int getManaPool() {
		return manaPool;
	}
	
	public float getCurrentMana() {
		return currentMana;
	}
	
	
	public float getManaPercentage() {
		return (float)(currentMana / manaPool);
	}
	
	public float getHealthPercentage() {
		return (float) (currentHealth / maxHealth);
	}
	
	public void setManaRechargeMultiplier(float manaRechargeMultiplier) {
		this.manaRechargeMultiplier = manaRechargeMultiplier;
	}
	
	public abstract void takeDamage(float damage);

	@Override
	public float vectorToAngle(Vector2 vector) {
		return SteeringUtils.vectorToAngle(vector);
	}

	@Override
	public Vector2 angleToVector(Vector2 outVector, float angle) {
		return SteeringUtils.angleToVector(outVector, angle);
	}

	@Override
	public float getMaxLinearSpeed() {
		return maxLinearSpeed;
	}

	@Override
	public void setMaxLinearSpeed(float maxLinearSpeed) {
		this.maxLinearSpeed = maxLinearSpeed;
	}

	@Override
	public float getMaxLinearAcceleration() {
		return maxLinearAcceleration;
	}

	@Override
	public void setMaxLinearAcceleration(float maxLinearAcceleration) {
		this.maxLinearAcceleration = maxLinearAcceleration;
	}

	@Override
	public Vector2 getLinearVelocity() {
		return body.getLinearVelocity();
	}

	@Override
	public float getBoundingRadius() {
		return radius;
	}

	@Override
	public boolean isTagged() {
		return tagged;
	}

	@Override
	public void setTagged(boolean tagged) {
		this.tagged = tagged;
	}
	
	@Override
	public float getOrientation() {return 0;}

	@Override
	public void setOrientation(float orientation) {}
	
	@Override
	public float getAngularVelocity() {return 0;}
	@Override
	public float getMaxAngularSpeed() {return 0;}

	@Override
	public void setMaxAngularSpeed(float maxAngularSpeed) {}

	@Override
	public float getMaxAngularAcceleration() {return 0;}

	@Override
	public void setMaxAngularAcceleration(float maxAngularAcceleration) {}
	
	@Override
	public Location<Vector2> newLocation() {return null;}

	@Override
	public float getZeroLinearSpeedThreshold() {return 0;}

	@Override
	public void setZeroLinearSpeedThreshold(float value) {}
}
