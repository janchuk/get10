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

package com.remijouannet.get10.scenes.collision;

import android.view.MotionEvent;

import com.remijouannet.get10.graphic2D.Collision;
import com.remijouannet.get10.graphic2D.shape.Shape;
import com.remijouannet.get10.graphic2D.GLRenderer;
import com.remijouannet.get10.scenes.gameScene.GameScene2;
import com.remijouannet.get10.Settings;


public class CollisionGS2 {
    private final static String TAG = CollisionGS2.class.getSimpleName();
    GLRenderer glRenderer;
    GameScene2 gameScene2;

    private Shape customBirdColumns0 = new Shape();
    private Shape customBirdColumns1 = new Shape();

    public CollisionGS2(GLRenderer glRenderer, GameScene2 gameScene2) {
        this.gameScene2 = gameScene2;
        this.glRenderer = glRenderer;

        customBirdColumns0.customPolygon.create(new float[]{
                        0.0f, 0.0f, 0.0f,
                        0.0f, 0.0f, 0.0f,
                        0.0f, 0.0f, 0.0f,
                        0.0f, 0.0f, 0.0f},
                new short[]{0, 1, 2, 0, 2, 3},
                Settings.color);

        customBirdColumns1.customPolygon.create(new float[]{
                        0.0f, 0.0f, 0.0f,
                        0.0f, 0.0f, 0.0f,
                        0.0f, 0.0f, 0.0f,
                        0.0f, 0.0f, 0.0f},
                new short[]{0, 1, 2, 0, 2, 3},
                Settings.color);
    }

    float[] vertices0 = new float[12];
    float[] vertices1 = new float[12];
    float[] shapeColumns;
    float[] shapeColumnsWay;
    public boolean collisionBirdColumns(){
        for (int i=0;i< gameScene2.columns.columnsGhost.length;i++){
            shapeColumns = gameScene2.columns.columnsGhost[i].getVertices();
            shapeColumnsWay = gameScene2.columns.columnsWay[i].getVertices();

            vertices0[0] = shapeColumnsWay[3];
            vertices0[1] = shapeColumnsWay[4];
            vertices0[3] = shapeColumns[3];
            vertices0[4] = shapeColumns[4];
            vertices0[6] = shapeColumns[6];
            vertices0[7] = shapeColumns[7];
            vertices0[9] = shapeColumnsWay[6];
            vertices0[10] = shapeColumnsWay[7];

            vertices1[0] = shapeColumns[0];
            vertices1[1] = shapeColumns[1];
            vertices1[3] = shapeColumnsWay[0];
            vertices1[4] = shapeColumnsWay[1];
            vertices1[6] = shapeColumnsWay[9];
            vertices1[7] = shapeColumnsWay[10];
            vertices1[9] = shapeColumns[9];
            vertices1[10] = shapeColumns[10];

            customBirdColumns0.customPolygon.setVertices(vertices0);

            customBirdColumns1.customPolygon.setVertices(vertices1);

            if (Collision.collision(gameScene2.bird.birdHit, customBirdColumns0) ||
                    Collision.collision(gameScene2.bird.birdHit, customBirdColumns1)){
                return true;
            }
        }
        return false;
    }

    public boolean collisionRestartTouch(MotionEvent event){
        return Collision.touchShape(gameScene2.uiPause.restart, event);
    }

    public boolean collisionCloseTouch(MotionEvent event){
        return  Collision.touchShape(gameScene2.uiPause.close, event);
    }

    private int waitCollision = 500;
    private boolean resultCollisionScore;
    public boolean collisionScore(){
        resultCollisionScore = Collision.collision(gameScene2.columns.startLine, gameScene2.bird.birdHit);
        if (waitCollision < 0 && resultCollisionScore){
            waitCollision = 500;
            return resultCollisionScore;
        }
        waitCollision -= glRenderer.deltaTime;
        return false;
    }
}
