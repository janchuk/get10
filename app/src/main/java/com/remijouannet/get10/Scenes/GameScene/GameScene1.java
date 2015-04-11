package com.remijouannet.get10.Scenes.GameScene;


import android.view.MotionEvent;

import com.remijouannet.get10.Activity.FirstActivity;
import com.remijouannet.get10.GameMode.GameMode;
import com.remijouannet.get10.Graphic2D.GameScene;
import com.remijouannet.get10.Graphic2D.Helper.MediaPlayerHelper;
import com.remijouannet.get10.Graphic2D.Texture.Message;
import com.remijouannet.get10.Graphic2D.Tools;
import com.remijouannet.get10.R;
import com.remijouannet.get10.Scenes.Collision.CollisionGS1;
import com.remijouannet.get10.Data;
import com.remijouannet.get10.Scenes.GameObjects.GameScene1.BackgroundGS1;
import com.remijouannet.get10.Scenes.GameObjects.GameScene1.BirdGS1;
import com.remijouannet.get10.Scenes.GameObjects.GameScene1.ColumnsGS1;
import com.remijouannet.get10.Scenes.GameObjects.GameScene1.UIPauseGS1;
import com.remijouannet.get10.Scenes.GameObjects.GameScene1.UIRunGS1;
import com.remijouannet.get10.Graphic2D.GLRenderer;
import com.remijouannet.get10.Scenes.GameWorld;
import com.remijouannet.get10.Scenes.SceneState;
import com.remijouannet.get10.Settings;

/*
    Copyright (C) 2015 Rémi Jouannet <remijouannet@gmail.com>
    This file is part of get10.
    get10 is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.
    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.
    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
  */

public class GameScene1 implements GameScene{
    private final static String TAG = GameScene1.class.getSimpleName();
    GLRenderer glRenderer;
    public GameWorld gameWorld;
    public BackgroundGS1 background;
    public ColumnsGS1 columns;
    public BirdGS1 bird;
    public UIPauseGS1 uiPause;
    public UIRunGS1 uiRun;
    public String STATE = SceneState.PAUSE;
    public CollisionGS1 collision;
    public Message messageLato;
    public int score = 0;
    public boolean invinsible = false;

    public GameScene1(GLRenderer glRenderer, GameWorld gameWorld) {
        this.gameWorld = gameWorld;
        this.glRenderer = glRenderer;
    }

    @Override
    public void init() {
        new MediaPlayerHelper(glRenderer.context, R.raw.coin);
        new MediaPlayerHelper(glRenderer.context, R.raw.gameover);
        background = new BackgroundGS1(glRenderer, this);
        columns = new ColumnsGS1(glRenderer, this);
        bird = new BirdGS1(glRenderer, this);
        collision = new CollisionGS1(glRenderer, this);
        uiPause = new UIPauseGS1(glRenderer, this);
        uiRun = new UIRunGS1(glRenderer, this);
        messageLato =  new Message(glRenderer, GLRenderer.context.getAssets(), "fonts/Lato/Lato-Regular.ttf",
                Tools.dpToPixel(78, FirstActivity.density), 2, 2, Tools.getIntFromColor(Settings.colorWhite));
        background.init();
        columns.init();
        bird.init();
        uiPause.init();
        uiRun.init();

        STATE = SceneState.PAUSE;
    }

    @Override
    public void drawStatic(float[] mtrxProjectionAndView) {
        background.draw(mtrxProjectionAndView);
        columns.draw(mtrxProjectionAndView);
        bird.draw(mtrxProjectionAndView);
        background.draw2(mtrxProjectionAndView);
        uiRun.draw(mtrxProjectionAndView);
    }

    @Override
    public void drawMove(float[] mtrxProjectionAndView) {
        move();
        collision();
        if (!STATE.equals(SceneState.STOPDRAW)){
            background.draw(mtrxProjectionAndView);
            columns.draw(mtrxProjectionAndView);
            bird.draw(mtrxProjectionAndView);
            background.draw2(mtrxProjectionAndView);
        }
        if (!STATE.equals(SceneState.STOP)){
            uiRun.draw(mtrxProjectionAndView);
        }else if (STATE.equals(SceneState.STOP)){
            uiPause.draw(mtrxProjectionAndView);
        }
    }

    @Override
    public void touchEvent(MotionEvent event) {
        bird.touchEvent(event);

        if (collision.collisionCloseTouch(event)){
            glRenderer.Return();
        }

        if (STATE.equals(SceneState.STOP)){
            if (collision.collisionRestartTouch(event)){
                STATE = SceneState.STOPDRAW;
                reset();
                return;
            }
        }else if (STATE.equals(SceneState.PAUSE)
                && !STATE.equals(SceneState.STOPDRAW)) {
            STATE = SceneState.MOVE;
        }
    }

    @Override
    public void move() {
        if (STATE.equals(SceneState.MOVE)) {
            bird.move();
            columns.move();
        }else if (STATE.equals(SceneState.STOP)){
            uiPause.move();
        }
    }

    @Override
    public void collision() {
        if (STATE.equals(SceneState.MOVE)){
            if (collision.collisionBirdColumns()  && !invinsible){
                gameOver();
            }
            if (collision.collisionScore()){
                score += 1;
                if (Settings.sound)
                    MediaPlayerHelper.playSound(glRenderer.context, R.raw.coin);
                if (score >= GameMode.getGameMode(Settings.currentGameMode+1).scoreToUnlock){
                    Data.setUnlock(Settings.currentGameMode+1, true);
                }
            }
        }
    }

    @Override
    public void gameOver() {
        if (Settings.sound)
            MediaPlayerHelper.playSound(glRenderer.context, R.raw.gameover);
        Data.setHighScore(Settings.currentGameMode, score);
        Data.setDeath(Settings.currentGameMode);
        STATE = SceneState.STOP;
        background.gameOver();
        columns.gameOver();
        bird.gameOver();
        uiRun.gameOver();
        score = 0;
        collision = new CollisionGS1(glRenderer, this);
    }

    @Override
    public void reset(){
        bird.reset();
        columns.reset();
        background.reset();
        STATE = SceneState.PAUSE;
    }
}
