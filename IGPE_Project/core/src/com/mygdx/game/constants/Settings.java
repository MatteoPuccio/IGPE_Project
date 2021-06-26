package com.mygdx.game.constants;

public class Settings {
	public final static float PPM = 16;
	
	public final static int UP = 0;
	public final static int RIGHT = 1;
	public final static int DOWN = 2;
	public final static int LEFT = 3;
	
	public final static int EASY = 0;
	public final static int NORMAL = 1;
	public final static int HARD = 2;
	
	private static float volume = 0.5f;
	private static int difficulty = NORMAL;
	
	public static float getVolume() {
		return volume;
	}

	public static void setVolume(float volume) {
		Settings.volume = volume;
	}

	public static void setDifficulty(int selectedIndex) {
		Settings.difficulty = selectedIndex;
	}
	
	public static int getDifficulty() {
		return difficulty;
	}
	
}
