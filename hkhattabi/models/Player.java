package hkhattabi.models;

import hkhattabi.models.weapon.Bullet;
import hkhattabi.models.weapon.Gun;
import hkhattabi.models.weapon.Shotgun;
import hkhattabi.models.weapon.Weapon;
import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.Arrays;


public class Player extends  Human {
    private Point2D velocity;
    private ArrayList<Weapon> weapons;
    private int score;



    public Player(Rectangle actorView, Position<Double> position) {
        super(actorView, position);
        this.health = 200;
        this.velocity = new Point2D(4, 4);
        this.score = 0;
        this.equipWeapons();

    }

    @Override
    public void equipWeapons() {
        this.weapons = new ArrayList<>();
        this.weapons.add(new Gun(this));
        this.weapons.add(new Shotgun(this));
        this.weaponEquiped = this.weapons.get(0);
    }


    public void moveY(double value) {
        for (int i = 0; i < Math.abs(value); i++) {
            this.position.setY(this.position.getY() + value);
            this.view.setTranslateY(this.position.getY());
        }
    }

    public void moveX(double value) {
        for (int i = 0; i < Math.abs(value); i++) {
            this.position.setX(this.position.getX() + value);
            this.view.setTranslateX(this.position.getX());
        }
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
            notifyView("Munition 16/16", ViewType.MUNITION_COUNT );
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
            notifyView("Munition " + weaponEquiped.getClip().size() + " / 16 ", ViewType.MUNITION_COUNT);

            if (isReloading == false && weaponEquiped.getClip().size() == 0) {
                isReloading = true;
                reload();

            }
        }

    }


    public void moveUp() {
        this.moveY(-this.velocity.getY());
    }

    public void moveBottom() {
        this.moveY(this.velocity.getY());
    }

    public void moveRight() {
        this.moveX(this.velocity.getX());
    }
    public void moveLeft() {
        this.moveX(- this.velocity.getX());
    }



    @Override
    public void die() {

    }





}
