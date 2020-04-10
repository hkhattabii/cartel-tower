package hkhattabi.models.weapon;

import hkhattabi.Factory;
import javafx.geometry.Point2D;

import java.util.ArrayList;

public class Gun extends Weapon {

    public Gun() {
        this.damage = 32;
        this.rateOfFire = new Point2D(16,16);
        this.clip = new ArrayList<>();
        this.fillClip();
    }


    @Override
    public void fillClip() {
        for (int i = 0; i < 16; i++){
            this.clip.add(Factory.createBullet());
        }
    }
}
