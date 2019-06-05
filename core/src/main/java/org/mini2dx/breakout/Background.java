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

import com.badlogic.gdx.graphics.Texture;
import org.mini2Dx.core.graphics.Graphics;

public class Background {
    private final static int BACKGROUND_SCALE = 8;

    private final Texture backgroundTexture = new Texture("misc/background.png");

    public void render(Graphics g) {
        for (int x = 0; x < BreakoutGame.gameWidth; x += backgroundTexture.getWidth() / BACKGROUND_SCALE)
            for (int y = 0; y < BreakoutGame.gameHeight; y += backgroundTexture.getHeight() / BACKGROUND_SCALE)
                g.drawTexture(backgroundTexture, x, y, backgroundTexture.getWidth() / BACKGROUND_SCALE, backgroundTexture.getHeight() / BACKGROUND_SCALE);
    }
}
