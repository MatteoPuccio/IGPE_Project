package com.mygdx.game;

public class Settings {
	public final static float PPM = 16;
	
	public final static int UP = 0;
	public final static int RIGHT = 1;
	public final static int DOWN = 2;
	public final static int LEFT = 3;

	public final static int TITLE_SCREEN = 0;
	public final static int RUNNING = 1;
	public final static int OPTIONS = 2;
	public final static int DEAD = 3;
	
	private static float volume = 0.05f;

	public static float getVolume() {
		return volume;
	}

	public static void setVolume(float volume) {
		Settings.volume = volume;
	}
	
}
