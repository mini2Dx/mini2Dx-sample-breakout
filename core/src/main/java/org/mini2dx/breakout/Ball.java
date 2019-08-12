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

import org.mini2Dx.core.Mdx;
import org.mini2Dx.core.audio.Sound;
import org.mini2Dx.core.collision.CollisionBox;
import org.mini2Dx.core.Graphics;
import org.mini2Dx.core.graphics.Sprite;
import org.mini2Dx.core.graphics.Texture;

import java.io.IOException;

public class Ball {
    private final static String BALL_TEXTURE_IMAGE = "misc/ball.png";

    private float acceleration = 180;
    private final CollisionBox collisionBox;
    private final Sprite ballSprite;
    private int verticalMovementSign = -1, horizontalMovementSign = 1;

    private static Sound wallCollisionSound;
    private final static float wallCollisionSoundVolume = 0.5f;

    private static Sound brickCollisionSound;
    private final static float brickCollisionSoundVolume = 0.5f;

    private static Sound paddleCollisionSound;
    private final static float paddleCollisionSoundVolume = 0.5f;

    static {
        try {
            wallCollisionSound = Mdx.audio.newSound(Mdx.files.internal("audio/wall.ogg"));
            brickCollisionSound = Mdx.audio.newSound(Mdx.files.internal("audio/brick.ogg"));
            paddleCollisionSound = Mdx.audio.newSound(Mdx.files.internal("audio/paddle.ogg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private final float SPEEDUP_STEP = (Paddle.PADDLE_ACCELERATION - 50 - acceleration) / (BreakoutGame.gridSizeX * BreakoutGame.gridSizeY);
    //50 is a random number I chose as the minimum gap between the paddle speed and the ball speed

    public Ball(){
        Texture ballTexture = Mdx.graphics.newTexture(Mdx.files.internal(BALL_TEXTURE_IMAGE));
        ballSprite = Mdx.graphics.newSprite(ballTexture);
        collisionBox = new CollisionBox();
        collisionBox.setWidth(ballSprite.getWidth());
        collisionBox.setHeight(ballSprite.getHeight());

        returnToDefaultPosition();
    }

    public void returnToDefaultPosition() {
        collisionBox.setCenter(BreakoutGame.gameWidth / 2, BreakoutGame.gameHeight * 3 / 4);
        verticalMovementSign = -1;
    }

    public void update(float delta) {
        collisionBox.preUpdate();
        if (collisionBox.getX() + collisionBox.getWidth() > BreakoutGame.gameWidth || collisionBox.getX() <= 0) { //lateral wall collision
            horizontalMovementSign *= -1;
            wallCollisionSound.play(wallCollisionSoundVolume);
        }
        if (CollisionHandler.getInstance().isBallTouchingPaddle()) {
            verticalMovementSign = -1;
            paddleCollisionSound.play(paddleCollisionSoundVolume);
        }
        if (CollisionHandler.getInstance().isBallTouchingAnyBrick()) {
            verticalMovementSign *= -1;
            brickCollisionSound.play(brickCollisionSoundVolume);
        }
        if (collisionBox.getY() <= 0) { //top wall collision
            verticalMovementSign = 1;
            wallCollisionSound.play(wallCollisionSoundVolume);
        }
        collisionBox.setX(collisionBox.getX() + acceleration * delta * horizontalMovementSign);
        collisionBox.setY(collisionBox.getY() + acceleration * delta * verticalMovementSign);
        if (CollisionHandler.getInstance().isBallTouchingAnyBrick()) {
            acceleration += SPEEDUP_STEP;
            if ((BreakoutGame.DEBUG_MODE & BreakoutGame.DEBUG_BALL_SPEEDUP) != 0)
                System.out.println(acceleration);
        }
    }

    public void render(Graphics g) {
        g.drawSprite(ballSprite, collisionBox.getRenderX(), collisionBox.getRenderY());
        if ((BreakoutGame.DEBUG_MODE & BreakoutGame.DEBUG_COLLISION_DRAW_COLLISION_BOXES) != 0)
            collisionBox.draw(g);
    }

    public CollisionBox getCollisionBox() {
        return collisionBox;
    }
}
