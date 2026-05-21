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
    private final Label titleLabel;
    private final Label MessageLabel;

    public GameView(Pane gamePane, Circle ball, Button playButton, Label scoreLabel, Label titleLabel, Label messageLabel) {
        this.gamePane = gamePane;
        this.ball = ball;
        this.playButton = playButton;
        this.scoreLabel = scoreLabel;
        this.titleLabel = titleLabel;
        this.MessageLabel = messageLabel;
        setupBall();
        setupScoreLabel();
    }

    private void setupBall() {
        ball.setRadius(35.0);
        ball.setCursor(Cursor.CROSSHAIR);
    }

    private void setupScoreLabel() {
        scoreLabel.setStyle(
                "-fx-font-size: 36px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: #e5c07b;"
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
        titleLabel.visibleProperty().bind(model.gameActiveProperty().not());

        scoreLabel.textProperty().bind(model.scoreProperty().asString());

        ball.setOnMouseEntered(e -> {
            if (model.isGameActive()) {
                ball.setOpacity(0.3);
                model.slowDown();           // Замедляем
                MessageLabel.setVisible(true);
            }
        });

        // ВОТ ЭТОТ ОБРАБОТЧИК БЫЛ УДАЛЕН - ВОЗВРАЩАЕМ!
        ball.setOnMouseExited(e -> {
            if (model.isGameActive()) {
                ball.setOpacity(1.0);
                model.restoreSpeed();        // ВОССТАНАВЛИВАЕМ СКОРОСТЬ
                MessageLabel.setVisible(false);
            }
        });

        ball.setOnMouseClicked(e -> {
            if (model.isGameActive()) {
                model.handleBallHit();
            }
        });

        // Скрывать надпись когда игра не активна
        MessageLabel.visibleProperty().bind(
                model.gameActiveProperty().and(ball.hoverProperty())
        );
    }
}