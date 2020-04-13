package hkhattabi.models;
import hkhattabi.models.weapon.Bullet;
import hkhattabi.models.weapon.Weapon;
import javafx.scene.shape.Rectangle;

import java.beans.PropertyChangeSupport;
import java.util.Timer;
import java.util.TimerTask;


public abstract class Human extends Actor {
    protected PropertyChangeSupport propertyChangeSupport;
    protected Weapon weaponEquipped;
    protected double health;
    protected boolean isReloading;
    protected boolean isLookingRight;
    protected  boolean isShooting;
    protected boolean isDead;

    public Human(Rectangle actorView, Position<Double> position) {
        super(actorView, position);
        this.isReloading = false;
    }

    public abstract void equipWeapons();
    public abstract void die(int ennemyCount);
    public abstract void shoot(Position<Double> target);


    public void shootWithGun(Position<Double> target) {
        if (weaponEquipped.getClip().size() > 0) {
            Bullet bullet = weaponEquipped.getClip().get(weaponEquipped.getClip().size() - 1);
            computeBulletTrajectory(bullet, target);
        }
    }

    public void computeBulletTrajectory(Bullet bullet, Position<Double> target) {
        double m;
        double p;
        Position<Double> pointStart = position;
        int direction = isLookingRight ? 1 : -1;

        m = (target.getY() - pointStart.getY()) / (target.getX() - pointStart.getX());
        p = pointStart.getX() == 0 ? pointStart.getY() : pointStart.getY() - (m * pointStart.getX());

        bullet.setM(m);
        bullet.setP(p);
        bullet.setDirection(direction);
        bullet.position.setX(direction == 1 ? this.position.getX() + this.getWidth() : this.position.getX());
        bullet.position.setY(this.position.getY());
        bullet.view.setTranslateX(bullet.position.getX());
        bullet.view.setTranslateY(bullet.position.getY());
        bullet.exitBarrel();
        weaponEquipped.getClip().remove(bullet);
    }


    public void reload() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                weaponEquipped.fillClip();
                isReloading = false;
                timer.cancel();
            }
        };
        timer.schedule(task, 500);
    }

    public Weapon getWeaponEquipped() {
        return weaponEquipped;
    }
    public void setLookingRight(boolean lookingRight) {
        isLookingRight = lookingRight;
    }
    public void setShooting(boolean shooting) {
        isShooting = shooting;
    }
    public boolean isShooting() {
        return isShooting;
    }
    public boolean isDead() {
        return isDead;
    }
    public double getHealth() {
        return health;
    }
    public void setHealth(double health) {
        this.health = health;
        if (this instanceof  Player) {
            this.notifyUiView("Vie : " + this.health, ViewType.HEALTH_COUNT);
        }
    }
}

