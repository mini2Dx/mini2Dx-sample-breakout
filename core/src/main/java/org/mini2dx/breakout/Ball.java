package org.mini2dx.breakout;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
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

public class Ball {
    private final static String BALL_TEXTURE_IMAGE = "misc/ball.png";

    private float acceleration = 180;
    private final CollisionBox collisionBox;
    private final Sprite ballSprite;
    private int verticalMovementSign = -1, horizontalMovementSign = 1;

    private final static Sound wallCollisionSound = Gdx.audio.newSound(Gdx.files.internal("audio/wall.ogg"));
    private final static Sound brickCollisionSound = Gdx.audio.newSound(Gdx.files.internal("audio/brick.ogg"));
    private final static Sound paddleCollisionSound = Gdx.audio.newSound(Gdx.files.internal("audio/paddle.ogg"));

    private final float SPEEDUP_STEP = (Paddle.PADDLE_ACCELERATION - 50 - acceleration) / (BreakoutGame.gridSizeX * BreakoutGame.gridSizeY);
    //50 is a random number I chose as the minimum gap between the paddle speed and the ball speed

    public Ball(){
        Texture ballTexture = new Texture(BALL_TEXTURE_IMAGE);
        ballSprite = new Sprite(ballTexture);
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
        if (collisionBox.getX() + collisionBox.getWidth() > BreakoutGame.gameWidth || collisionBox.getX() <= 0) { //wall collision
            horizontalMovementSign *= -1;
            wallCollisionSound.play();
        }
        if (CollisionHandler.getInstance().isBallTouchingPaddle()) {
            verticalMovementSign = -1;
            paddleCollisionSound.play();
        }
        if (CollisionHandler.getInstance().isBallTouchingAnyBrick()) {
            verticalMovementSign *= -1;
            brickCollisionSound.play();
        }
        if (collisionBox.getY() <= 0) { //wall collision
            verticalMovementSign *= -1;
            wallCollisionSound.play();
        }
        collisionBox.setX(collisionBox.getX() + acceleration * delta * horizontalMovementSign);
        collisionBox.setY(collisionBox.getY() + acceleration * delta * verticalMovementSign);
        if (CollisionHandler.getInstance().isBallTouchingAnyBrick()) {
            acceleration += SPEEDUP_STEP;
            if ((BreakoutGame.DEBUG_MODE & BreakoutGame.DEBUG_BALL_SPEEDUP) != 0)
                System.out.println(acceleration);
        }
    }

    public void interpolate(float alpha) {
        collisionBox.interpolate(null, alpha);
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
