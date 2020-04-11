package hkhattabi.models.weapon;

import hkhattabi.models.Human;
import javafx.geometry.Point2D;

import java.util.ArrayList;

public abstract class Weapon {
    protected boolean isTimeSlowedDown;
    protected Human usedBy;
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

    public boolean isTimeSlowedDown() {
        return isTimeSlowedDown;
    }

    public void setTimeSlowedDown(boolean timeSlowedDown) {
        isTimeSlowedDown = timeSlowedDown;
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
