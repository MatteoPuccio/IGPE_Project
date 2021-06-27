package com.mygdx.game.view.audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.ObjectMap;
import com.mygdx.game.constants.Settings;
import com.mygdx.game.constants.SoundConstants;
import com.mygdx.game.controller.SoundHandler;

/*	Sounds è una classe singleton che conserva le istanze di tutti gli effetti sonori del gioco. 
 * 	Collabora con SoundHandler per gestire la riproduzione dei suoni nei vari momenti del gioco 
 *  e usa SoundConstants per trovare i vari riferimenti ai suoni. 
 */
public class Sounds {
	private static Sounds instance = null;
//	I suoni sono conservati in una HashMap, le cui chiavi sono valori di SoundsConstants
	private ObjectMap<Integer, Sound> sounds;
	private Music caveAmbience;

	private Sounds() {
		sounds = new ObjectMap<Integer,Sound>();
		
		sounds.put(SoundConstants.MENU_CONFIRM, Gdx.audio.newSound(Gdx.files.internal("sounds/menu/confirm_style_2_001.ogg")));
		sounds.put(SoundConstants.MENU_BACK, Gdx.audio.newSound(Gdx.files.internal("sounds/menu/back_style_2_001.ogg")));
		sounds.put(SoundConstants.MENU_ERROR, Gdx.audio.newSound(Gdx.files.internal("sounds/menu/error_style_2_001.ogg")));
		sounds.put(SoundConstants.HIT, Gdx.audio.newSound(Gdx.files.internal("sounds/sfx/hurt_020.wav")));
		sounds.put(SoundConstants.PICKUP, Gdx.audio.newSound(Gdx.files.internal("sounds/sfx/retro_pickUp_18.wav")));
		sounds.put(SoundConstants.EXPLOSION, Gdx.audio.newSound(Gdx.files.internal("sounds/sfx/explosion_03.wav")));
		sounds.put(SoundConstants.PLAYER_HIT, Gdx.audio.newSound(Gdx.files.internal("sounds/sfx/hurt_185.wav")));
		sounds.put(SoundConstants.STEP, Gdx.audio.newSound(Gdx.files.internal("sounds/sfx/feet_49.wav")));
		sounds.put(SoundConstants.COINS, Gdx.audio.newSound(Gdx.files.internal("sounds/sfx/coins.wav")));
		sounds.put(SoundConstants.HEALTH_POTION, Gdx.audio.newSound(Gdx.files.internal("sounds/sfx/health_potion.ogg")));
		sounds.put(SoundConstants.POWERUP, Gdx.audio.newSound(Gdx.files.internal("sounds/sfx/powerup.wav")));
		sounds.put(SoundConstants.MAGIC_PICKUP, Gdx.audio.newSound(Gdx.files.internal("sounds/sfx/magic_pickup.wav")));
		
		caveAmbience = Gdx.audio.newMusic(Gdx.files.internal("sounds/sfx/cave_ambience.mp3"));
		caveAmbience.setLooping(true);
	}
	
	public static Sounds getInstance() {
		if (instance == null)
			instance = new Sounds();
		return instance;
	}
	
	public void playMusic() {
		caveAmbience.setVolume(Settings.getMusicVolume());
		caveAmbience.play();
	}
	
	public void pauseMusic() {
		caveAmbience.pause();
	}
	
	public void stopMusic() {
		caveAmbience.stop();
	}
	
//	Riproduce i suoni corrispondenti alle chiavi ricevute dalla queue di SoundHandler
	public void update() {
		while (!SoundHandler.getInstance().getQueue().isEmpty()) {
			sounds.get(SoundHandler.getInstance().getQueue().pop()).play(Settings.getVolume());
		}
	}
	
	public void dispose() {
		for (Integer i: sounds.keys()) {
			sounds.get(i).dispose();
		}
		caveAmbience.dispose();
	}
}
