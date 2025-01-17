package hkhattabi.controllers;
import hkhattabi.Factory;
import hkhattabi.models.*;
import hkhattabi.models.weapon.Bazooka;
import hkhattabi.models.weapon.Bullet;
import hkhattabi.models.weapon.Gun;
import hkhattabi.models.weapon.Shotgun;
import hkhattabi.views.GameView;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.ColorPicker;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;


public class GameController {
    private int GAME_WIDTH = 1366;
    private int GAME_HEIGHT = 768;
    private int stageNumber;
    private int ennemyCount;
    private int durationBeforeStartStage;
    private boolean gameStarted;
    private boolean gamepaused;
    private Timeline timeline;
    private GameView gameView;
    private HashMap<KeyCode, Boolean> keys;
    private Position<Double> cursorPosition;
    private ArrayList<Ennemy>  ennemies;
    private ArrayList<Bullet> ennemyBullets;
    private ArrayList<Bullet> playerBullets;
    public static Player currentPlayer;


    public GameController(Stage stage) {
        this.durationBeforeStartStage = 3;
        this.gameStarted = false;
        this.gamepaused = false;
        this.gameView = new GameView(this, stage);
        this.keys = new HashMap<>();
        this.cursorPosition = new Position<>(0.0,0.0);
        this.ennemies = new ArrayList<>();
        this.ennemyBullets = new ArrayList<>();
        this.playerBullets = new ArrayList<>();
        gameView.init();
        this.onMenuClicked();
    }




    public void startGame(boolean continueGame) {
        gameStarted = true;
        Model.gameView = gameView;
        if(!continueGame) {
            stageNumber = 1;
            ennemyCount = 1;
        }
        initPlayer();
        onGameStartClicked();
        callReinforcement();
        //startTimerOnStageStarted();
        AnimationTimer ticks = new AnimationTimer() {
            @Override
            public void handle(long l) {
                update();
            }
        };
        ticks.start();
    }
    public void update() {
        if (!gamepaused && gameStarted) {
            listenUserInput();
            listenEnnemy();
            listenPlayerBullets();
            listenEnnemyBullets();
            listenEnnemyCount();

            this.playerBullets.removeIf(Bullet::isCollided);
            this.ennemyBullets.removeIf(Bullet::isCollided);
            this.ennemies.removeIf(Ennemy::isDead);
        }


    }
    public void hurtHuman(Human human, double damage) {
        if (!human.isDead()) {
            human.hurt(human.getHealth() - damage);
            if (human.getHealth() <= 0) {
                human.die(this.ennemies.size() - 1);
                this.destroyActor(human);
            }
        }
    }
    public void onBulletCollided(Bullet bullet, Human human) {
        boolean isBazzokaBullet = (bullet.getShootedBy() instanceof Bazooka);
        this.hurtHuman(human, bullet.getShootedBy().getDamage());
        if (!isBazzokaBullet) {
            this.destroyActor(bullet);
            bullet.setIsCollided(true);
        }
    }

