package com.mygdx.game.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.GameMain;


public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 1024;
		config.height = 768;
		config.title = "No Way To Go But Down";
		config.addIcon("icon/game-icon-mac.png", Files.FileType.Internal);
		config.addIcon("icon/game-icon-linux.png", Files.FileType.Internal);
		config.addIcon("icon/game-icon-windows.png", Files.FileType.Internal);
		config.fullscreen = true;
		new LwjglApplication(GameMain.getInstance(), config);

	}
}
