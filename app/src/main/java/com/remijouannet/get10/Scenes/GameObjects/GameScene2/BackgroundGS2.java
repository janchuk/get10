package com.remijouannet.get10.Scenes.GameObjects.GameScene2;

import android.view.MotionEvent;

import com.remijouannet.get10.Graphic2D.Shape.Shape;
import com.remijouannet.get10.Graphic2D.Helper.BitmapHelper;
import com.remijouannet.get10.Graphic2D.GLRenderer;
import com.remijouannet.get10.Graphic2D.GameObject;
import com.remijouannet.get10.Graphic2D.Texture.LoadTexture;
import com.remijouannet.get10.Graphic2D.Texture.TextureRegion;
import com.remijouannet.get10.Scenes.GameScene.GameScene2;
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

public class BackgroundGS2 implements GameObject{
    private static final String TAG = BackgroundGS2.class.getSimpleName();
    public Shape background;
    public Shape bigCircle;
    public Shape littleCircle;
    public LoadTexture textureBackground;

    GLRenderer glRenderer;
    GameScene2 gameScene2;

    public BackgroundGS2(GLRenderer glRenderer, GameScene2 gameScene2) {
        this.gameScene2 = gameScene2;
        this.glRenderer = glRenderer;
    }

    @Override
    public void draw(float[] mtrxProjectionAndView) {
        background.Draw(mtrxProjectionAndView);
        bigCircle.Draw(mtrxProjectionAndView);
    }

    @Override
    public void draw2(float[] mtrxProjectionAndView) {
        littleCircle.Draw(mtrxProjectionAndView);
    }

    @Override
    public void init() {
        background = new Shape();
        bigCircle = new Shape();
        littleCircle = new Shape();
        textureBackground = new LoadTexture(glRenderer);

        textureBackground.bitmap(BitmapHelper.cropBitmap(
                BitmapHelper.getAssetsBitmap(
                        glRenderer.context.getAssets(), Settings.background, glRenderer.screenWidth),
                glRenderer.screenRatio));
        //textureBackground.recycle();

        create();
    }

    @Override
    public void create() {
        background.rect.create(glRenderer.screenWidth / 2, glRenderer.screenHeight / 2,
                glRenderer.screenWidth, glRenderer.screenHeight,
                textureBackground.texture()
        );

        bigCircle.circle.create(Settings.bigCircleRadius,
                glRenderer.screenWidth / 2,
                glRenderer.screenHeight / 2,
                Settings.colorWhite);

        littleCircle.circle.create(Settings.littleCircleRadius * 1.02f,
                glRenderer.screenWidth / 2,
                glRenderer.screenHeight / 2,
                new TextureRegion(textureBackground,
                        textureBackground.texture().getWidth() / 2,
                        textureBackground.texture().getHeight() / 2,
                        (Settings.littleCircleRadius * textureBackground.texture().getHeight() / glRenderer.screenHeight) * 2).texture()
        );

    }

    @Override
    public void reset() {
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