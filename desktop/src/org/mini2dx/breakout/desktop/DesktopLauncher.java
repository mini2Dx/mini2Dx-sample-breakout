package org.mini2dx.breakout.desktop;

import com.badlogic.gdx.backends.lwjgl.DesktopMini2DxGame;
import org.mini2Dx.libgdx.desktop.DesktopMini2DxConfig;
import org.mini2dx.breakout.BreakoutGame;
import org.mini2dx.breakout.Startup;

public class DesktopLauncher {
	public static void main (String[] arg) {
		DesktopMini2DxConfig config = new DesktopMini2DxConfig(Startup.GAME_IDENTIFIER);
		config.vSyncEnabled = true;
		config.width = (int)BreakoutGame.gameWidth;
		config.height = (int)BreakoutGame.gameHeight;
		new DesktopMini2DxGame(new Startup(), config);
	}
}
