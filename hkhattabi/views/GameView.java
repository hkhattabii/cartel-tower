package hkhattabi.views;

import hkhattabi.controllers.GameController;
import hkhattabi.models.Player;
import hkhattabi.models.Position;
import hkhattabi.models.ViewType;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;


public class GameView {
    private Stage stage;
    private Scene scene;
    private Pane gamePane;
    private GameController gameController;
    private Text stageCountText;
    private Text healthCountText;
    private Text munitionsCountText;
    private Text ennemyCountText;
    private Text weaponEquipedText;
    private ArrayList<String> users;



    public GameView(GameController gameController, Stage stage) {
        this.gameController = gameController;
        this.stage = stage;
        this.gamePane = new Pane();
        this.users = new ArrayList<>();

    }

    public void init() {
        stage.setTitle("CARTEL TOWER");
        stage.setWidth(1366);
        stage.setHeight(768);
        stage.show();
    }

    public void displayMenu() {
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        scene = new Scene(vBox);
        Text newGameText = renderText("NOUVELLE PARTIE", null, 32, true);
        Text continueGameText = renderText("CONTINUER", null, 32, true);
        try {
            File usersFile = new File("users.txt");
            Scanner scanner = new Scanner(usersFile);
            while (scanner.hasNextLine()) {
                users.add(scanner.nextLine());
            }
         } catch (FileNotFoundException e) {
            System.out.println("Cannot find file");
            continueGameText.setFill(Color.GREY);
            e.printStackTrace();
        }

        continueGameText.setOnMouseClicked(e -> displayContinue());
        Text settingsGameText = renderText("OPTIONS", null, 32, true);
        Text quitGameText = renderText("QUITTER", null, 32, true);
        newGameText.setOnMouseClicked(e -> gameController.startGame());
        vBox.getChildren().addAll(newGameText, continueGameText, settingsGameText, quitGameText);
        stage.setScene(scene);
    }
    public void displayContinue() {
        ScrollPane scrollPane = new ScrollPane();
        VBox vBox = new VBox();
        for (String user : users) {
            String[] userArr = user.split(";");
            Text text = renderText(userArr[0] + " : étage " + userArr[1], null, 22, true);
            text.setOnMouseClicked(e -> {
                gameController.continueGame(userArr[1]);
            });
            vBox.getChildren().addAll(text);
        }
        scrollPane.setContent(vBox);
        scene = new Scene(scrollPane);
        stage.setScene(scene);
    }
    public void displaySettings() {

    }
    public void displayGame(Player player, int stageNumber, int ennemyCount) {
        stageCountText = renderText("Etage : " + stageNumber, new Position<Double>(64.0, 64.0), 16, false);
        ennemyCountText = renderText("Ennemis restant : " + ennemyCount, new Position<Double>(1366.0 - 256.0, 64.0), 16, false);
        healthCountText = renderText("Vie : " + player.getHealth() +  "  / 250", new Position<Double>( 64.0, 768.0 - 64.0), 16, false);
        weaponEquipedText = renderText(player.getWeaponEquipped().getName(), new Position<Double>(1366.0 - 300, 768.0 - 64.0), 16, false);
        munitionsCountText = renderText("Munition : " + player.getWeaponEquipped().getClip().size() + " / " + player.getWeaponEquipped().getMaxBulletCount(), new Position<Double>(1366.0 - 228, 768.0 - 64.0), 16, false);
        gamePane.getChildren().addAll(stageCountText,ennemyCountText , healthCountText, weaponEquipedText, munitionsCountText);
        scene = new Scene(gamePane);
        scene.setOnKeyPressed(e -> gameController.onKeyPressed(e.getCode()));
        scene.setOnKeyReleased(e -> gameController.onKeyReleased(e.getCode()));
        scene.setOnMouseClicked(e -> gameController.onMouseClicked());
        scene.setOnMouseMoved(e -> gameController.onMouseMoved(e));
        stage.setScene(scene);
    }

    public void displayFinishGame() {
        VBox vBox = new VBox();
        HBox hBox = new HBox();

        vBox.setPrefWidth(1366);
        vBox.setPrefHeight(768);

        Text finisGameText = renderText("Voulez-vous quitter la partie ? ", null, 32, false);
        Text yesText = renderText("Oui", null, 20, true);
        Text noText = renderText("Non", null, 20, true);

        yesText.setOnMouseClicked(event -> {
            displayUserSave();
        });

        noText.setOnMouseClicked(event -> {
            gamePane.getChildren().remove(vBox);
            gameController.setGamepaused(false);
        });

        hBox.getChildren().addAll(yesText,noText);
        vBox.getChildren().addAll(finisGameText, hBox);
        vBox.setAlignment(Pos.CENTER);
        hBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(8);
        hBox.setSpacing(32);


        gamePane.getChildren().addAll(vBox);
    }

    public void displayUserSave() {
        Stage dialog = new Stage();
        VBox vBox1 = new VBox();
        dialog.initModality(Modality.APPLICATION_MODAL);
        TextField textField = new TextField();
        Button registerUser = new Button("Enregistrer");
        textField.setPromptText("Nom du joueur");
        registerUser.setOnMouseClicked(event -> gameController.saveUser(textField.getText()));

        vBox1.getChildren().addAll(textField, registerUser);
        dialog.setScene(new Scene(vBox1));
        dialog.showAndWait();
    }






    public Text renderText(String textToADD, Position<Double> position, int size, boolean hoverable) {
        Text text = new Text(textToADD);
        text.setFont(new Font(size));

        if (position != null) {
            text.setTranslateX(position.getX());
            text.setTranslateY(position.getY());
        }

        if (hoverable) {
            text.hoverProperty().addListener((observableValue, oldValue, isHover) -> {
                if (isHover) {
                    text.setFill(Color.BLUE);
                } else {
                    text.setFill(Color.BLACK);
                }
            });
        }


        return text;
    }

    public void updateView(Object value, ViewType viewType) {
            switch (viewType) {
                case STAGE_COUNT:
                    stageCountText.setText((String)value);
                    break;
                case ENNEMY_COUNT:
                    ennemyCountText.setText((String)value);
                    break;
                case HEALTH_COUNT:
                    healthCountText.setText((String)value);
                    break;
                case MUNITION_COUNT:
                    munitionsCountText.setText((String) value);
                    break;
                case WEAPON_EQUIPED:
                    weaponEquipedText.setText((String) value);
                    break;
                case REMOVE_ACTOR:
                    gamePane.getChildren().remove((Node) value);
                    break;
                case ADD_ACTOR:
                    gamePane.getChildren().addAll((Node) value);
                    break;
        }
    }

}
