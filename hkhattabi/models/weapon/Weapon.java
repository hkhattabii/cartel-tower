package hkhattabi.models.weapon;

import javafx.geometry.Point2D;

import java.util.ArrayList;

public abstract class Weapon {
    protected ArrayList<Bullet> clip;
    protected Point2D rateOfFire;
    protected double damage;


    public ArrayList<Bullet> getClip() {
        return clip;
    }


    public abstract void fillClip();
}
