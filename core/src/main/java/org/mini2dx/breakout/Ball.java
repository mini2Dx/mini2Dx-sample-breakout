package org.mini2dx.breakout;

import com.badlogic.gdx.graphics.Texture;
import org.mini2Dx.core.engine.geom.CollisionBox;
import org.mini2Dx.core.graphics.Graphics;
import org.mini2Dx.core.graphics.Sprite;

public class Ball {
    private final static String BALL_TEXTURE_IMAGE = "ballBlue.png";
    public static final int SPEEDUP_SCORE = (BreakoutGame.DEBUG_MODE & BreakoutGame.DEBUG_BALL_SPEEDUP_EARLIER) == 0 ? 45 : 5;
    public static final float SPEEDUP_FACTOR = 1.4f;

    @SuppressWarnings("FieldCanBeLocal")
    private float acceleration = 180;
    private CollisionBox collisionBox;
    private Sprite ballSprite;
    private int verticalMovementSign = -1, horizontalMovementSign = 1;
    private boolean didSpeedUp = false;
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
        if (collisionBox.getX() + collisionBox.getWidth() > BreakoutGame.gameWidth || collisionBox.getX() <= 0)
            horizontalMovementSign *= -1;
        if (CollisionHandler.getInstance().isBallTouchingPaddle())
            verticalMovementSign = -1;
        if (CollisionHandler.getInstance().isBallTouchingAnyBrick())
            verticalMovementSign *= -1;
        if (collisionBox.getY() <= 0)
            verticalMovementSign *= -1;
        collisionBox.setX(collisionBox.getX() + acceleration * delta * horizontalMovementSign);
        collisionBox.setY(collisionBox.getY() + acceleration * delta * verticalMovementSign);
        if (ScoreCounter.getInstance().getScore() == SPEEDUP_SCORE && !didSpeedUp) {
            didSpeedUp = true;
            acceleration *= SPEEDUP_FACTOR;
            if ((BreakoutGame.DEBUG_MODE & BreakoutGame.DEBUG_BALL_SPEEDUP) != 0)
                System.out.println("Ball shrink!");
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
