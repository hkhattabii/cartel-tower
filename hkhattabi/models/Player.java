package hkhattabi.models;

import hkhattabi.models.weapon.Bullet;
import hkhattabi.models.weapon.Gun;
import hkhattabi.models.weapon.Shotgun;
import hkhattabi.models.weapon.Weapon;
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
    @Override
    public void equipWeapons() {
        this.weapons.add(new Gun(this));
        this.weapons.add(new Shotgun(this));
        this.weaponEquipped = this.weapons.get(0);
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

        m = (target.getY() - pointStart.getY()) / (target.getX() - pointStart.getX());
        p = this.position.getX() == 0 ? pointStart.getY() : pointStart.getY() - (m * pointStart.getX());

        if (weaponEquipped.getClip().size() > 0) {
            notifyUiView("Munition 16/16", ViewType.MUNITION_COUNT );
            Bullet bullet = weaponEquipped.getClip().get(weaponEquipped.getClip().size() - 1);
            bullet.setM(m);
            bullet.setP(p);
            bullet.setDirection(direction);
            bullet.position.setX(direction == 1 ? this.position.getX() + this.getWidth() : this.position.getX());
            bullet.position.setY(this.position.getY());
            bullet.view.setTranslateX(bullet.position.getX());
            bullet.view.setTranslateY(bullet.position.getY());
            bullet.exitBarrel();
            weaponEquipped.getClip().remove(bullet);
            notifyUiView("Munition " + weaponEquipped.getClip().size() + " / 16 ", ViewType.MUNITION_COUNT);

            if (!isReloading && weaponEquipped.getClip().size() == 0) {
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
    public void die(int ennemyCount) {

    }





}
