package com.example.lr2v2;

import javafx.beans.binding.Bindings;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class GameView {
    private final Pane gamePane;
    private final Circle ball;
    private final Button playButton;

    public GameView(Pane gamePane, Circle ball, Button playButton) {
        this.gamePane = gamePane;
        this.ball = ball;
        this.playButton = playButton;
        setupBall();
    }

    private void setupBall() {
        ball.setRadius(20.0);
        ball.setFill(Color.GREEN);

        ball.setOnMouseEntered(e -> ball.setFill(Color.YELLOW));
        ball.setOnMouseExited(e -> ball.setFill(Color.GREEN));
        ball.setOnMousePressed(e -> ball.setFill(Color.RED));
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

        ball.visibleProperty().bind(model.gameActiveProperty());
        playButton.visibleProperty().bind(model.gameActiveProperty().not());

        ball.setOnMouseClicked(e -> {
            ball.setFill(Color.YELLOW);
            model.moveBallRandomly();
        });
    }
}