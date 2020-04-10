package hkhattabi.models.weapon;

import hkhattabi.models.Actor;
import hkhattabi.models.Position;
import javafx.animation.AnimationTimer;
import javafx.scene.shape.Rectangle;

public class Bullet extends Actor {

    private double m;
    private double p;
    private int direction;

    public Bullet(Rectangle actorView) {
        super(actorView, new Position<Double>(0.0, 0.0));
        direction = 1;
    }



    public void exitBarrel() {
        AnimationTimer animationTimer =  new AnimationTimer() {
            @Override
            public void handle(long l) {
                followTrajectory();
            }
        };
        animationTimer.start();
    }


    public void followTrajectory() {
        for (int i = 0; i < 16; i++) {
            this.position.setX(this.position.getX() + direction);
            this.position.setY((this.m * this.position.getX()) + this.p);
            this.view.setTranslateX(this.position.getX());
            this.view.setTranslateY(this.position.getY());
        }
    }


    public void setM(double m) {
        this.m = m;
    }


    public void setP(double p) {
        this.p = p;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }
}
