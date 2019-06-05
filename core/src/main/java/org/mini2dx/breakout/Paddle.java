package org.mini2dx.breakout;

import com.badlogic.gdx.graphics.Texture;
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
import org.mini2Dx.core.engine.geom.CollisionBox;
import org.mini2Dx.core.graphics.Graphics;
import org.mini2Dx.core.graphics.Sprite;

class Paddle {
    private final static int SHRINK_SCORE = (BreakoutGame.DEBUG_MODE & BreakoutGame.DEBUG_PADDLE_SHRINK_EARLIER) == 0 ? 30 : 5;
    private final static float SHRINK_SCALE = 0.8f;

    public final static float PADDLE_ACCELERATION = 350;
    public static final String PADDLE_TEXTURE_IMAGE = "misc/paddle.png";
    private final CollisionBox collisionBox;
    private final Sprite paddleSprite;
    private boolean didShrink = false;

    public Paddle(){
        Texture paddleTexture = new Texture(PADDLE_TEXTURE_IMAGE);
        paddleSprite = new Sprite(paddleTexture);
        collisionBox = new CollisionBox();
        collisionBox.setWidth(paddleSprite.getWidth());
        collisionBox.setHeight(paddleSprite.getHeight());

        returnToDefaultPosition();
    }

    public void returnToDefaultPosition(){
        collisionBox.setCenterX(BreakoutGame.gameWidth / 2);
        collisionBox.setY(BreakoutGame.gameHeight - paddleSprite.getHeight());
    }

    public void update(float delta) {
        collisionBox.preUpdate();

        if (ScoreCounter.getInstance().getScore() == SHRINK_SCORE && !didShrink) {
            paddleSprite.setScale(SHRINK_SCALE, SHRINK_SCALE);
            collisionBox.setHeight(paddleSprite.getHeight() * SHRINK_SCALE);
            collisionBox.setWidth(paddleSprite.getWidth() * SHRINK_SCALE);
            didShrink = true;
            if ((BreakoutGame.DEBUG_MODE & BreakoutGame.DEBUG_PADDLE_SHRINK) != 0) {
                System.out.println("Paddle shrink!");
            }
        }

        InputHandler iH = InputHandler.getInstance();
        if (iH.isLeftPressed()) {
            collisionBox.setX(Math.max(collisionBox.getX() - PADDLE_ACCELERATION * delta, 0));
        }
        if (iH.isRightPressed()){
            float newX = collisionBox.getX() + PADDLE_ACCELERATION * delta;
            collisionBox.setX(newX + collisionBox.getWidth() < BreakoutGame.gameWidth ? newX : BreakoutGame.gameWidth - collisionBox.getWidth());
        }
    }

    public void interpolate(float alpha) {
        collisionBox.interpolate(null, alpha);
    }

    public void render(Graphics g) {
        g.drawSprite(paddleSprite, collisionBox.getRenderX(), collisionBox.getRenderY());
        if ((BreakoutGame.DEBUG_MODE & BreakoutGame.DEBUG_COLLISION_DRAW_COLLISION_BOXES) != 0)
            collisionBox.draw(g);
    }

    public CollisionBox getCollisionBox() {
        return collisionBox;
    }
}
