package com.mygdx.game.view.animations;

import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.model.GameModel;

public class WeaponSlashAnimation 
{
	private TextureRegion texture;
	private Vector2 position;
	private float angle,startingAngle;
	private boolean playing;
	
	public WeaponSlashAnimation() {	//aggiungere tipo di arma
		texture = new TextureRegion(new Texture("weapons/weapon_sword_1.png"));
		position = new Vector2();
		angle = 0;
		startingAngle = 0;
		playing = false;
	}
	
	public void playSwingAnimation(float deltaTime) {
		angle += deltaTime * 1000;
		if(angle >= 90 + startingAngle) 
			stopPlaying();
		position = GameModel.getInstance().getCharacter().getPosition();
	}
	
	private void stopPlaying() {
		playing = false;
	}
	
	public Vector2 getPosition() {
		return position;
	}
	
	public TextureRegion getTexture() {
		return texture;
	}
	
	public float getAngle() {
		return angle;
	}
	
	public void reset() {
		angle = 0;
	}
	
	public boolean isPlaying() {
		return playing;
	}
	
	public void play(float startingAngle) {
		this.startingAngle = startingAngle;
		angle = startingAngle - 45;
		
		playing = true;
	}
	
	public float getStartingAngle() {
		return startingAngle;
	}

}
