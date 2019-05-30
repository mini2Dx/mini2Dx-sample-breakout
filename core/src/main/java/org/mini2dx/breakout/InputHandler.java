package org.mini2dx.breakout;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

class InputHandler {
    private boolean left, right, restart, quit;
    private static InputHandler current;

    static{
        current = new InputHandler();
    }

    private InputHandler(){

    }

    public static void update(){
        current.left = Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.DPAD_LEFT) || Gdx.input.isKeyPressed(Input.Keys.A);
        current.right = Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.DPAD_RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D);
        current.restart = Gdx.input.isKeyPressed(Input.Keys.R) || Gdx.input.isKeyPressed(Input.Keys.ENTER);
        current.quit = Gdx.input.isKeyPressed(Input.Keys.ESCAPE) || Gdx.input.isKeyPressed(Input.Keys.Q);

        if ((BreakoutGame.DEBUG_MODE & BreakoutGame.DEBUG_INPUT) != 0) {
            System.out.print(current.left ? 'L' : 'l');
            System.out.print(current.right ? 'R' : 'r');
            System.out.print(current.restart ? 'A' : 'a'); //a stands for again because R is already in use.
            System.out.println(current.quit ? 'Q' : 'q');
        }
    }

    public static InputHandler getInstance() {
        return current;
    }

    public boolean isLeftPressed() {
        return left;
    }

    public boolean isRightPressed() {
        return right;
    }

    public boolean isRestartPressed() {
        return restart;
    }

    public boolean isQuitPressed() {
        return quit;
    }
}
