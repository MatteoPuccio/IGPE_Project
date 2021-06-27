package com.mygdx.game.view.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class ScoreLabel {

	private BitmapFont font;
	private Vector2 position;
	private Texture background;
	private Texture texture;
	
	public ScoreLabel(String texturePath, Vector2 position) {
		this.position = new Vector2(position);
		
		font = new BitmapFont(Gdx.files.internal("UI/a_goblin_appears.fnt"), Gdx.files.internal("UI/a_goblin_appears.png"), false);
		background = new Texture("UI/score_label_background.png");
		texture = new Texture(texturePath);
	}
	
	public void draw(SpriteBatch batchUI, String value) {
		batchUI.draw(background, position.x - 10, position.y - 40, 150, 50);
		batchUI.draw(texture, position.x, position.y - 35, 40, 40);
		font.draw(batchUI, value, position.x + 50, position.y);
	}
	
	public void dispose() {
		texture.dispose();
		background.dispose();
		font.dispose();
	}
}
