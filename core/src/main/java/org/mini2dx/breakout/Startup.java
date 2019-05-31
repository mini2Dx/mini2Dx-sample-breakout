package org.mini2dx.breakout;

import org.mini2Dx.core.game.ScreenBasedGame;

public class Startup extends ScreenBasedGame {
    public static final String GAME_IDENTIFIER = "org.mini2dx.breakout";

    @Override
    public void initialise() {
        addScreen(new MainMenu());
        addScreen(new BreakoutGame());
    }

    @Override
    public int getInitialScreenId() {
        return MainMenu.ID;
    }
}
