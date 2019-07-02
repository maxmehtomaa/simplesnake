package com.choicely.snake;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Snake {

    private static final int SNAKE_PART_SIZE = 20;
    private boolean gameOver;

    private final Paint paint = new Paint();

    private static final int UP = 0;
    private static final int RIGHT = 1;
    private static final int LEFT = 2;
    private static final int DOWN = 3;

    private int speed;
    private Board board;

//    private RectF headRect;

    private int direction;
    private int snakeLength = 10;

    private final List<RectF> snakeParts = new ArrayList<>();


    public Snake(Board board) {
        this.board = board;
        init();
    }

    private void init() {
        RectF headRect = new RectF(0, 0, SNAKE_PART_SIZE, SNAKE_PART_SIZE);
        snakeParts.add(headRect);
        direction = RIGHT;
        speed = 20;
        paint.setColor(Color.RED);
    }

    public void update() {
        switch (direction) {
            case UP:
                move(0, -speed);
                break;
            case RIGHT:
                move(speed, 0);
                break;
            case LEFT:
                move(-speed, 0);
                break;
            case DOWN:
                move(0, speed);
                break;
            default:
                move(0, speed);
        }

    }

    public void draw(Canvas canvas) {
        //canvas.drawRect(point.x-snakeLength, point.y-snakeLength, point.x+snakeLength, point.y+snakeLength, paint);
        for (RectF r : snakeParts) {
            canvas.drawRect(r, paint);
        }
    }

    public void move(int xChange, int yChange) {
        RectF previous = snakeParts.get(0);

        RectF r = new RectF(previous.left, previous.top, previous.right, previous.bottom);
        r.offset(xChange, yChange);
        boolean changedDirection = false;
        if (r.right > board.getWidth()) {
            r.offsetTo(board.getWidth() - r.width(), r.top);
            changedDirection = true;
        }

        if (r.bottom > board.getHeight()) {
            r.offsetTo(r.left, board.getHeight() - r.height());
            changedDirection = true;
        }

        if (r.left < 0) {
            r.offsetTo(0, r.top);
            changedDirection = true;
        }

        if (r.top < 0) {
            r.offsetTo(r.left, 0);
            changedDirection = true;
        }

        if (changedDirection) {
            switch (direction) {
                case RIGHT:
                    direction = DOWN;
                    break;
                case DOWN:
                    direction = LEFT;
                    break;
                case LEFT:
                    direction = UP;
                    break;
                case UP:
                    direction = RIGHT;
                    break;
            }
        }

        // TODO: did I hit something other than

        Board.CollisionType collisionType = board.didIHitAnything(r);
        if (collisionType != null) {
            switch (collisionType) {
                case BORDER_RIGHT:
                    if (r.right > board.getWidth()) {
                        r.offsetTo(board.getWidth() - r.width(), r.top);
                    }
                    break;

                case BORDER_BOTTOM:
                    if (r.bottom > board.getHeight()) {
                        r.offsetTo(r.left, board.getHeight() - r.height());
                    }
                    break;

                case BORDER_LEFT:
                    if (r.left < 0) {
                        r.offsetTo(0, r.top);
                    }
                    break;

                case BORDER_TOP:
                    if (r.top < 0) {
                        r.offsetTo(r.left, 0);
                    }
                    break;
                case SCORE:
                    snakeLength += 2;
                    break;
                case SNAKE:
                    gameOver = true;
                    // TODO: SNAKE IS DEAD!
                    return;
            }
        }

        snakeParts.add(0, r);
        if (snakeParts.size() > snakeLength) {
            snakeParts.remove(snakeParts.size() - 1);
        }
//        Log.d(TAG, "move: " + headRect.left + ":" +  headRect.top);
    }

    public void setBoardWidth(int boardWidth) {
//        this.boardWidth = boardWidth;
    }

    public void setBoardHeight(int boardHeight) {
//        this.boardHeight = boardHeight;
    }

    public boolean hit(RectF r) {
        return false;
    }
}
