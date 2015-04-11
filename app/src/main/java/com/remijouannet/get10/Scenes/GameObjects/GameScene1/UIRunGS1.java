package com.remijouannet.get10.Scenes.GameObjects.GameScene1;

import android.view.MotionEvent;

import com.remijouannet.get10.Activity.FirstActivity;
import com.remijouannet.get10.Graphic2D.Shape.Shape;
import com.remijouannet.get10.Graphic2D.GLRenderer;
import com.remijouannet.get10.Graphic2D.GameObject;
import com.remijouannet.get10.Graphic2D.Helper.BitmapHelper;
import com.remijouannet.get10.Graphic2D.Helper.GraphicHelper;
import com.remijouannet.get10.Graphic2D.Texture.LoadTexture;
import com.remijouannet.get10.Graphic2D.Texture.Message;
import com.remijouannet.get10.Graphic2D.Tools;
import com.remijouannet.get10.Scenes.GameScene.GameScene1;
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


public class UIRunGS1 implements GameObject{
    private static final String TAG = UIRunGS1.class.getSimpleName();
    public Message scoreMessage;

    public Shape close;
    LoadTexture closeTexture;

    GLRenderer glRenderer;
    GameScene1 gameScene1;

    int score = 0;

    public UIRunGS1(GLRenderer glRenderer, GameScene1 gameScene1) {
        this.gameScene1 = gameScene1;
        this.glRenderer = glRenderer;
        score = gameScene1.score;
    }

    @Override
    public void draw(float[] mtrxProjectionAndView) {
        if (gameScene1.score != score){
            score = gameScene1.score;
        }

        //TODO:That's fucking ugly
        if (String.valueOf(score).length() > 1){
            scoreMessage.draw(mtrxProjectionAndView, String.valueOf(score),
                    (glRenderer.screenWidth /2) - scoreMessage.cellWidth/4.5f,
                    glRenderer.screenHeight /1.1f, Tools.dpToPixel(82, FirstActivity.density));
        }else {
            scoreMessage.draw(mtrxProjectionAndView, String.valueOf(score),
                    glRenderer.screenWidth / 2, glRenderer.screenHeight / 1.1f, Tools.dpToPixel(82, FirstActivity.density));
        }

        GraphicHelper.drawTexture(mtrxProjectionAndView, close, close.getBuffer());
    }

    @Override
    public void draw2(float[] mtrxProjectionAndView) {

    }

    @Override
    public void init() {
        scoreMessage = gameScene1.messageLato;

        closeTexture = new LoadTexture(glRenderer);
        close = new Shape();

        create();
    }

    @Override
    public void create(){

        //CLOSE
        closeTexture.bitmap(BitmapHelper.getAssetsBitmap(
                glRenderer.context.getAssets(), "UI/close_white.png", glRenderer.screenWidth));
        //closeTexture.recycle();

        close.rect.create(glRenderer.screenWidth / 9.0f, glRenderer.screenHeight / 15.0f,
                Settings.sizeCloseButton, Settings.sizeCloseButton, closeTexture.texture()
        );

    }

    @Override
    public void reset(){

    }

    @Override
    public void gameOver() {

    }

    @Override
    public void touchEvent(MotionEvent event) {

    }

    @Override
    public void move() {

    }
}