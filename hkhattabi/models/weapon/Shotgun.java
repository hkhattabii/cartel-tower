package hkhattabi.models.weapon;

import hkhattabi.models.Human;
import hkhattabi.models.Player;
import hkhattabi.models.Position;
import hkhattabi.models.NotifyType;

import java.util.ArrayList;

public class Shotgun extends Weapon {

    public Shotgun(Human usedBy) {
        super("SPAS 12", usedBy);
        this.bulletSize = 16;
        this.damage = 100;
        this.rateOfFire = 12;
        this.clip = new ArrayList<>();
        this.maxBulletCount = 6;
        this.fillClip();
    }

    @Override
    public void fire(Position<Double> target) {
        if (clip.size() >= 3) {
            Bullet bullet = clip.get(clip.size() - 1);
            Bullet bullet2 = clip.get(clip.size() - 2);
            Bullet bullet3 = clip.get(clip.size() - 3);

            computeBulletTrajectory(bullet, target);
            computeBulletTrajectory(bullet2, new Position<>(target.getX(), target.getY() - 32));
            computeBulletTrajectory(bullet3, new Position<>(target.getX(), target.getY() + 32));

        }


        if (usedBy instanceof Player) {
            notifyView("Munition " + clip.size() + " /  " + maxBulletCount, NotifyType.MUNITION_COUNT);
        }

    }

}
