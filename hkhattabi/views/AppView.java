package hkhattabi.views;
import hkhattabi.controllers.GameController;
import hkhattabi.models.Actor;
import hkhattabi.models.Player;
import hkhattabi.models.ViewType;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;


public class AppView {
    protected GameController gameController;
    protected GameView gameView;
    protected UiView uiView;
    protected Pane appPane;
    protected int WIDTH;
    protected int HEIGHT;


    public AppView(GameController gameController, int gameWidth, int gameHeight) {
        this.gameController = gameController;
        this.gameView = new GameView();
        this.uiView = new UiView();
        this.appPane = new Pane();
        this.WIDTH = gameWidth;
        this.HEIGHT = gameHeight;
    }


    public void onInit(Stage stage) {
        Rectangle appWindow = new Rectangle(this.WIDTH, this.HEIGHT, Color.BLACK);

        Scene scene = new Scene(this.appPane);
        stage.setTitle("CARTEL TOWER");

        scene.setOnKeyPressed(event -> gameController.onKeyPressed(event.getCode()));
        scene.setOnKeyReleased(event -> gameController.onKeyReleased(event.getCode()));
        scene.setOnMouseClicked(event -> gameController.onMouseClicked());
        scene.setOnMouseMoved(event -> gameController.onMouseMoved(event));

        this.appPane.getChildren().addAll(appWindow, uiView.getPane());
        this.appPane.setCursor(Cursor.CROSSHAIR);
        stage.setWidth(this.WIDTH);
        stage.setHeight(this.HEIGHT);
        stage.setScene(scene);
        stage.show();
    }




    public void removeMenu() {
        this.uiView.removeNode(this.uiView.getMenu());
    }

    public void displayGame(Player currentPlayer) {
        this.appPane.getChildren().addAll(gameView.getPane());
        uiView.displayGameInfo(currentPlayer);
    }

    public void displayMenu() {
        uiView.displayMenu(this.gameController);
    }

    public void updateUiView(String newText, ViewType viewType) {
        this.uiView.updateTextUi(newText, viewType);
    }
    public void updateGameView(Actor actor, ViewType viewType) {this.gameView.updateWorld(actor, viewType);}
    public int getGameWidth() {
        return this.WIDTH;
    }
    public int getGameHeight() {
        return this.HEIGHT;
    }


}