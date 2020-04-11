package hkhattabi;

import hkhattabi.models.*;
import hkhattabi.models.weapon.Bullet;
import hkhattabi.models.weapon.Weapon;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class Factory {

    public static Player createPlayer(double appWidth, double appHeight) {
        Position<Double> position = new Position<Double>(appWidth/2, appHeight/ 2);
        Rectangle rectangle =  new Rectangle(Actor.width, Actor.height);
        rectangle.setFill(Color.BLUE);
        return new Player(rectangle, position);
    }

    public static Bullet createBullet(Weapon weapon) {
        boolean usedByPlayer = (weapon.getUsedBy() instanceof Player);
        Rectangle rectangle = new Rectangle(4, 4);
        rectangle.setFill(usedByPlayer ? Color.BLUE : Color.RED);

        return new Bullet(rectangle, weapon);
    }

    public static Ennemy createEnnemy(double appWidth, double appHeight, Position playerPosition) {
        double randomPosX = appWidth * Math.random();
        double randomPosY = appHeight * Math.random();
        Position<Double> position = new Position<Double>(randomPosX, randomPosY);
        Rectangle rectangle = new Rectangle(Actor.width, Actor.height);
        rectangle.setFill(Color.RED);
        return new Ennemy(rectangle, position, playerPosition);
    }

}
