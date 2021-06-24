package com.mygdx.game.view.audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.ObjectMap;
import com.mygdx.game.constants.SoundConstants;

public class Sounds {
	private static Sounds instance = null;
	private ObjectMap<Integer, Sound> sounds;

	public Sounds() {
		sounds = new ObjectMap<Integer,Sound>();
		
		sounds.put(SoundConstants.MENU_CONFIRM, Gdx.audio.newSound(Gdx.files.internal("sounds/menu/confirm_style_2_001.ogg")));
		sounds.put(SoundConstants.MENU_BACK, Gdx.audio.newSound(Gdx.files.internal("sounds/menu/back_style_2_001.ogg")));
		sounds.put(SoundConstants.MENU_ERROR, Gdx.audio.newSound(Gdx.files.internal("sounds/menu/error_style_2_001.ogg")));
		sounds.put(SoundConstants.HIT, Gdx.audio.newSound(Gdx.files.internal("sounds/sfx/hurt_020.wav")));
		sounds.put(SoundConstants.PICKUP, Gdx.audio.newSound(Gdx.files.internal("sounds/sfx/retro_pickUp_18.wav")));
	}
	
	public static Sounds getInstance() {
		if (instance == null)
			instance = new Sounds();
		return instance;
	}
	
	public void update() {
		while (!SoundHandler.getInstance().getQueue().isEmpty()) {
			sounds.get(SoundHandler.getInstance().getQueue().pop()).play();
		}
	}
	
	public void dispose() {
		for (Integer i: sounds.keys()) {
			sounds.get(i).dispose();
		}
	}
}
