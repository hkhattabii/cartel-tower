package hkhattabi.models;
import hkhattabi.views.AppView;
import javafx.scene.shape.Rectangle;


public class Actor {
    public static AppView appView;
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


    public boolean isCollidedWith(Actor actor) {
        return this.view.getBoundsInParent().intersects(actor.getView().getBoundsInParent());
    }

    public void notifyView(String newText, ViewType viewType) {
        appView.updateUiView(newText, viewType);
    }

    public void notifyGameView(ViewType viewType) {
        switch (viewType) {
            case ADD_ACTOR:
                appView.addActor(this);
                break;
            case REMOVE_ACTOR:
                appView.removeActor(this);
                break;
            default:
                break;

        }
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
