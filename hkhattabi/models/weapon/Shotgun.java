package hkhattabi.models.weapon;

import hkhattabi.Factory;
import javafx.geometry.Point2D;

import java.util.ArrayList;

public class Shotgun extends Weapon {

    public Shotgun() {
        this.damage = 32;
        this.rateOfFire = new Point2D(4,4);
        this.clip = new ArrayList<>();
        this.fillClip();
    }

    @Override
    public void fillClip() {
        for (int i = 0; i < 6; i++) {
            this.clip.add(Factory.createBullet());
        }
    }
}
