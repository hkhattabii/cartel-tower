package hkhattabi.models.weapon;

import hkhattabi.models.Human;
import java.util.ArrayList;

public abstract class Weapon {
    protected Human usedBy;
    protected boolean isTimeSlowedDown;
    protected ArrayList<Bullet> clip;
    protected int rateOfFire;
    protected double damage;
    Weapon(Human usedBy) {
        this.usedBy = usedBy;
        this.isTimeSlowedDown = false;
    }
    public ArrayList<Bullet> getClip() {
        return clip;
    }
    public abstract void fillClip();
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
}
