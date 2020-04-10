package hkhattabi.controllers;
import hkhattabi.Factory;
import hkhattabi.models.Actor;
import hkhattabi.models.Player;
import hkhattabi.models.Position;
import hkhattabi.views.AppView;
import javafx.animation.AnimationTimer;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.util.HashMap;


public class GameController {
    private boolean gameStarted;
    private AppView appView;
    private Player currentPlayer;
    private HashMap<KeyCode, Boolean> keys;
    private int stageNumber;



    public GameController() {
        this.appView = new AppView(this);
        Actor.appView = appView;
        this.keys = new HashMap<KeyCode, Boolean>();
        this.gameStarted = false;
        this.stageNumber = 0;

    }

    public void startGame() {
        this.gameStarted = true;
        this.appView.removeMenu();
        this.appView.displayGame();
        this.currentPlayer = Factory.createPlayer(appView.getGameWidth(), appView.getGameHeight());
        this.spawnActor(currentPlayer);
        AnimationTimer ticks = new AnimationTimer() {
            @Override
            public void handle(long l) {
                update();
            }
        };
        ticks.start();
    }

    public void update() {
        if (this.isPressed(KeyCode.Z) && this.currentPlayer.getPosition().getY() > 0 + (Actor.height)) {
            this.currentPlayer.moveUp();
        }
        if (this.isPressed(KeyCode.Q) && this.currentPlayer.getPosition().getX() > 0 + (Actor.width)) {
            this.currentPlayer.moveLeft();
        }
        if (this.isPressed(KeyCode.S) && this.currentPlayer.getPosition().getY() < this.appView.getGameHeight() - (Actor.height * 4)) {
            this.currentPlayer.moveBottom();
        }
        if (this.isPressed(KeyCode.D) && this.currentPlayer.getPosition().getX() < this.appView.getGameWidth() -( Actor.width * 3)) {
            this.currentPlayer.moveRight();
        }
    }




    public void onKeyPressed(KeyCode keyCode) {
        if (gameStarted) {
            this.keys.put(keyCode, true);
        }

    }

    public void onKeyReleased(KeyCode keyCode) {
        if (gameStarted) {
            this.keys.put(keyCode, false);
        }

    }

    public void onMouseMoved(MouseEvent event) {
        if (gameStarted) {
            this.appView.getCursorPosition().setX(event.getX());
            this.appView.getCursorPosition().setY(event.getY());


            if (event.getX() < currentPlayer.getPosition().getX()) {
                this.currentPlayer.setLookingRight(false);
            } else {
                this.currentPlayer.setLookingRight(true);
            }
        }

    }

    public void onMouseClicked(Position<Double> target) {
        if (gameStarted) {
            if (this.currentPlayer.getWeaponEquiped().getClip().size() > 0 ) {
                spawnActor(this.currentPlayer.getWeaponEquiped().getClip().get(this.currentPlayer.getWeaponEquiped().getClip().size() - 1));
            }
            this.currentPlayer.shoot(this.appView.getCursorPosition());
        }

    }



    public boolean isPressed(Object key) {
        return this.keys.getOrDefault(key, false);
    }





    public void spawnActor(Actor actor) {
        appView.getGameView().addActor(actor);
    }

    public AppView getAppView() {
        return this.appView;
    }
}
