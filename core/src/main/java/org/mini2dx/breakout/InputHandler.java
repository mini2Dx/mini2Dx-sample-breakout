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
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

class InputHandler {
    private boolean left, right, restart, quit, any;
    private static final InputHandler current;

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
        current.any = Gdx.input.isKeyPressed(Input.Keys.ANY_KEY);
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

    public boolean isAnyKeyPressed() {
        return any;
    }
}
