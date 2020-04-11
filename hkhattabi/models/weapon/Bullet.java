package hkhattabi.models.weapon;

import hkhattabi.models.Actor;
import hkhattabi.models.Ennemy;
import hkhattabi.models.Player;
import hkhattabi.models.Position;
import javafx.animation.AnimationTimer;
import javafx.scene.shape.Rectangle;

public class Bullet extends Actor {
    private Weapon shootedBy;
    private boolean isCollided;
    private double m;
    private double p;
    private int direction;
    private AnimationTimer animationTimer;
    public Bullet(Rectangle actorView, Weapon shootedBy) {
        super(actorView, new Position<Double>(0.0, 0.0));
        direction = 1;
        this.shootedBy = shootedBy;
    }



    public void exitBarrel() {
        animationTimer =  new AnimationTimer() {
            @Override
            public void handle(long l) {
                followTrajectory();
            }
        };
        animationTimer.start();
    }


    public void followTrajectory() {

        if (this.position.getX() < 0 || this.position.getY() > appView.getGameHeight() || this.position.getX() > appView.getGameWidth() ||this.position.getY() < 0) {
            animationTimer.stop();
            animationTimer = null;
        } else {


            for (int i = 0; i < this.shootedBy.getRateOfFire(); i++) {
                this.position.setX(this.position.getX() + direction);
                this.position.setY((this.m * this.position.getX()) + this.p);
                this.view.setTranslateX(this.position.getX());
                this.view.setTranslateY(this.position.getY());
            }
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

    public Weapon getShootedBy() {
        return shootedBy;
    }

    public void setIsCollided(boolean collided) {
        this.isCollided = collided;
    }

    public boolean isCollided() {
        return this.isCollided;
    }
}
