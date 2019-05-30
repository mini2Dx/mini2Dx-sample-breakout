package org.mini2dx.breakout;

import com.badlogic.gdx.Gdx;
import org.mini2Dx.core.game.BasicGame;
import org.mini2Dx.core.graphics.Graphics;
import org.mini2Dx.core.graphics.viewport.FitViewport;
import org.mini2Dx.core.graphics.viewport.Viewport;

public class BreakoutGame extends BasicGame {
	public static final String GAME_IDENTIFIER = "org.mini2dx.breakout";
	public static final int gridSizeX = 8, gridSizeY = 6;
	public static final float gameWidth = gridSizeX * Box.width, gameHeight = gridSizeY * Box.height * 3;
    private Viewport viewport;
	private Paddle paddle;
    private Box[][] boxes = new Box[gridSizeX][gridSizeY];
	private final Box.Color[] boxColor = {Box.Color.RED, Box.Color.PURPLE, Box.Color.BLUE, Box.Color.GREEN, Box.Color.YELLOW, Box.Color.GREY};

	@Override
    public void initialise() {
	    if (gridSizeY > boxColor.length)
	        throw new RuntimeException("There should be at least a color for each row of boxes");
        viewport = new FitViewport(gameWidth, gameHeight);
	    paddle = new Paddle();
        for (int j = 0; j < gridSizeY; j++)
            for (int i = 0; i < gridSizeX; i++)
                boxes[i][j] = new Box(boxColor[j], i * Box.width, j * Box.height);
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
        for (int i = 0; i < gridSizeX; i++)
            for (int j = 0; j < gridSizeY; j++)
                boxes[i][j].update(delta);
    }
    
    @Override
    public void interpolate(float alpha) {
        paddle.interpolate(alpha);
        for (int i = 0; i < gridSizeX; i++)
            for (int j = 0; j < gridSizeY; j++)
                boxes[i][j].interpolate(alpha);
    }
    
    @Override
    public void render(Graphics g) {
        viewport.apply(g);
        paddle.render(g);
        for (int i = 0; i < gridSizeX; i++)
            for (int j = 0; j < gridSizeY; j++)
                boxes[i][j].render(g);
    }
}
