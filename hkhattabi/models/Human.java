package hkhattabi.models;


import hkhattabi.models.weapon.Weapon;
import javafx.scene.shape.Rectangle;

import java.util.Timer;
import java.util.TimerTask;


public abstract class Human extends Actor {
    protected Weapon weaponEquiped;
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
    public abstract void die();

    public abstract void shoot(Position<Double> target);


    public void reload() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                weaponEquiped.fillClip();
                isReloading = false;
                timer.cancel();
            }
        };

        timer.schedule(task, 500);
    }

    public Weapon getWeaponEquiped() {
        return weaponEquiped;
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

    public void setDead(boolean dead) {
        isDead = dead;
    }

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = health;
    }
}

