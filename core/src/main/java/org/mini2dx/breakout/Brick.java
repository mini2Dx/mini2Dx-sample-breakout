package org.mini2dx.breakout;


import com.badlogic.gdx.graphics.Texture;
import org.mini2Dx.core.engine.geom.CollisionBox;
import org.mini2Dx.core.graphics.Graphics;
import org.mini2Dx.core.graphics.Sprite;

public class Brick {

    public enum Color {
        BLUE("element_blue_rectangle.png"),
        GREEN("element_green_rectangle.png"),
        GREY("element_grey_rectangle.png"),
        PURPLE("element_purple_rectangle.png"),
        RED("element_red_rectangle.png"),
        YELLOW("element_yellow_rectangle.png");

        private final String color;

        Color(final String color) {
            this.color = color;
        }

        @Override
        public String toString() {
            return color;
        }
    }

    private CollisionBox collisionBox;
    private Sprite boxSprite;
    private boolean isAlive = true;
    public static final float height = 32f, width = 64f;

    Brick(Color color, float xPosition, float yPosition) {
        Texture boxTexture = new Texture(color.toString());
        boxSprite = new Sprite(boxTexture);
        collisionBox = new CollisionBox();
        collisionBox.setHeight(boxSprite.getHeight());
        collisionBox.setWidth(boxSprite.getWidth());
        collisionBox.setX(xPosition);
        collisionBox.setY(yPosition);
    }

    void update(float delta) {
        collisionBox.preUpdate();
        if (CollisionHandler.getInstance().getTouchedBrick() == this)
            setAlive(false);
    }

    void interpolate(float alpha) {
        collisionBox.interpolate(null, alpha);
    }

    void render(Graphics g) {
        if (isAlive) {
            g.drawSprite(boxSprite, collisionBox.getRenderX(), collisionBox.getRenderY());
            if ((BreakoutGame.DEBUG_MODE & BreakoutGame.DEBUG_COLLISION_DRAW_COLLISION_BOXES) != 0) {
                collisionBox.draw(g);
            }
        }
    }


    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public CollisionBox getCollisionBox() {
        return collisionBox;
    }
}
