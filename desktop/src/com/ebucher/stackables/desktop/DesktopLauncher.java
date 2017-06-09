package com.ebucher.stackables.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.ebucher.stackables.StackablesMain;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config.width = 540;
		config.height = 980;

		new LwjglApplication(new StackablesMain(), config);
	}
}
