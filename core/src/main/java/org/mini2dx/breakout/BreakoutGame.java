package org.mini2dx.breakout;

import com.badlogic.gdx.Gdx;
import org.mini2Dx.core.game.BasicGame;
import org.mini2Dx.core.graphics.Graphics;
import org.mini2Dx.core.graphics.viewport.FitViewport;
import org.mini2Dx.core.graphics.viewport.Viewport;

public class BreakoutGame extends BasicGame {
    public static final int DEBUG_INPUT = 1, DEBUG_COLLISION_DRAW_COLLISION_BOXES = 2, DEBUG_COLLISION_PRINT = 4, DEBUG_BALL_SPEEDUP = 8,
            DEBUG_BALL_SPEEDUP_EARLIER = 16, DEBUG_PADDLE_SHRINK = 32, DEBUG_PADDLE_SHRINK_EARLIER = 64;
    public static final int DEBUG_MODE = 0;

    public static final String GAME_IDENTIFIER = "org.mini2dx.breakout";
    public static final int gridSizeX = 10, gridSizeY = 6;
    public static final float gameWidth = gridSizeX * Brick.width, gameHeight = gridSizeY * Brick.height * 3;
    private Viewport viewport;
    private Background background;
    private Paddle paddle;
    private Ball ball;
    private final Brick.Color[] boxColor = {Brick.Color.RED, Brick.Color.PURPLE, Brick.Color.BLUE, Brick.Color.GREEN, Brick.Color.YELLOW, Brick.Color.GREY};
    private Brick[][] bricks = new Brick[gridSizeX][gridSizeY];
    private ScoreCounter score;

    @Override
    public void initialise() {
        if (gridSizeY > boxColor.length)
            throw new RuntimeException("There should be at least a color for each row of bricks");
        viewport = new FitViewport(gameWidth, gameHeight);
        background = new Background();
        initialiseGame();
    }

    public void initialiseGame() {
        paddle = new Paddle();
        ball = new Ball();
        for (int j = 0; j < gridSizeY; j++)
            for (int i = 0; i < gridSizeX; i++)
                bricks[i][j] = new Brick(boxColor[j], i * Brick.width, j * Brick.height);

        CollisionHandler.getInstance().setPaddle(paddle);
        CollisionHandler.getInstance().setBall(ball);
        CollisionHandler.getInstance().setBricks(bricks);
        score = new ScoreCounter();
    }

    @Override
    public void update(float delta) {
        InputHandler.update();
        if (InputHandler.getInstance().isQuitPressed()) {
            Gdx.app.exit();
        } else if (InputHandler.getInstance().isRestartPressed()) {
            initialiseGame();
        }

        paddle.update(delta);
        CollisionHandler.update();
        ball.update(delta);
        for (int i = 0; i < gridSizeX; i++)
            for (int j = 0; j < gridSizeY; j++)
                bricks[i][j].update(delta);

        score.update();
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
        background.render(g);
        paddle.render(g);
        ball.render(g);
        for (int i = 0; i < gridSizeX; i++)
            for (int j = 0; j < gridSizeY; j++)
                bricks[i][j].render(g);

        score.render(g);
    }
}