package hkhattabi.views;
import hkhattabi.models.Actor;
import javafx.scene.layout.Pane;


public class GameView {
    private Pane gamePane;

    public GameView() {
        this.gamePane = new Pane();
    }

    public void addActor(Actor actor) {
        this.gamePane.getChildren().addAll(actor.getView());
    }

    public void removeActor(Actor actor) {
        this.gamePane.getChildren().remove(actor.getView());
    }



    public Pane getGamePane() {
        return this.gamePane;
    }
}
