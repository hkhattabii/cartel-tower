package hkhattabi.models.weapon;

import hkhattabi.models.Human;
import java.util.ArrayList;

public class Gun extends Weapon {
    public Gun(Human usedBy) {
        super(usedBy);
        this.bulletSize = 8;
        this.name = "Pistolet";
        this.damage = 32;
        this.rateOfFire = 16;
        this.clip = new ArrayList<>();
        this.maxBulletCount = 16;
        this.fillClip();
    }

}
