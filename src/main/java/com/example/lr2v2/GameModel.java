package com.example.lr2v2;

import javafx.beans.property.*;
import javafx.geometry.Point2D;

import java.util.Random;

public class GameModel {
    private final BooleanProperty gameActive = new SimpleBooleanProperty(false);
    private final ObjectProperty<Point2D> ballPosition = new SimpleObjectProperty<>(new Point2D(0, 0));
    private final Random random = new Random();
    private double gamePaneWidth;
    private double gamePaneHeight;

    public void startGame(double width, double height) {
        this.gamePaneWidth = width;
        this.gamePaneHeight = height;
        setBallPosition(new Point2D(width / 2, height / 2));
        setGameActive(true);
    }

    public void stopGame() {
        setGameActive(false);
    }

    public void moveBallRandomly() {
        if (isGameActive()) {
            double x = random.nextDouble() * (gamePaneWidth - 40) + 20;
            double y = random.nextDouble() * (gamePaneHeight - 40) + 20;
            setBallPosition(new Point2D(x, y));
        }
    }

    public BooleanProperty gameActiveProperty() {
        return gameActive;
    }

    public ObjectProperty<Point2D> ballPositionProperty() {
        return ballPosition;
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
}