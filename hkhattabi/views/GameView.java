package hkhattabi.views;
import hkhattabi.models.Actor;
import hkhattabi.models.ViewType;
import javafx.scene.layout.Pane;



public class GameView {
    private Pane gamePane;
    public GameView() {
        this.gamePane = new Pane();
    }
    public void updateWorld(Actor actor, ViewType viewType) {
        switch (viewType) {
            case ADD_ACTOR:
                addActor(actor);
                break;
            case REMOVE_ACTOR:
                removeActor(actor);
                break;
            default:
                break;
        }
    }
    public void addActor(Actor actor) {
        this.gamePane.getChildren().addAll(actor.getView());
    }
    public void removeActor(Actor actor) {
        this.gamePane.getChildren().remove(actor.getView());
    }
    public Pane getPane() {
        return this.gamePane;
    }
}
