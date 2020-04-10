package hkhattabi.models;


import hkhattabi.models.weapon.Bullet;
import hkhattabi.models.weapon.Weapon;
import javafx.scene.shape.Rectangle;

import java.util.Timer;
import java.util.TimerTask;


public abstract class Human extends Actor {
    protected Weapon weaponEquiped;
    protected double health;
    protected boolean isReloading;
    protected boolean isLookingRight;


    public Human(Rectangle actorView, Position<Double> position) {
        super(actorView, position);
        this.isReloading = false;
    }

    public abstract void die();

    public void shoot(Position<Double> target) {
        double m  = 0;
        double p = 0;
        int direction = isLookingRight ? 1 : -1;
        Position<Double> pointStart = this.position;
        Position<Double> pointEnd = target;

        m = (pointEnd.getY() - pointStart.getY()) / (pointEnd.getX() - pointStart.getX());
        p = this.position.getX() == 0 ? pointStart.getY() : pointStart.getY() - (m * pointStart.getX());

        System.out.println(m);
        if (weaponEquiped.getClip().size() > 0) {
            Bullet bullet = weaponEquiped.getClip().get(weaponEquiped.getClip().size() - 1);
            bullet.setM(m);
            bullet.setP(p);
            bullet.setDirection(this.isLookingRight ?  1 : -1);
            bullet.position.setY(this.position.getY());
            bullet.position.setX(this.position.getX());
            bullet.view.setTranslateX(bullet.position.getX());
            bullet.view.setTranslateY(bullet.position.getY());
            bullet.exitBarrel();
            weaponEquiped.getClip().remove(bullet);
        } else if (isReloading == false) {
            isReloading = true;
            reload();
        }

    }


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

        timer.schedule(task, 1000);


    }

    public Weapon getWeaponEquiped() {
        return weaponEquiped;
    }


    public void setLookingRight(boolean lookingRight) {
        isLookingRight = lookingRight;
    }
}

