package org.mini2dx.breakout;


import org.mini2Dx.core.graphics.Graphics;

public class ScoreCounter {
    private static ScoreCounter current;
    private int score = 0;
    private String scoreString;
    private boolean isScoreStringDirty = true;

    public ScoreCounter() {
        current = this;
    }

    public static ScoreCounter getInstance() {
        return current;
    }

    public void update() {
        if (CollisionHandler.getInstance().isBallTouchingAnyBrick()) {
            score++;
            isScoreStringDirty = true;
        }
    }

    public void render(Graphics g) {
        if (isScoreStringDirty) {
            scoreString = "Score: " + score;
            isScoreStringDirty = false;
        }
        g.drawString(scoreString, 8, 16);
    }

    public int getScore() {
        return score;
    }
}
