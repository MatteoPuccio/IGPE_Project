package com.mygdx.game.constants;

public class Settings {
	//Pixel per metro, unit� di misura delle celle nel mondo
	public final static float PPM = 16;
	
	public final static int UP = 0;
	public final static int RIGHT = 1;
	public final static int DOWN = 2;
	public final static int LEFT = 3;

	public final static int EASY = 0;
	public final static int NORMAL = 1;
	public final static int HARD = 2;
	
	public final static int WINDOWED = 4;
	public final static int FULLSCREEN = 5;
	
	private static float volume = 0.5f;
	private static float musicVolume = 0.5f;
	private static int difficulty = NORMAL;
	
	//state � inizialmente windowed cos� che il change listener di OptionScreen rilevi il change e metta fullscreen
	private static int displayState = FULLSCREEN;
	
	public static float getVolume() {
		return volume;
	}
	
	public static float getMusicVolume() {
		return musicVolume;
	}

	public static void setVolume(float volume) {
		Settings.volume = volume;
	}
	
	public static void setMusicVolume(float volume) {
		Settings.musicVolume = volume;
	}

	public static void setDifficulty(int selectedIndex) {
		Settings.difficulty = selectedIndex;
	}
	
	public static int getDifficulty() {
		return difficulty;
	}
	
	public static int getDisplayState() {
		return displayState;
	}
	
	public static void setDisplayState(int displayState) {
		Settings.displayState = displayState;
	}
}
