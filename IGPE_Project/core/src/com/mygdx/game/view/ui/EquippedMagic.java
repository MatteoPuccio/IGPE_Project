package com.mygdx.game.view.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

//Le box dell'interfaccia contenenti le immagini delle magie equipaggiate
public class EquippedMagic {

	private Texture background;
	Vector2 position;
	
	public EquippedMagic(Vector2 position) {
		background = new Texture("UI/equipped_box_background.png");
		this.position = new Vector2(position);
	}
	
	public void draw(Texture magicTexture, SpriteBatch batch) {
		batch.draw(background, position.x, position.y);
		
		if(magicTexture != null)
			batch.draw(magicTexture, position.x, position.y);
	}
	
	public void draw(SpriteBatch batch) {
		draw(null, batch);
	}
	
	public void dispose() {
		background.dispose();
	}
	
}
