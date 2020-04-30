package hkhattabi.models;
import hkhattabi.views.GameView;
import javafx.scene.shape.Rectangle;



abstract public class Actor extends Model {
    public static double width = 16;
    public static double height = 16;
    protected Rectangle view;
    protected Position<Double> position;


    public Actor(Rectangle actorView, Position<Double> position) {
        this.view = actorView;
        this.position = position;
        this.view.setTranslateX(position.getX());
        this.view.setTranslateY(position.getY());
    }
    public boolean isCollidedWith(Actor actor) {
        return this.view.getBoundsInParent().intersects(actor.getView().getBoundsInParent());
    }

    public Rectangle getView() {
        return view;
    }
    public double getWidth() {
        return width;
    }
    public Position<Double> getPosition() {
        return position;
    }


}
