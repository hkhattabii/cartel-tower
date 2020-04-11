package hkhattabi.views;

import hkhattabi.controllers.GameController;
import hkhattabi.models.Player;
import hkhattabi.models.ViewType;
import javafx.scene.Node;
import javafx.scene.control.Button;
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
    protected Text enemyCountText;
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
    public void displayGameInfo(Player currentPlayer) {
        gameInfo = new VBox();
        stageCountText = createUiText("Etage : 0");
        munitionCountText = createUiText("munitions " + currentPlayer.getWeaponEquipped().getClip().size() + " / 16");
        healthCountText = createUiText("Vie : " + currentPlayer.getHealth() );
        enemyCountText = createUiText("Ennemy restant : 1");
        gameInfo.getChildren().addAll(stageCountText, munitionCountText, healthCountText, enemyCountText);
        addNode(gameInfo);
    }
    public Text createUiText(String textToAdd) {
        Text text = new Text(textToAdd);
        text.setFont(Font.font(16));
        text.setFill(Color.WHITE);
        text.setX(16);
        text.setY(900);
        return text;

    }
    public void addNode(Node node) {
        uiPane.getChildren().addAll(node);
    }
    public void removeNode(Node node) {
        uiPane.getChildren().remove(node);
    }
    public void updateTextUi(String newText, ViewType viewType) {
        switch (viewType){
            case HEALTH_COUNT:
                healthCountText.setText(newText);
                break;
            case ENNEMY_COUNT:
                enemyCountText.setText(newText);
                break;
            case MUNITION_COUNT:
                munitionCountText.setText(newText);
                break;
            case STAGE_COUNT:
                stageCountText.setText(newText);
                break;
            default:
                break;
        }
}
    public Pane getPane() {
        return uiPane;
    }
    public Node getMenu() {
        return menu;
    }

}
