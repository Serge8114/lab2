package com.example.lr2v2;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.ResourceBundle;

public class GameController implements Initializable {
    @FXML
    private Pane gamePane;
    @FXML
    private Circle ball;
    @FXML
    private Button playButton;
    @FXML
    private Label scoreLabel;
    @FXML
    private Label titleLabel;
    @FXML
    private Label MessageLabel;

    private GameModel gameModel;
    private GameView gameView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        gameModel = new GameModel();
        gameView = new GameView(gamePane, ball, playButton, scoreLabel, titleLabel, MessageLabel);

        gameView.bindToModel(gameModel);

        playButton.setOnAction(e -> {
            gameModel.startGame(gamePane.getWidth(), gamePane.getHeight());
        });
    }
}