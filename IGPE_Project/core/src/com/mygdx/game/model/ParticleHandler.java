package com.mygdx.game.model;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class ParticleHandler {

	private static ParticleHandler instance = null;
	
	public class Particle {
		
		Vector2 position;
		int particleId;
		
		float width;
		float heigth;
		
		public Particle(Vector2 position, int particleId, float width, float heigth) {
			this.position = new Vector2(position);
			this.particleId = particleId;
			this.width = width;
			this.heigth = heigth;
		}
		
		public Vector2 getPosition() {
			return position;
		}
		
		public int getParticleId() {
			return particleId;
		}
		
		public float getWidth() {
			return width;
		}
		
		public float getHeigth() {
			return heigth;
		}
	}
	
	private Array<Particle> particles;
	
	private ParticleHandler() {
		particles = new Array<Particle>();
	}
	
	public static ParticleHandler getInstance() {
		if(instance == null)
			instance = new ParticleHandler();
		return instance;
	}
	
	public void addParticle(Vector2 position, int particleId, float width, float heigth) {
		particles.add(new Particle(position, particleId, width, heigth));
	}
	
	public Array<Particle> getParticles() {
		return particles;
	}
	
}
