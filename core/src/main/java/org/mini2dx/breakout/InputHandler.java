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
        current.left = Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.DPAD_LEFT);
        current.right = Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.DPAD_RIGHT);
        current.restart = Gdx.input.isKeyPressed(Input.Keys.R) || Gdx.input.isKeyPressed(Input.Keys.ENTER);
        current.quit = Gdx.input.isKeyPressed(Input.Keys.ESCAPE);
    }

    public static InputHandler getInput(){
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
