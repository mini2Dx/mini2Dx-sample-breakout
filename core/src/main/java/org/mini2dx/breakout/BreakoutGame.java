package org.mini2dx.breakout;

import com.badlogic.gdx.Gdx;
import org.mini2Dx.core.game.BasicGame;
import org.mini2Dx.core.graphics.Graphics;
import org.mini2Dx.core.graphics.viewport.FitViewport;
import org.mini2Dx.core.graphics.viewport.Viewport;

public class BreakoutGame extends BasicGame {
	public static final String GAME_IDENTIFIER = "org.mini2dx.breakout";
	public static final int gameWidth  = 800, gameHeight = 600;
    private Viewport viewport;
	private Paddle paddle;

	@Override
    public void initialise() {
	    paddle = new Paddle();
        viewport = new FitViewport(gameWidth, gameHeight);
    }
    
    @Override
    public void update(float delta) {
        InputHandler.update();
        if (InputHandler.getInput().isQuitPressed()){
            Gdx.app.exit();
        } else if (InputHandler.getInput().isRestartPressed()){
            //TODO: add game restart capability
            return;
        }
        paddle.update(delta);
    }
    
    @Override
    public void interpolate(float alpha) {
        paddle.interpolate(alpha);
    }
    
    @Override
    public void render(Graphics g) {
        viewport.apply(g);
        paddle.render(g);
    }
}
