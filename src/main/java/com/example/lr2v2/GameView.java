package com.example.lr2v2;

import javafx.beans.binding.Bindings;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class GameView {
    private final Pane gamePane;
    private final Circle ball;
    private final Button playButton;
    private final Label scoreLabel;

    public GameView(Pane gamePane, Circle ball, Button playButton, Label scoreLabel) {
        this.gamePane = gamePane;
        this.ball = ball;
        this.playButton = playButton;
        this.scoreLabel = scoreLabel;
        setupBall();
        setupScoreLabel();
    }

    private void setupBall() {
        ball.setRadius(30.0);
        ball.setCursor(Cursor.CROSSHAIR);
    }

    private void setupScoreLabel() {
        scoreLabel.setStyle(
                "-fx-font-size: 24px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: #280137;" +
                        "-fx-background-color: #D3D3D3;" +
                        "-fx-padding: 10px;"
        );
    }

    public void bindToModel(GameModel model) {
        ball.centerXProperty().bind(Bindings.createDoubleBinding(
                () -> model.ballPositionProperty().get().getX(),
                model.ballPositionProperty()
        ));

        ball.centerYProperty().bind(Bindings.createDoubleBinding(
                () -> model.ballPositionProperty().get().getY(),
                model.ballPositionProperty()
        ));

        ball.fillProperty().bind(model.ballColorProperty());

        ball.visibleProperty().bind(model.gameActiveProperty());
        playButton.visibleProperty().bind(model.gameActiveProperty().not());

        scoreLabel.textProperty().bind(model.scoreProperty().asString());

        ball.setOnMouseEntered(e -> {
            if (model.isGameActive()) {
                ball.setOpacity(0.8);
            }
        });

        ball.setOnMouseExited(e -> {
            if (model.isGameActive()) {
                ball.setOpacity(1.0);
            }
        });

        ball.setOnMouseClicked(e -> {
            if (model.isGameActive()) {
                model.handleBallHit();
            }
        });
    }
}