package com.choicely.snake;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Random;

public class Board extends View {

    private final Paint paint = new Paint();
    private final Random random = new Random();

    private boolean isRunning = false;

    private Snake snake;
    private ArrayList<Score> scores = new ArrayList<>();


    public Board(Context context) {
        super(context);
        init();
    }

    public Board(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        createScore(w, h);
        snake.setBoardWidth(w);
        snake.setBoardHeight(h);
        if (!isRunning) {
            startGame();
        }
    }

    private void init() {
        snake = new Snake(this);
        paint.setColor(Color.RED);
    }


    public void createScore(int width, int height) {
        for (int i = 0; i < 4; i++) {
//            int x = random.nextInt(width - 20);
            int y = random.nextInt(height - 20);
            int x = width - 20;
            Score s = new Score(x, y);
            scores.add(s);
        }
    }

    private Runnable update = new Runnable() {
        @Override
        public void run() {
            snake.update();
            postInvalidate();

            if (isRunning) {
                Board.this.postDelayed(update, 1000 / 60);
            }
        }
    };

    public void startGame() {
        isRunning = true;
        post(update);
    }

    public void stopGame() {
        isRunning = false;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.BLUE);
        snake.draw(canvas);

        for (Score s : scores) {
            s.draw(canvas);
        }
    }

    public enum CollisionType {

        BORDER_RIGHT,
        BORDER_LEFT,
        BORDER_TOP,
        BORDER_BOTTOM,
        SCORE,
        SNAKE;

    }

    @Nullable
    public CollisionType didIHitAnything(RectF r) {
        for (Score s : scores) {
            if (RectF.intersects(r, s.getPosition())) {
                return CollisionType.SCORE;
            }
        }

        if (snake.hit(r)) {
            isRunning = false;
            return CollisionType.SNAKE;
        }
        return null;
    }
}
