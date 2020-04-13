package hkhattabi.models.weapon;

import hkhattabi.models.Human;

import java.util.ArrayList;

public class Bazooka extends Weapon {
    public Bazooka(Human usedBy) {
        super(usedBy);
        this.bulletSize = 500;
        this.name = "Bazooka";
        this.damage = 1000;
        this.rateOfFire = 8;
        this.clip = new ArrayList<>();
        this.maxBulletCount = 1;
        this.fillClip();
    }
}
