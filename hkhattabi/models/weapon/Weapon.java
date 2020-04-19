package hkhattabi.models.weapon;

import hkhattabi.Factory;
import hkhattabi.models.Human;
import java.util.ArrayList;

public abstract class Weapon {
    protected int bulletSize;
    protected int maxBulletCount;
    protected String name;
    protected Human usedBy;
    protected ArrayList<Bullet> clip;
    protected int rateOfFire;
    protected double damage;

    Weapon(Human usedBy) {
        this.usedBy = usedBy;
    }

    public ArrayList<Bullet> getClip() {
        return clip;
    }

    public void fillClip() {
        for (int i = 0; i < maxBulletCount; i++){
            this.clip.add(Factory.createBullet(this, Human.playerColor, Human.ennemyColor));
        }
    }
    public double getDamage() {
        return damage;
    }
    public void setRateOfFire(int rateOfFire) {
        this.rateOfFire = rateOfFire;
    }
    public int getRateOfFire() {
        return rateOfFire;
    }
    public Human getUsedBy() {
        return usedBy;
    }
    public String getName() {return name;}

    public int getMaxBulletCount() {
        return maxBulletCount;
    }

    public int getBulletSize() {
        return bulletSize;
    }
}
