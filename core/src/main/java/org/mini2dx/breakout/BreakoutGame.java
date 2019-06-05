/*******************************************************************************
 * Copyright 2019 Viridian Software Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package org.mini2dx.breakout;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import org.mini2Dx.core.game.GameContainer;
import org.mini2Dx.core.graphics.GlyphLayout;
import org.mini2Dx.core.graphics.Graphics;
import org.mini2Dx.core.graphics.viewport.FitViewport;
import org.mini2Dx.core.graphics.viewport.Viewport;
import org.mini2Dx.core.screen.BasicGameScreen;
import org.mini2Dx.core.screen.ScreenManager;
import org.mini2Dx.core.screen.transition.FadeInTransition;
import org.mini2Dx.core.screen.transition.FadeOutTransition;
import org.mini2Dx.core.screen.transition.NullTransition;

public class BreakoutGame extends BasicGameScreen {
    public static final int ID = 2;

    public static final int DEBUG_INPUT = 1, DEBUG_COLLISION_DRAW_COLLISION_BOXES = 2, DEBUG_COLLISION_PRINT = 4, DEBUG_BALL_SPEEDUP = 8,
            DEBUG_PADDLE_SHRINK = 16, DEBUG_PADDLE_SHRINK_EARLIER = 32;
    public static final int DEBUG_MODE = 0;

    public static final int gridSizeX = 10, gridSizeY = 6;
    public static final float gameWidth = gridSizeX * Brick.width, gameHeight = gridSizeY * Brick.height * 3;

    private static final String WIN_STRING = "You won!";
    private static final String GAME_OVER_STRING = "GAME OVER!";

    private static final Brick.Color[] brickColors = {Brick.Color.RED, Brick.Color.PURPLE, Brick.Color.BLUE, Brick.Color.GREEN, Brick.Color.YELLOW, Brick.Color.GREY};

    private Viewport viewport;
    private Background background;
    private Paddle paddle;
    private Ball ball;
    private final Brick[][] bricks = new Brick[gridSizeX][gridSizeY];
    private ScoreCounter score;
    private LivesHandler lives;
    private boolean didSaveScore;
    private boolean isGameRestarted;

    private final GlyphLayout glyphLayout = new GlyphLayout();

    @Override
    public void initialise(GameContainer gameContainer) {
        //noinspection ConstantConditions
        assert gridSizeY > brickColors.length; //there should be at least a color for every row of bricks

        viewport = new FitViewport(gameWidth, gameHeight);
        background = new Background();
        initialiseGame();
    }

    public void initialiseGame() {
        isGameRestarted = false;
        didSaveScore = false;
        paddle = new Paddle();
        ball = new Ball();
        initialiseBricks();
        CollisionHandler.getInstance().setPaddle(paddle);
        CollisionHandler.getInstance().setBall(ball);
        score = new ScoreCounter();
        lives = new LivesHandler();
    }

    private void initialiseBricks() {
        for (int j = 0; j < gridSizeY; j++)
            for (int i = 0; i < gridSizeX; i++)
                bricks[i][j] = new Brick(brickColors[j], i * Brick.width, j * Brick.height);

        CollisionHandler.getInstance().setBricks(bricks);
    }

    @Override
    public void update(GameContainer gameContainer, ScreenManager screenManager, float delta) {
        if (isGameRestarted)
            initialiseGame();

        InputHandler.update();
        if (InputHandler.getInstance().isQuitPressed()) {
            screenManager.enterGameScreen(MainMenu.ID, new NullTransition(),
                    new FadeInTransition());
            LeaderboardHandler.getInstance().addScore(ScoreCounter.getInstance().getScore());
            isGameRestarted = true;
        } else if (InputHandler.getInstance().isRestartPressed()) {
            screenManager.enterGameScreen(BreakoutGame.ID, new NullTransition(),
                    new FadeInTransition());
            LeaderboardHandler.getInstance().addScore(ScoreCounter.getInstance().getScore());
            isGameRestarted = true;
        }

        if (CollisionHandler.getInstance().getAliveBricks() == 0) {
            initialiseBricks();
            ball.returnToDefaultPosition();
        }

        if (!lives.isDead()) {
            paddle.update(delta);
            CollisionHandler.update();
            ball.update(delta);
            for (int i = 0; i < gridSizeX; i++)
                for (int j = 0; j < gridSizeY; j++)
                    bricks[i][j].update();

            score.update();
            if (ball.getCollisionBox().getY() > gameHeight) {
                lives.decrease();
                if (!lives.isDead())
                    ball.returnToDefaultPosition();
            }
        } else {
            if (!didSaveScore) {
                didSaveScore = true;
                LeaderboardHandler.getInstance().addScore(ScoreCounter.getInstance().getScore());
            }

            if (InputHandler.getInstance().isAnyKeyPressed()) {
                screenManager.enterGameScreen(MainMenu.ID, new FadeOutTransition(),
                        new FadeInTransition());
                isGameRestarted = true;
            }
        }
    }

    @Override
    public void interpolate(GameContainer gameContainer, float alpha) {
        paddle.interpolate(alpha);
        ball.interpolate(alpha);
        for (int i = 0; i < gridSizeX; i++)
            for (int j = 0; j < gridSizeY; j++)
                bricks[i][j].interpolate(alpha);
    }

    @Override
    public void render(GameContainer gameContainer, Graphics g) {
        viewport.apply(g);
        background.render(g);
        if (lives.isDead()) {
            drawCenterAlignedString(g, GAME_OVER_STRING);
        } else if (CollisionHandler.getInstance().getAliveBricks() == 0) {
            drawCenterAlignedString(g, WIN_STRING);
        } else {
            paddle.render(g);
            ball.render(g);
            for (int i = 0; i < gridSizeX; i++)
                for (int j = 0; j < gridSizeY; j++)
                    bricks[i][j].render(g);
        }
        score.render(g);
        lives.render(g);
    }

    @Override
    public int getId() {
        return ID;
    }

    public void drawCenterAlignedString(Graphics g, String s) {
        glyphLayout.setText((BitmapFont) g.getFont(), s);
        int renderX = Math.round((gameWidth / 2f) - (glyphLayout.width / 2f));
        int renderY = Math.round((gameHeight / 2f) - (glyphLayout.height / 2f));
        g.drawString(s, renderX, renderY);
    }
}