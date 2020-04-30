package hkhattabi.models.weapon;

import hkhattabi.models.Human;
import hkhattabi.models.Player;
import hkhattabi.models.Position;
import hkhattabi.models.NotifyType;

import java.util.ArrayList;

public class Bazooka extends Weapon {
    public Bazooka(Human usedBy) {
        super("RPG", usedBy);
        this.bulletSize = 500;
        this.damage = 1000;
        this.rateOfFire = 8;
        this.clip = new ArrayList<>();
        this.maxBulletCount = 1;
        this.fillClip();
    }

    @Override
    public void fire(Position<Double> target) {
        if (clip.size() > 0) {
            Bullet bullet = clip.get(clip.size() -1);
            computeBulletTrajectory(bullet, target);
        }


        if (usedBy instanceof Player) {
            notifyView("Munition " + clip.size() + " /  " + maxBulletCount, NotifyType.MUNITION_COUNT);
        }
    }
}
