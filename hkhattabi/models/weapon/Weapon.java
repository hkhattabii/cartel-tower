package hkhattabi.models.weapon;

import hkhattabi.Factory;
import hkhattabi.models.*;

import java.util.ArrayList;

public abstract class Weapon extends Item {
    protected int bulletSize;
    protected int maxBulletCount;
    protected ArrayList<Bullet> clip;
    protected int rateOfFire;
    protected double damage;

    Weapon(String name, Human usedBy) {
        super(name, usedBy);
        this.usedBy = usedBy;
    }

    public void fillClip() {
        for (int i = 0; i < maxBulletCount; i++){
            this.clip.add(Factory.createBullet(this, Human.playerColor, Human.ennemyColor));
        }
    }

    public abstract void fire(Position<Double> target);

    public void computeBulletTrajectory(Bullet bullet, Position<Double> target) {
        double m;
        double p;
        Position<Double> pointStart = usedBy.getPosition();

        int direction = usedBy.isLookingRight() ? 1 : -1;



        m = (target.getY() - pointStart.getY()) / (target.getX() - pointStart.getX());
        p = pointStart.getX() == 0 ? pointStart.getY() : pointStart.getY() - (m * pointStart.getX());

        bullet.setTrajectory(new Trajectory(m,p,direction));
        bullet.getPosition().setX(direction == 1 ? usedBy.getPosition().getX() + usedBy.getWidth() : usedBy.getPosition().getX());
        bullet.getPosition().setY(usedBy.getPosition().getY());

        bullet.getView().setTranslateX(bullet.getPosition().getX());
        bullet.getView().setTranslateY(bullet.getPosition().getY());
        bullet.exitBarrel();
        getClip().remove(bullet);
    }



    public ArrayList<Bullet> getClip() {
        return clip;
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
    public String getName() {return name;}

    public int getMaxBulletCount() {
        return maxBulletCount;
    }

    public int getBulletSize() {
        return bulletSize;
    }
}
