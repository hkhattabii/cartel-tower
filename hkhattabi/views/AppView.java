package hkhattabi.views;
import hkhattabi.controllers.GameController;
import hkhattabi.models.Position;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import java.util.HashMap;


public class AppView {
    protected GameController gameController;
    protected GameView gameView;
    protected UiView uiView;
    protected Pane appPane;
    protected int WIDTH;
    protected int HEIGHT;
    protected Position<Double> cursorPosition;


    public AppView(GameController gameController) {
        this.gameController = gameController;
        this.gameView = new GameView();
        this.uiView = new UiView();
        this.appPane = new Pane();
        this.WIDTH = 1920;
        this.HEIGHT = 1080;
        this.cursorPosition = new Position<Double>(0.0,0.0);
    }


    public void onInit(Stage stage) {
        Rectangle appWindow = new Rectangle(this.WIDTH, this.HEIGHT, Color.BLACK);

        Scene scene = new Scene(this.appPane);
        stage.setTitle("CARTEL TOWER");



        scene.setOnKeyPressed(event -> gameController.onKeyPressed(event.getCode()));
        scene.setOnKeyReleased(event -> gameController.onKeyReleased(event.getCode()));
        scene.setOnMouseClicked(event -> gameController.onMouseClicked(new Position<Double>(event.getX(), event.getY())));
        scene.setOnMouseMoved(event -> gameController.onMouseMoved(event));


        uiView.displayMenu(gameController);


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

    public void displayGame() {
        this.appPane.getChildren().addAll(gameView.getPane());
        uiView.displayGameInfo();
    }

    public void updateStageNumber(String newText) {
        this.uiView.updateTextUi(this.uiView.getStageCountText(), newText);
    }

    public void updateMunitionNumber(String newText) {
        this.uiView.updateTextUi(this.uiView.getMunitionCountText(), newText);
    }

    public int getGameWidth() {
        return this.WIDTH;
    }

    public int getGameHeight() {
        return this.HEIGHT;
    }


    public GameView getGameView() {
        return gameView;
    }

    public Position<Double> getCursorPosition() {
        return cursorPosition;
    }
}