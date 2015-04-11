package com.remijouannet.get10.Graphic2D;

import android.view.MotionEvent;

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

public interface GameScene {
    public void init();
    public void drawStatic(float[] mtrxProjectionAndView);
    public void drawMove(float[] mtrxProjectionAndView);
    public void touchEvent(MotionEvent event);
    public void move();
    public void collision();
    public void gameOver();
    public void reset();
}
