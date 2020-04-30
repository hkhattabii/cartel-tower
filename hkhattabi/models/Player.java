package hkhattabi.models;

import hkhattabi.models.weapon.*;
import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;


public class Player extends  Human {

    private Point2D velocity;
    private ArrayList<Weapon> weapons = new ArrayList<>();



    public Player(Rectangle actorView, Position<Double> position) {
        super(actorView, position);
        this.health = 200;
        this.velocity = new Point2D(4, 4);
        this.equipWeapons();

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
    public void equipWeapons() {
        this.weapons.add(new Gun(this));
        this.weapons.add(new Shotgun(this));
        this.weapons.add(new Bazooka(this));
        this.weaponEquipped = this.weapons.get(0);
    }


    @Override
    public void hurt(double health) {
        this.health = health;
        this.notifyView("Vie : " + this.health, NotifyType.HEALTH_COUNT);
    }


    @Override
    public void die(int ennemyCount) {

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

    public void setWeaponEquiped(int index) {
        this.weaponEquipped = this.weapons.get(index);
        this.notifyView(weaponEquipped.getName(), NotifyType.WEAPON_EQUIPED);
        this.notifyView("Munition : " + weaponEquipped.getClip().size() + "/" + weaponEquipped.getClip().size(), NotifyType.MUNITION_COUNT );
    }



}
