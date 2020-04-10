package hkhattabi.models;

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

    public void equipWeapons() {
        this.weapons = new ArrayList<Weapon>();
        this.weapons.add(new Gun());
        this.weapons.add(new Shotgun());
        this.weaponEquiped = this.weapons.get(0);
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
