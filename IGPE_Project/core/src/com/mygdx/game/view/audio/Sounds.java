package com.mygdx.game.view.audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class Sounds {
	public Sound menu_confirm, menu_back, menu_error;
	public Sound fire, sword, pickup;

	public Sounds() {
		menu_confirm = Gdx.audio.newSound(Gdx.files.internal("sounds/menu/back_style_2_001.ogg"));
		menu_back = Gdx.audio.newSound(Gdx.files.internal("sounds/menu/confirm_style_2_001.ogg"));
		menu_error = Gdx.audio.newSound(Gdx.files.internal("sounds/menu/error_style_2_001.ogg"));
		
		fire = Gdx.audio.newSound(Gdx.files.internal("sounds/fire.wav"));
		sword = Gdx.audio.newSound(Gdx.files.internal("sounds/sword.wav"));
		pickup = Gdx.audio.newSound(Gdx.files.internal("sounds/pickup.wav"));
	}
	
	public void dispose() {
		menu_confirm.dispose();
		menu_back.dispose();
		menu_error.dispose();
		fire.dispose();
		sword.dispose();
		pickup.dispose();
	}
}
