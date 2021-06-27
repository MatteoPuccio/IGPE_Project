package com.mygdx.game.controller;

import com.badlogic.gdx.utils.Array;

public class SoundHandler {
	
	private static SoundHandler instance = null;
	
	//Una coda di suoni (ci si aspettano costanti di SoundConstants) che viene gestita nella classe Sounds
	private Array<Integer> soundQueue;
	
	private SoundHandler() {
		soundQueue = new Array<Integer>();
	}
	
	public static SoundHandler getInstance() {
		if (instance == null)
			instance = new SoundHandler();
		return instance;
	}
	
	public void addSoundToQueue(int s) {
		soundQueue.add(s);
	}
	
	public Array<Integer> getQueue() {
		return soundQueue;
	}
	
	public void clear() {
		soundQueue.clear();
	}
}
