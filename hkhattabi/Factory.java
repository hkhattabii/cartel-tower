package hkhattabi;

import hkhattabi.models.*;
import hkhattabi.models.weapon.Bullet;
import hkhattabi.models.weapon.Weapon;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class Factory {
    public static Player createPlayer(double appWidth, double appHeight, Color playerColor) {
        Position<Double> position = new Position<>(appWidth / 2, appHeight / 2);
        Rectangle rectangle =  new Rectangle(Actor.width, Actor.height);
        rectangle.setFill(playerColor);
        return new Player(rectangle, position);
    }
    public static Bullet createBullet(Weapon weapon, Color playerColor, Color ennemyColor) {
        boolean usedByPlayer = (weapon.getUsedBy() instanceof Player);
        Rectangle rectangle = new Rectangle(weapon.getBulletSize(), weapon.getBulletSize());
        rectangle.setFill(usedByPlayer ? playerColor : ennemyColor);

        return new Bullet(rectangle, weapon);
    }
    public static Ennemy createEnemy(double appWidth, double appHeight, Position<Double> playerPosition, Color ennemyColor) {
        double randomPosX = (appWidth * Math.random()) - Actor.width;
        double randomPosY = (appHeight * Math.random()) - Actor.height;
        Position<Double> position = new Position<>(randomPosX, randomPosY);
        Rectangle rectangle = new Rectangle(Actor.width, Actor.height);
        rectangle.setFill(ennemyColor);
        return new Ennemy(rectangle, position, playerPosition);
    }

}
