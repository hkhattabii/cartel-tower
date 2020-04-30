package hkhattabi.models;
import hkhattabi.models.weapon.Weapon;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import java.util.Timer;
import java.util.TimerTask;


public abstract class Human extends Actor {
    public static Color playerColor = Color.BLUE;
    public static Color ennemyColor = Color.RED;
    protected Weapon weaponEquipped;
    protected double health;
    protected boolean isReloading;
    protected boolean isLookingRight;
    protected  boolean isShooting;
    protected boolean isDead;

    public Human(Rectangle actorView, Position<Double> position) {
        super(actorView, position);
        this.isReloading = false;
    }

    public abstract void equipWeapons();
    public abstract void die(int ennemyCount);

    public void shoot(Position<Double> target) {
        this.isShooting = true;
        weaponEquipped.fire(target);

        if (!isReloading && weaponEquipped.getClip().size() <= 0) {
            isReloading = true;
            reload();
        }

    }

    public void reload() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                weaponEquipped.fillClip();
                isReloading = false;
                timer.cancel();
            }
        };
        timer.schedule(task, 500);
    }


    public abstract void hurt(double health);

    public Weapon getWeaponEquipped() {
        return weaponEquipped;
    }
    public void setLookingRight(boolean lookingRight) {
        isLookingRight = lookingRight;
    }
    public void setShooting(boolean shooting) {
        isShooting = shooting;
    }

    public boolean isLookingRight() {
        return isLookingRight;
    }

    public boolean isShooting() {
        return isShooting;
    }
    public boolean isDead() {
        return isDead;
    }
    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = health;
        if (this instanceof  Player) {
            this.notifyView("Vie : " + this.health, NotifyType.HEALTH_COUNT);
        }
    }
}

