package hkhattabi;

import hkhattabi.models.Actor;
import hkhattabi.models.Player;
import hkhattabi.models.Position;
import hkhattabi.models.weapon.Bullet;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class Factory {

    public static Player createPlayer(double appWidth, double appHeight) {
        Position<Double> position = new Position<Double>(appWidth/2, appHeight/ 2);
        Rectangle rectangle =  new Rectangle(Actor.width, Actor.height);
        rectangle.setFill(Color.BLUE);
        return new Player(rectangle, position);
    }

    public static Bullet createBullet() {
        Rectangle rectangle = new Rectangle(4, 4);
        rectangle.setFill(Color.ORANGE);

        return new Bullet(rectangle);
    }

}
