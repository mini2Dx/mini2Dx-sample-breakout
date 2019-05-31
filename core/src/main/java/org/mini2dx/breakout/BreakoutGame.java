package org.mini2dx.breakout;

import com.badlogic.gdx.Gdx;
import org.mini2Dx.core.game.BasicGame;
import org.mini2Dx.core.graphics.Graphics;
import org.mini2Dx.core.graphics.viewport.FitViewport;
import org.mini2Dx.core.graphics.viewport.Viewport;

public class BreakoutGame extends BasicGame {
    public static final int DEBUG_INPUT = 1, DEBUG_COLLISION_DRAW_COLLISION_BOXES = 2, DEBUG_COLLISION_PRINT = 4;
    public static final int DEBUG_MODE = 0;

    public static final String GAME_IDENTIFIER = "org.mini2dx.breakout";
    public static final int gridSizeX = 10, gridSizeY = 6;
    public static final float gameWidth = gridSizeX * Brick.width, gameHeight = gridSizeY * Brick.height * 3;
    private Viewport viewport;
    private Paddle paddle;
    private Ball ball;
    private final Brick.Color[] boxColor = {Brick.Color.RED, Brick.Color.PURPLE, Brick.Color.BLUE, Brick.Color.GREEN, Brick.Color.YELLOW, Brick.Color.GREY};
    private Brick[][] bricks = new Brick[gridSizeX][gridSizeY];

    @Override
    public void initialise() {
        if (gridSizeY > boxColor.length)
            throw new RuntimeException("There should be at least a color for each row of bricks");
        viewport = new FitViewport(gameWidth, gameHeight);
        initaliseGame();
    }

    public void initaliseGame() {
        paddle = new Paddle();
        ball = new Ball();
        for (int j = 0; j < gridSizeY; j++)
            for (int i = 0; i < gridSizeX; i++)
                bricks[i][j] = new Brick(boxColor[j], i * Brick.width, j * Brick.height);

        CollisionHandler.getCollisionHandler().setPaddle(paddle);
        CollisionHandler.getCollisionHandler().setBall(ball);
        CollisionHandler.getCollisionHandler().setBricks(bricks);
    }

    @Override
    public void update(float delta) {
        InputHandler.update();
        if (InputHandler.getInput().isQuitPressed()) {
            Gdx.app.exit();
        } else if (InputHandler.getInput().isRestartPressed()) {
            initaliseGame();
        }

        paddle.update(delta);
        CollisionHandler.update();
        ball.update(delta);
        for (int i = 0; i < gridSizeX; i++)
            for (int j = 0; j < gridSizeY; j++)
                bricks[i][j].update(delta);
    }

    @Override
    public void interpolate(float alpha) {
        paddle.interpolate(alpha);
        ball.interpolate(alpha);
        for (int i = 0; i < gridSizeX; i++)
            for (int j = 0; j < gridSizeY; j++)
                bricks[i][j].interpolate(alpha);
    }

    @Override
    public void render(Graphics g) {
        viewport.apply(g);
        paddle.render(g);
        ball.render(g);
        for (int i = 0; i < gridSizeX; i++)
            for (int j = 0; j < gridSizeY; j++)
                bricks[i][j].render(g);
    }
}