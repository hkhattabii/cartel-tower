package hkhattabi.models.weapon;

import hkhattabi.Factory;
import hkhattabi.models.Human;
import java.util.ArrayList;

public class Gun extends Weapon {
    public Gun(Human usedBy) {
        super(usedBy);
        this.damage = 32;
        this.rateOfFire = 16;
        this.clip = new ArrayList<>();
        this.fillClip();
    }

    @Override
    public void fillClip() {
        for (int i = 0; i < 16; i++){
            this.clip.add(Factory.createBullet(this));
        }
    }
}
