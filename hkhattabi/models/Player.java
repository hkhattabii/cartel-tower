package hkhattabi.models;

import hkhattabi.models.weapon.*;
import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;
import java.beans.PropertyChangeSupport;
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
    public void shoot(Position<Double> target) {
        this.isShooting = true;
        if (this.weaponEquipped instanceof Gun) {
            shootWithGun(target);
        } else if (this.weaponEquipped instanceof Shotgun) {
            shootWithShotGun(target);
        } else if (weaponEquipped instanceof  Bazooka) {
            shootWithBazzoka(target);
        }

        if (!isReloading && weaponEquipped.getClip().size() <= 0) {
            isReloading = true;
            reload();
        }
        notifyUiView("Munition " + weaponEquipped.getClip().size() + " /  " + weaponEquipped.getMaxBulletCount(), ViewType.MUNITION_COUNT);
    }



    public void shootWithShotGun(Position<Double> target) {
        if (weaponEquipped.getClip().size() >= 3) {
            Bullet bullet = weaponEquipped.getClip().get(weaponEquipped.getClip().size() - 1);
            Bullet bullet2 = weaponEquipped.getClip().get(weaponEquipped.getClip().size() - 2);
            Bullet bullet3 = weaponEquipped.getClip().get(weaponEquipped.getClip().size() - 3);

            computeBulletTrajectory(bullet, target);
            computeBulletTrajectory(bullet2, new Position<>(target.getX(), target.getY() - 32));
            computeBulletTrajectory(bullet3, new Position<>(target.getX(), target.getY() + 32));

        }
    }

    public void shootWithBazzoka(Position<Double> target) {
        if (weaponEquipped.getClip().size() > 0) {
            Bullet bullet = weaponEquipped.getClip().get(weaponEquipped.getClip().size() -1);
            computeBulletTrajectory(bullet, target);
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
    public void setWeaponEquiped(int index) {
        this.weaponEquipped = this.weapons.get(index);
        this.notifyUiView(weaponEquipped.getName(), ViewType.WEAPON_EQUIPED);
        this.notifyUiView("Munition : " + weaponEquipped.getClip().size() + "/" + weaponEquipped.getClip().size(), ViewType.MUNITION_COUNT );
    }


    @Override
    public void die(int ennemyCount) {

    }
}