    public void checkBulletExitedWindow(Bullet bullet) {
        if (bullet.getPosition().getX() < 0 || bullet.getPosition().getY() > this.GAME_HEIGHT || bullet.getPosition().getX() > this.GAME_WIDTH ||bullet.getPosition().getY() < 0) {
            bullet.stopAnimationTimer();
            bullet.setIsCollided(true);
            this.destroyActor(bullet);
        }
    }
    public void listenEnnemyCount() {
        if (ennemies.size() == 0) {
            ennemyCount++;
            stageNumber += 1;
            this.currentPlayer.notifyView("Etage : " + stageNumber, NotifyType.STAGE_COUNT);
            callReinforcement();
            startTimerOnStageStarted();
        }
    }
    public void listenEnnemyBullets() {
        for (Bullet bullet : ennemyBullets) {
            checkBulletExitedWindow(bullet);
            if (bullet.isCollidedWith(this.currentPlayer)) {
                onBulletCollided(bullet, this.currentPlayer);
            }
        }
    }
    public void listenPlayerBullets() {
        for (Bullet bullet : playerBullets) {
            checkBulletExitedWindow(bullet);
            for (Ennemy ennemy : ennemies) {
                if (bullet.isCollidedWith(ennemy)) {
                    onBulletCollided(bullet, ennemy);
                }
            }
        }
    }
    public void listenEnnemy() {
        for (Ennemy ennemy : ennemies) {
            if (this.currentPlayer.getPosition().getX() < ennemy.getPosition().getX()) {
                ennemy.setLookingRight(false);
            } else {
                ennemy.setLookingRight(true);
            }
            if (ennemy.isShooting()) {
                if (ennemy.getWeaponEquipped().getClip().size() > 0) {
                    Bullet bullet;
                    bullet = ennemy.getWeaponEquipped().getClip().get(ennemy.getWeaponEquipped().getClip().size() - 1);
                    spawnActor(bullet);
                    ennemyBullets.add(bullet);
                    ennemy.setShooting(false);
                }
            }
        }
    }
    public void listenUserInput() {
        if (this.isPressed(KeyCode.Z) && this.currentPlayer.getPosition().getY() > 0 + (Actor.height)) {
            this.currentPlayer.moveUp();
        }
        if (this.isPressed(KeyCode.Q) && this.currentPlayer.getPosition().getX() > 0 + (Actor.width)) {
            this.currentPlayer.moveLeft();
        }
        if (this.isPressed(KeyCode.S) && this.currentPlayer.getPosition().getY() < this.GAME_HEIGHT - (Actor.height * 4)) {
            this.currentPlayer.moveBottom();
        }
        if (this.isPressed(KeyCode.D) && this.currentPlayer.getPosition().getX() < this.GAME_WIDTH -( Actor.width * 3)) {
            this.currentPlayer.moveRight();
        }
        if (this.isPressed(KeyCode.DIGIT1)) {
            this.currentPlayer.setWeaponEquiped(0);
        }
        if (this.isPressed(KeyCode.DIGIT2)) {
            this.currentPlayer.setWeaponEquiped(1);
        }
        if (this.isPressed(KeyCode.DIGIT3)) {
            this.currentPlayer.setWeaponEquiped(2);
        }
        if (this.isPressed(KeyCode.SHIFT)) {
            for (Ennemy ennemy : ennemies) {
                ennemy.getWeaponEquipped().setRateOfFire(4);
            }
        } else {
            for (Ennemy ennemy : ennemies) {
                ennemy.getWeaponEquipped().setRateOfFire(16);
            }
        }
        if (this.isPressed(KeyCode.ESCAPE)) {
            onGameFinished();
        }
    }
    public void onKeyPressed(KeyCode keyCode) {
        if (gameStarted) {
            this.keys.put(keyCode, true);
        }
    }
    public void onKeyReleased(KeyCode keyCode) {
        if (gameStarted) {
            this.keys.put(keyCode, false);
        }
    }
    public void onMouseMoved(MouseEvent event) {
        if (gameStarted) {
            this.cursorPosition.setX(event.getX());
            this.cursorPosition.setY(event.getY());
            if (event.getX() < currentPlayer.getPosition().getX()) {
                this.currentPlayer.setLookingRight(false);

            } else {
                this.currentPlayer.setLookingRight(true);
            }
            for (Ennemy ennemy : ennemies) {
                ennemy.aimPlayer(this.currentPlayer.getPosition());
            }
        }
    }

    public void onMouseClicked() {
        if (gameStarted && !gamepaused) {
            if (this.currentPlayer.getWeaponEquipped().getClip().size() > 0 ) {
                if (this.currentPlayer.getWeaponEquipped() instanceof Gun ||this.currentPlayer.getWeaponEquipped() instanceof Bazooka) {
                    Bullet bullet = this.currentPlayer.getWeaponEquipped().getClip().get(this.currentPlayer.getWeaponEquipped().getClip().size() - 1);
                    spawnActor(bullet);
                    playerBullets.add(bullet);
                } else if (this.currentPlayer.getWeaponEquipped() instanceof Shotgun) {
                    Bullet bullet = this.currentPlayer.getWeaponEquipped().getClip().get(this.currentPlayer.getWeaponEquipped().getClip().size() - 1);
                    Bullet bullet2 = this.currentPlayer.getWeaponEquipped().getClip().get(this.currentPlayer.getWeaponEquipped().getClip().size() - 2);
                    Bullet bullet3 = this.currentPlayer.getWeaponEquipped().getClip().get(this.currentPlayer.getWeaponEquipped().getClip().size() - 3);
                    spawnActor(bullet);
                    spawnActor(bullet2);
                    spawnActor(bullet3);
                    playerBullets.add(bullet);
                    playerBullets.add(bullet2);
                    playerBullets.add(bullet3);
                }
            }
            this.currentPlayer.shoot(this.cursorPosition);
        }
    }

