package hkhattabi.controllers;
import hkhattabi.Factory;
import hkhattabi.models.Actor;
import hkhattabi.models.Player;
import hkhattabi.views.AppView;
import javafx.animation.AnimationTimer;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;


public class GameController {
    private AppView appView;
    private Player currentPlayer;
    private int ennemyCount;




    public GameController() {
        this.appView = new AppView(this);
        this.ennemyCount = 1;
        this.currentPlayer = Factory.createPlayer(appView.getGameWidth(), appView.getGameHeight());
        this.spawnActor(currentPlayer);
    }

    public void startGame() {
        AnimationTimer ticks = new AnimationTimer() {
            @Override
            public void handle(long l) {
                update();
            }
        };
        ticks.start();
    }

    public void update() {
        if (this.appView.isPressed(KeyCode.Z) && this.currentPlayer.getPosition().getY() > 0 + (Actor.height)) {
            this.currentPlayer.moveUp();
        }
        if (this.appView.isPressed(KeyCode.Q) && this.currentPlayer.getPosition().getX() > 0 + (Actor.width)) {
            this.currentPlayer.moveLeft();
        }
        if (this.appView.isPressed(KeyCode.S) && this.currentPlayer.getPosition().getY() < this.appView.getGameHeight() - (Actor.height * 4)) {
            this.currentPlayer.moveBottom();
        }
        if (this.appView.isPressed(KeyCode.D) && this.currentPlayer.getPosition().getX() < this.appView.getGameWidth() -( Actor.width * 3)) {
            this.currentPlayer.moveRight();
        }
    }

    public void onMouseMoved(MouseEvent event) {
        this.appView.getCursorPosition().setX(event.getX());
        this.appView.getCursorPosition().setY(event.getY());

        if (event.getX() < currentPlayer.getPosition().getX()) {
            this.currentPlayer.setLookingRight(false);
        } else {
            this.currentPlayer.setLookingRight(true);
        }
    }

    public void onMouseClicked() {
        if (this.currentPlayer.getWeaponEquiped().getClip().size() > 0 ) {
            spawnActor(this.currentPlayer.getWeaponEquiped().getClip().get(this.currentPlayer.getWeaponEquiped().getClip().size() - 1));
        }
        this.currentPlayer.shoot(this.appView.getCursorPosition());

    }

    public void spawnActor(Actor actor) {
        appView.getGameView().addActor(actor);
    }

    public AppView getAppView() {
        return this.appView;
    }
}
