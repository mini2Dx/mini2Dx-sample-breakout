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
import org.mini2Dx.core.engine.geom.CollisionBox;
import org.mini2Dx.core.graphics.Graphics;
import org.mini2Dx.core.graphics.Sprite;

public class Brick {

    public enum Color {
        BLUE("bricks/blue.png"),
        GREEN("bricks/green.png"),
        GREY("bricks/grey.png"),
        PURPLE("bricks/purple.png"),
        RED("bricks/red.png"),
        YELLOW("bricks/yellow.png");

        private final String color;

        Color(final String color) {
            this.color = color;
        }

        @Override
        public String toString() {
            return color;
        }
    }

    private final CollisionBox collisionBox;
    private final Sprite boxSprite;
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

    void update() {
        collisionBox.preUpdate();
        if (CollisionHandler.getInstance().getTouchedBrick() == this) {
            setAlive(false);
            CollisionHandler.getInstance().killBrick();
        }
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
