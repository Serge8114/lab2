package com.example.lr2v2;

import javafx.animation.AnimationTimer;
import javafx.beans.property.*;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

import java.util.Random;

public class GameModel {
    private final IntegerProperty score = new SimpleIntegerProperty(0);
    private final BooleanProperty gameActive = new SimpleBooleanProperty(false);
    private final ObjectProperty<Point2D> ballPosition = new SimpleObjectProperty<>(new Point2D(0, 0));
    private final ObjectProperty<Color> ballColor = new SimpleObjectProperty<>(Color.GREEN);
    private final Random random = new Random();

    private double gamePaneWidth;
    private double gamePaneHeight;
    private double velocityX;
    private double velocityY;
    private AnimationTimer timer;

    private static final double NORMAL_SPEED = 2;
    private static final double SLOWED_SPEED = 1;
    private double currentSpeed = NORMAL_SPEED;
    private static final double DIRECTION_CHANGE_PROBABILITY = 0.008;

    public void startGame(double width, double height) {
        this.gamePaneWidth = width;
        this.gamePaneHeight = height;

        double startX = width / 2;
        double startY = height / 2;
        setBallPosition(new Point2D(startX, startY));

        double angle = random.nextDouble() * 2 * Math.PI;
        velocityX = Math.cos(angle) * NORMAL_SPEED;
        velocityY = Math.sin(angle) * NORMAL_SPEED;

        setGameActive(true);
        setScore(0);
        setBallColor(Color.GREEN);
        startAnimation();
    }

    public void stopGame() {
        setGameActive(false);
        if (timer != null) {
            timer.stop();
        }
    }

    private void startAnimation() {
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (isGameActive()) {
                    updateBallPosition();
                }
            }
        };
        timer.start();
    }

    private void updateBallPosition() {
        Point2D currentPos = ballPosition.get();

        maybeChangeDirection();

        double newX = currentPos.getX() + (velocityX / NORMAL_SPEED * currentSpeed);
        double newY = currentPos.getY() + (velocityY / NORMAL_SPEED * currentSpeed);

        if (newX < 30 || newX > gamePaneWidth - 30) {
            velocityX *= -1;
            newX = Math.max(30, Math.min(gamePaneWidth - 30, newX));
        }

        if (newY < 30 || newY > gamePaneHeight - 30) {
            velocityY *= -1;
            newY = Math.max(30, Math.min(gamePaneHeight - 30, newY));
        }

        setBallPosition(new Point2D(newX, newY));
    }

    private void maybeChangeDirection() {
        if (random.nextDouble() < DIRECTION_CHANGE_PROBABILITY) {
            double angle = random.nextDouble() * 2 * Math.PI;
            velocityX = Math.cos(angle) * NORMAL_SPEED;
            velocityY = Math.sin(angle) * NORMAL_SPEED;
        }
    }

    public void handleBallHit() {
        if (isGameActive()) {
            setScore(getScore() + 1);
        }
    }

    public void slowDown() {
        currentSpeed = SLOWED_SPEED;
    }

    public void restoreSpeed() {
        currentSpeed = NORMAL_SPEED;
    }

    public IntegerProperty scoreProperty() {
        return score;
    }

    public int getScore() {
        return score.get();
    }

    public void setScore(int value) {
        score.set(value);
    }

    public BooleanProperty gameActiveProperty() {
        return gameActive;
    }

    public ObjectProperty<Point2D> ballPositionProperty() {
        return ballPosition;
    }

    public ObjectProperty<Color> ballColorProperty() {
        return ballColor;
    }

    public boolean isGameActive() {
        return gameActive.get();
    }

    public void setGameActive(boolean active) {
        gameActive.set(active);
    }

    public void setBallPosition(Point2D position) {
        ballPosition.set(position);
    }

    public void setBallColor(Color color) {
        ballColor.set(color);
    }
}