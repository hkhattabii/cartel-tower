package hkhattabi.models;
import javafx.scene.shape.Rectangle;


public class Actor {
    protected Rectangle view;
    protected Position<Double> position;
    public static double width = 16;
    public static double height = 16;


    public Actor(Rectangle actorView, Position<Double> position) {
        this.view = actorView;
        this.position = position;
        this.view.setTranslateX(position.getX());
        this.view.setTranslateY(position.getY());
    }


    public Rectangle getView() {
        return view;
    }


    public Position<Double> getPosition() {
        return position;
    }
}