    public void startTimerOnStageStarted() {
        durationBeforeStartStage = 3;
        timeline = new Timeline(new KeyFrame(Duration.millis(1000), e -> {
            durationBeforeStartStage -= 1;
            if (durationBeforeStartStage <= 0) {
                timeline.stop();
                timeline = null;
            }
        }));
        timeline.setCycleCount(3);
        timeline.play();
    }
    public void spawnActor(Actor actor) {
        actor.notifyView(actor.getView(), NotifyType.ADD_ACTOR);
    }
    public void destroyActor(Actor actor) {
        actor.notifyView(actor.getView(), NotifyType.REMOVE_ACTOR);
    }
    public void callReinforcement() {
        for (int i = 0; i < ennemyCount; i++) {
            Ennemy ennemy = Factory.createEnemy(this.GAME_WIDTH, this.GAME_HEIGHT, this.currentPlayer.getPosition(), Human.ennemyColor);
            ennemies.add(ennemy);
            spawnActor(ennemy);
        }
    }
    public boolean isPressed(Object key) {
        return this.keys.getOrDefault(key, false);
    }
    public void initPlayer() {
        Player player = Factory.createPlayer(this.GAME_WIDTH, this.GAME_HEIGHT, Human.playerColor);
        this.currentPlayer = player;
        this.spawnActor(player);
    }
    public void onChangePlayerColor(ColorPicker colorPicker) {
        Human.playerColor = colorPicker.getValue();
    }
    public void onChangeEnnemyColor(ColorPicker colorPicker) {
        Human.ennemyColor = colorPicker.getValue();
    }
    public void onLoadingUser(String stageNumber) {
        System.out.println(stageNumber);
        this.stageNumber = Integer.parseInt(stageNumber);
        this.ennemyCount = Integer.parseInt(stageNumber);
        startGame(true);


    }
    public void onGameStartClicked() {
        this.gameView.displayGame(currentPlayer, this.stageNumber, this.ennemyCount);
    }
    public void onMenuClicked() {
        this.gameView.displayMenu();
    }
    public void onContinueclicked() {
        gameView.displayContinue();
    }
    public void onSettingsClicked() {this.gameView.displaySettings();}
    public void onGameFinished() {
        this.gamepaused = true;
        this.gameView.displayFinishGame();
    }
    public void saveUser(String username) {
        String usersSaved = "";
        boolean newFile = false;
        try {
            File usersFile = new File("users.txt");
            Scanner scanner = new Scanner(usersFile);
            while (scanner.hasNextLine()) {
                usersSaved += scanner.nextLine() + "\n";
            }
            usersSaved += username + ";" + stageNumber;
        } catch (FileNotFoundException e) {
            System.out.println("File not found, create now");
            newFile = true;
        }
        if (newFile) {
            usersSaved = username + ";" + stageNumber;
        }

        try {
            FileWriter fileWriter = new FileWriter("users.txt");
            fileWriter.write(usersSaved);
            fileWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An Error occured");
        }
        gameView.getUsers().clear();
        gameView.getGamePane().getChildren().clear();
        this.gameStarted = false;
        this.gamepaused = false;
        this.keys = new HashMap<>();
        this.cursorPosition = new Position<>(0.0,0.0);
        this.ennemies = new ArrayList<>();
        this.ennemyBullets = new ArrayList<>();
        this.playerBullets = new ArrayList<>();
        gameView.displayMenu();

    }

    public void setGamepaused(boolean gamepaused) {
        this.gamepaused = gamepaused;
    }
}
