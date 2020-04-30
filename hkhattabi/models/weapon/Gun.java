package hkhattabi.models.weapon;

import hkhattabi.models.Human;
import hkhattabi.models.Player;
import hkhattabi.models.Position;
import hkhattabi.models.NotifyType;

import java.util.ArrayList;

public class Gun extends Weapon {
    public Gun(Human usedBy) {
        super("Colt 43", usedBy);
        this.bulletSize = 8;
        this.damage = 32;
        this.rateOfFire = 16;
        this.clip = new ArrayList<>();
        this.maxBulletCount = 16;
        this.fillClip();
    }

    @Override
    public void fire(Position<Double> target) {
        if (getClip().size() > 0) {
            Bullet bullet = getClip().get(getClip().size() - 1);
            computeBulletTrajectory(bullet, target);
        }

        if (usedBy instanceof Player) {
            notifyView("Munition " + clip.size() + " /  " + maxBulletCount, NotifyType.MUNITION_COUNT);
        }

    }

}
