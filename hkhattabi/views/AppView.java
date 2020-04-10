package hkhattabi.views;
import hkhattabi.controllers.GameController;
import hkhattabi.models.Position;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;


public class AppView {
    protected GameController gameController;
    protected GameView gameView;
    protected UiView uiView;
    protected Pane appPane;
    protected int WIDTH;
    protected int HEIGHT;
    protected Position<Double> cursorPosition;
    protected HashMap<KeyCode, Boolean> keys;


    public AppView(GameController gameController) {
        this.gameController = gameController;
        this.gameView = new GameView();
        this.uiView = new UiView();
        this.appPane = new Pane();
        this.WIDTH = 1366;
        this.HEIGHT = 768;
        this.cursorPosition = new Position<Double>(0.0,0.0);
        this.keys = new HashMap();
    }

    public void onInit(Stage stage) {
        appPane.setPrefWidth(this.WIDTH);
        appPane.setPrefHeight(this.HEIGHT);

        Scene scene = new Scene(this.appPane, Color.BLACK);
        stage.setTitle("CARTEL TOWER");


        scene.setOnKeyPressed(event -> onKeypressed(event));
        scene.setOnKeyReleased(event -> onKeyReleased(event));
        scene.setOnMouseClicked(event -> gameController.onMouseClicked());
        scene.setOnMouseMoved(event -> gameController.onMouseMoved(event));


        this.appPane.getChildren().addAll(gameView.getGamePane(), uiView.getUiPane());
        this.appPane.setCursor(Cursor.CROSSHAIR);
        stage.setWidth(this.WIDTH);
        stage.setHeight(this.HEIGHT);
        stage.setScene(scene);
        stage.show();
    }


    public void onKeypressed(KeyEvent event) {
        this.keys.put(event.getCode(), true);
    }

    public void onKeyReleased(KeyEvent event) {
        this.keys.put(event.getCode(), false);
    }




    public boolean isPressed(Object key) {
        return this.keys.getOrDefault(key, false);
    }



    public int getGameWidth() {
        return this.WIDTH;
    }

    public int getGameHeight() {
        return this.HEIGHT;
    }


    public GameView getGameView() {
        return gameView;
    }

    public Position<Double> getCursorPosition() {
        return cursorPosition;
    }
}
