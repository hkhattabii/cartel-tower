package hkhattabi.models.weapon;

import hkhattabi.models.Actor;
import hkhattabi.models.Position;
import hkhattabi.models.Trajectory;
import javafx.animation.AnimationTimer;
import javafx.scene.shape.Rectangle;

public class Bullet extends Actor {
    private Weapon shootedBy;
    private boolean isCollided;
    private Trajectory trajectory;
    private AnimationTimer animationTimer;
    public Bullet(Rectangle actorView, Weapon shootedBy) {
        super(actorView, new Position<Double>(0.0, 0.0));
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
        for (int i = 0; i < this.shootedBy.getRateOfFire(); i++) {
            this.position.setX(this.position.getX() + trajectory.getDirection());
            this.position.setY((this.trajectory.getM() * this.position.getX()) + this.trajectory.getP());
            this.view.setTranslateX(this.position.getX());
            this.view.setTranslateY(this.position.getY());
        }
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
    public void stopAnimationTimer() {
        animationTimer.stop();
        animationTimer = null;
    }

    public void setTrajectory(Trajectory trajectory) {
        this.trajectory = trajectory;
    }
}
