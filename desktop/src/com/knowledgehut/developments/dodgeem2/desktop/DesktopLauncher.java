package com.knowledgehut.developments.dodgeem2.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.knowledgehut.developments.dodgeem2.DodgeEm2;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.height = DodgeEm2.HEIGHT;
		config.width = DodgeEm2.WIDTH;
        config.title = "DodgeEm";
		new LwjglApplication(new DodgeEm2(), config);
	}
}
