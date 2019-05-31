package org.mini2dx.breakout;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import org.mini2Dx.core.game.BasicGame;
import org.mini2Dx.core.graphics.GlyphLayout;
import org.mini2Dx.core.graphics.Graphics;
import org.mini2Dx.core.graphics.viewport.FitViewport;
import org.mini2Dx.core.graphics.viewport.Viewport;

public class BreakoutGame extends BasicGame {
    public static final int DEBUG_INPUT = 1, DEBUG_COLLISION_DRAW_COLLISION_BOXES = 2, DEBUG_COLLISION_PRINT = 4, DEBUG_BALL_SPEEDUP = 8,
            DEBUG_BALL_SPEEDUP_EARLIER = 16, DEBUG_PADDLE_SHRINK = 32, DEBUG_PADDLE_SHRINK_EARLIER = 64;
    public static final int DEBUG_MODE = 0;

    private final static String GAME_OVER_STRING = "GAME OVER!";

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
    private LivesHandler lives;

    private GlyphLayout glyphLayout = new GlyphLayout();

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
        lives = new LivesHandler();
    }

    @Override
    public void update(float delta) {
        InputHandler.update();
        if (InputHandler.getInstance().isQuitPressed()) {
            Gdx.app.exit();
        } else if (InputHandler.getInstance().isRestartPressed()) {
            initialiseGame();
        }
        if (!lives.isDead()) {
            paddle.update(delta);
            CollisionHandler.update();
            ball.update(delta);
            for (int i = 0; i < gridSizeX; i++)
                for (int j = 0; j < gridSizeY; j++)
                    bricks[i][j].update(delta);

            score.update();
            if (ball.getCollisionBox().getY() > gameHeight) {
                lives.decrease();
                if (!lives.isDead())
                    ball.returnToDefaultPosition();
            }
        }
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
        lives.render(g);
        if (lives.isDead()) {
            glyphLayout.setText((BitmapFont) g.getFont(), GAME_OVER_STRING);
            int renderX = Math.round((getWidth() / 2f) - (glyphLayout.width / 2f));
            int renderY = Math.round((getHeight() / 2f) - (glyphLayout.height / 2f));
            g.drawString(GAME_OVER_STRING, renderX, renderY);
        }
    }
}