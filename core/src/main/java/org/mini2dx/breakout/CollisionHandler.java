package org.mini2dx.breakout;

import com.badlogic.gdx.utils.Array;
import org.mini2Dx.core.collisions.RegionQuadTree;
import org.mini2Dx.core.engine.geom.CollisionBox;
import org.mini2Dx.core.graphics.Graphics;

public class CollisionHandler {
    private static CollisionHandler current;

    private Paddle paddle;
    private Ball ball;
    private Brick[][] bricks;

    private boolean isBallTouchingPaddle = false;
    private boolean isBallTouchingAnyBrick = false;
    private Brick touchedBrick = null;
    private int aliveBricks;

    static {
        current = new CollisionHandler();
    }

    private CollisionHandler(){
    }

    public static CollisionHandler getInstance() {
        return current;
    }

    public void setPaddle(Paddle paddle) {
        this.paddle = paddle;
    }

    public void setBall(Ball ball){
        this.ball = ball;
    }

    public static void update(){
        current.isBallTouchingPaddle = current.ball.getCollisionBox().intersects(current.paddle.getCollisionBox());

        current.touchedBrick = null;

        for (int i = 0; i < BreakoutGame.gridSizeX; i++)
            for (int j = 0; j < BreakoutGame.gridSizeY; j++)
                if (current.bricks[i][j].isAlive() && current.ball.getCollisionBox().intersects(current.bricks[i][j].getCollisionBox()))
                    current.touchedBrick = current.bricks[i][j];

        current.isBallTouchingAnyBrick = current.touchedBrick != null;

        if ((BreakoutGame.DEBUG_MODE & BreakoutGame.DEBUG_COLLISION_PRINT) != 0) {
            if (current.isBallTouchingPaddle)
                System.out.println("Touched the paddle");
            if (current.isBallTouchingAnyBrick)
                System.out.println("Touched a brick");
        }
    }

    public void setBricks(Brick[][] bricks) {
        this.bricks = bricks;
        aliveBricks = bricks.length * bricks[0].length;
    }

    public boolean isBallTouchingPaddle(){
        return isBallTouchingPaddle;
    }

    public boolean isBallTouchingAnyBrick() {
        return isBallTouchingAnyBrick;
    }

    public Brick getTouchedBrick() {
        return touchedBrick;
    }

    public int getAliveBricks() {
        return aliveBricks;
    }

    public void killBrick() {
        aliveBricks--;
    }
}
