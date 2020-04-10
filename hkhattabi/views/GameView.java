package hkhattabi.views;
import hkhattabi.models.Actor;
import hkhattabi.models.Position;
import javafx.scene.Cursor;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;


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



    public Pane getPane() {
        return this.gamePane;
    }
}
