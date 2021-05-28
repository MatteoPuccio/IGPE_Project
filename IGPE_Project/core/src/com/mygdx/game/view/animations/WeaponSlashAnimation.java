package com.mygdx.game.view.animations;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Settings;
import com.mygdx.game.model.GameModel;

public class WeaponSlashAnimation 
{
	private TextureRegion texture;
	private Vector2 position;
	private float angle;
	public WeaponSlashAnimation()	//aggiungere tipo di arma
	{
		texture = new TextureRegion(new Texture("weapon_sword_1.png"));
		position = new Vector2();
		angle = 0;
	}
	public void playSwingAnimation(float deltaTime)
	{
		angle += deltaTime * 200;
		if(angle >= 90)
			angle = 0;
		position = GameModel.getInstance().getCharacter().getPosition();
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
	
}
