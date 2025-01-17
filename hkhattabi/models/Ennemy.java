package hkhattabi.models;

import hkhattabi.models.weapon.Gun;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class Ennemy extends Human {
    private Position<Double> playerPosition;
    private double secondToWaitBeforeShoot;
    private Timeline timeline;


    public Ennemy (Rectangle actorView, Position<Double> position, Position<Double> playerPosition) {
        super(actorView, position);
        this.playerPosition = playerPosition;
        this.health = 100;
        this.equipWeapons();
        this.secondToWaitBeforeShoot = 1;
        this.timeline = new Timeline(new KeyFrame(Duration.millis(secondToWaitBeforeShoot), e -> {
            shoot(this.playerPosition);
            this.secondToWaitBeforeShoot = (5000 - 1000)  * Math.random();
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        this.timeline.play();
    }

    @Override
    public void equipWeapons() {
        this.weaponEquipped = new Gun(this);
    }
    @Override
    public void die(int ennemyCount) {
        this.isDead = true;
        this.notifyView("Ennemis restant : " + ennemyCount, NotifyType.ENNEMY_COUNT);
        timeline.stop();
    }

    @Override
    public void hurt(double health) {
        this.health = health;
    }

    public void aimPlayer(Position<Double> position) {
        this.playerPosition = position;
    }
}
