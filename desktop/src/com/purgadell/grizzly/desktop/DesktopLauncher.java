package com.purgadell.grizzly.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.purgadell.grizzly.PearlGame;
import com.purgadell.grizzly.Resources.Variables;

		public class DesktopLauncher {
			public static void main (String[] arg) {
				LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = Variables.V_SCREEN_WIDTH;
		config.height = Variables.V_SCREEN_HEIHGT;
		new LwjglApplication(new PearlGame(), config);
	}
}
