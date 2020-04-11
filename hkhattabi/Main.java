package hkhattabi;

import hkhattabi.controllers.GameController;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        GameController gameController = new GameController(stage);

    }


    //Lance le jeu
    public static void main(String[] args) { launch(args);}


}
