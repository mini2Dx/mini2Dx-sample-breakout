package org.mini2dx.breakout;

import com.badlogic.gdx.graphics.Texture;
import org.mini2Dx.core.engine.geom.CollisionBox;
import org.mini2Dx.core.graphics.Graphics;
import org.mini2Dx.core.graphics.Sprite;

class Paddle {

    @SuppressWarnings("FieldCanBeLocal")
    private final float acceleration = 250;
    private CollisionBox collisionBox;
    private Sprite paddleSprite;

    public Paddle(){
        Texture paddleTexture = new Texture("paddleBlu.png");
        paddleSprite = new Sprite(paddleTexture);
        collisionBox = new CollisionBox();
        collisionBox.setWidth(paddleSprite.getWidth());
        collisionBox.setHeight(paddleSprite.getHeight());

        collisionBox.setX(BreakoutGame.gameWidth / 2f - paddleSprite.getWidth() / 2);
        collisionBox.setY(BreakoutGame.gameHeight - paddleSprite.getHeight());
    }

    public void update(float delta) {
        collisionBox.preUpdate();

        InputHandler iH = InputHandler.getInput();
        if (iH.isLeftPressed()) {
            collisionBox.setX(Math.max(collisionBox.getX() - acceleration * delta, 0));
        }
        if (iH.isRightPressed()){
            float newX = collisionBox.getX() + acceleration * delta;
            collisionBox.setX(newX + collisionBox.getWidth() < BreakoutGame.gameWidth ? newX : BreakoutGame.gameWidth - collisionBox.getWidth());
        }
    }

    public void interpolate(float alpha) {
        collisionBox.interpolate(null, alpha);
    }

    public void render(Graphics g) {
        g.drawSprite(paddleSprite, collisionBox.getRenderX(), collisionBox.getRenderY());
    }
}
