package com.choicely.snake;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

public class Score {

    private int score;

    private int size = 20;
    private final Paint paint = new Paint();

    private RectF position;

    public Score(int x, int y) {
        this.position = new RectF(x, y, x + size, y + size);
        init();
    }


    public void init() {
        paint.setColor(Color.YELLOW);
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void draw(Canvas canvas) {
        canvas.drawRect(position, paint);
    }

    public RectF getPosition() {
        return position;
    }

    public void setPosition(RectF position) {
        this.position = position;
    }
}
