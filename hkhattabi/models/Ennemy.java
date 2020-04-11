package hkhattabi.models;

import hkhattabi.models.weapon.Bullet;
import hkhattabi.models.weapon.Gun;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class Ennemy extends Human {
    private Position<Double> playerPosition;
    private Timeline timeline;

    public Ennemy (Rectangle actorView, Position<Double> position, Position<Double> playerPosition) {
        super(actorView, position);
        this.playerPosition = playerPosition;
        this.health = 100;
        this.equipWeapons();
        this.timeline = new Timeline(new KeyFrame(Duration.millis((5000 - 1000)  * Math.random()), e -> shoot(this.playerPosition)));
        timeline.setCycleCount(Timeline.INDEFINITE);
        this.timeline.play();
    }




    public void aimPlayer(Position<Double> position) {
        this.playerPosition = position;
    }


    @Override
    public void equipWeapons() {
        this.weaponEquiped = new Gun(this);
    }

    @Override
    public void die() {
        this.isDead = true;
        timeline.stop();
    }

    @Override
    public void shoot(Position<Double> target) {
        this.isShooting = true;
        double m;
        double p;
        int direction = isLookingRight ? 1 : -1;
        Position<Double> pointStart = this.position;
        Position<Double> pointEnd = target;

        m = (pointEnd.getY() - pointStart.getY()) / (pointEnd.getX() - pointStart.getX());
        p = this.position.getX() == 0 ? pointStart.getY() : pointStart.getY() - (m * pointStart.getX());


        if (weaponEquiped.getClip().size() > 0) {
            Bullet bullet = weaponEquiped.getClip().get(weaponEquiped.getClip().size() - 1);
            bullet.setM(m);
            bullet.setP(p);
            bullet.setDirection(direction);
            bullet.position.setX(direction == 1 ? this.position.getX() + this.getWidth() : this.position.getX());
            bullet.position.setY(this.position.getY());
            bullet.view.setTranslateX(bullet.position.getX());
            bullet.view.setTranslateY(bullet.position.getY());
            bullet.exitBarrel();
            weaponEquiped.getClip().remove(bullet);

            if (isReloading == false && weaponEquiped.getClip().size() == 0) {
                isReloading = true;
                reload();

            }
        }
    }
}
