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


import org.mini2Dx.core.graphics.Graphics;

public class ScoreCounter {
    private static ScoreCounter current;
    private int score = 0;
    private String scoreString;
    private boolean isScoreStringDirty = true;

    public ScoreCounter() {
        current = this;
    }

    public static ScoreCounter getInstance() {
        return current;
    }

    public void update() {
        if (CollisionHandler.getInstance().isBallTouchingAnyBrick()) {
            score += 100 * LivesHandler.getInstance().getLives() / LivesHandler.INITIAL_LIVES_NUM;
            isScoreStringDirty = true;
        }
    }

    public void render(Graphics g) {
        if (isScoreStringDirty) {
            scoreString = "Score: " + score;
            isScoreStringDirty = false;
        }
        g.drawString(scoreString, 4, 8);
    }

    public int getScore() {
        return score;
    }
}
