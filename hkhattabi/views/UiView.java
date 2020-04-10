package hkhattabi.views;

import hkhattabi.controllers.GameController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class UiView {
    private Pane uiPane;
    protected VBox menu;
    protected VBox gameInfo;
    protected Text stageCountText;
    protected Text munitionCountText;
    protected Text healthCountText;

    public UiView() {
        uiPane = new Pane();
        uiPane.setPrefWidth(300);
        uiPane.setPrefHeight(300);
    }


    public void displayMenu(GameController gameController) {
        menu = new VBox();
        Button button = new Button("JOUER");
        button.setOnMouseClicked(event -> gameController.startGame());
        menu.getChildren().addAll(button);
        addNode(menu);
    }



    public void displayGameInfo() {
        gameInfo = new VBox();



        stageCountText = new Text("Etage : 0");
        stageCountText.setFont(Font.font(32));
        stageCountText.setFill(Color.WHITE);
        stageCountText.setX(16);
        stageCountText.setY(16);


        munitionCountText = new Text("munitions 16 / 16");
        munitionCountText.setFont(Font.font(32));
        munitionCountText.setFill(Color.WHITE);
        munitionCountText.setX(32);
        munitionCountText.setY(32);

        healthCountText = new Text("Vie : 250");
        healthCountText.setFont(Font.font(32));
        healthCountText.setFill(Color.WHITE);
        healthCountText.setX(16);
        munitionCountText.setY(900);

        










        gameInfo.getChildren().addAll(stageCountText, munitionCountText, healthCountText);
        addNode(gameInfo);

    }



    public void addNode(Node node) {
        uiPane.getChildren().addAll(node);
    }

    public void removeNode(Node node) {
        uiPane.getChildren().remove(node);
    }

    public void updateTextUi(Text node, String newText) {
        node.setText(newText);
    }

    public Pane getPane() {
        return uiPane;
    }

    public Node getMenu() {
        return this.menu;
    }

    public Text getStageCountText() {
        return this.stageCountText;
    }

    public Text getMunitionCountText() {
        return this.munitionCountText;
    }
}
