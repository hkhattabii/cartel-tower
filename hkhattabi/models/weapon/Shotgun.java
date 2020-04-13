package hkhattabi.models.weapon;

import hkhattabi.models.Human;
import java.util.ArrayList;

public class Shotgun extends Weapon {

    public Shotgun(Human usedBy) {
        super(usedBy);
        this.bulletSize = 16;
        this.name = "Fusil à pompe";
        this.damage = 100;
        this.rateOfFire = 12;
        this.clip = new ArrayList<>();
        this.maxBulletCount = 6;
        this.fillClip();
    }

}
