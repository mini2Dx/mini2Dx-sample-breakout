package org.mini2dx.breakout;

import com.badlogic.gdx.graphics.Texture;
import org.mini2Dx.core.graphics.Graphics;

public class LivesHandler {
    public static final int INITIAL_LIVES_NUM = 3;
    private final static String HEART_TEXTURE_IMAGE = "heart.png";
    private final static Texture HEART_TEXTURE = new Texture(HEART_TEXTURE_IMAGE);
    private static LivesHandler current = new LivesHandler();
    private int lives = INITIAL_LIVES_NUM;

    public LivesHandler() {
        current = this;
    }

    public static LivesHandler getInstance() {
        return current;
    }

    public int getLives() {
        return lives;
    }

    public boolean isDead() {
        return lives == 0;
    }

    public void decrease() {
        lives--;
    }

    public void render(Graphics g) {
        for (int i = 0; i < lives; i++) {
            g.drawTexture(HEART_TEXTURE, 4 + (HEART_TEXTURE.getWidth() / 4 + 4) * i, BreakoutGame.gameHeight - HEART_TEXTURE.getHeight() / 4f, HEART_TEXTURE.getWidth() / 4, HEART_TEXTURE.getHeight() / 4);
        }
    }
}
