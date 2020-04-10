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


    public void notifyView(String newText, ViewType viewType) {
        switch (viewType){
            case MUNITION_COUNT:
                appView.updateMunitionNumber(newText);
                break;
            case STAGE_COUNT:
                appView.updateStageNumber(newText);
                break;
            default:
                break;
        }

    }

    public Rectangle getView() {
        return view;
    }


    public Position<Double> getPosition() {
        return position;
    }

}
