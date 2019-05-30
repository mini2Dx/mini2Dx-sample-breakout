package org.mini2dx.breakout;

import com.badlogic.gdx.graphics.Texture;
import org.mini2Dx.core.graphics.Graphics;

public class Background {
    private final static int BACKGROUND_SCALE = 8;

    private final Texture backgroundTexture = new Texture("bg.png");

    public void render(Graphics g) {
        for (int x = 0; x < BreakoutGame.gameWidth; x += backgroundTexture.getWidth() / BACKGROUND_SCALE)
            for (int y = 0; y < BreakoutGame.gameHeight; y += backgroundTexture.getHeight() / BACKGROUND_SCALE)
                g.drawTexture(backgroundTexture, x, y, backgroundTexture.getWidth() / BACKGROUND_SCALE, backgroundTexture.getHeight() / BACKGROUND_SCALE);
    }
}
