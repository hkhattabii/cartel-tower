package hkhattabi.controllers;
import hkhattabi.Factory;
import hkhattabi.models.*;
import hkhattabi.models.weapon.Bullet;
import hkhattabi.views.AppView;
import javafx.animation.AnimationTimer;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.HashMap;


public class GameController {
    private int GAME_WIDTH = 1366;
    private int GAME_HEIGHT = 768;
    private int ennemyCount;
    private boolean gameStarted;
    private AppView appView;
    private Player currentPlayer;
    private HashMap<KeyCode, Boolean> keys;
    private Position<Double> cursorPosition;
    private ArrayList<Ennemy>  ennemies;
    private ArrayList<Bullet> ennemyBullets;
    private ArrayList<Bullet> playerBullets;

    public GameController(Stage stage) {
        int stageNumber = 1;
        this.ennemyCount = 1;
        this.gameStarted = false;
        this.appView = new AppView(this, this.GAME_WIDTH, this.GAME_HEIGHT);
        this.keys = new HashMap<>();
        this.cursorPosition = new Position<>(0.0,0.0);
        this.ennemies = new ArrayList<>();
        this.ennemyBullets = new ArrayList<>();
        this.playerBullets = new ArrayList<>();
        Actor.appView = appView;
        appView.onInit(stage);
        this.displayMenu();
    }
    public void startGame() {
        this.gameStarted = true;
        this.removeMenu();
        this.initPlayer();
        this.displayGame();
        this.callReinforcement();
        AnimationTimer ticks = new AnimationTimer() {
            @Override
            public void handle(long l) {
                update();
            }
        };
        ticks.start();
    }
    public void update() {
        listenUserInput();
        listenEnnemy();
        listenPlayerBullets();
        listenEnnemyBullets();
        listenEnnemyCount();

        this.playerBullets.removeIf(Bullet::isCollided);
        this.ennemyBullets.removeIf(Bullet::isCollided);
        this.ennemies.removeIf(Ennemy::isDead);

    }
    public void hurtHuman(Human human, double damage) {
        if (!human.isDead()) {
            human.setHealth(human.getHealth() - damage);
            if (human.getHealth() <= 0) {
                human.die(this.ennemies.size() - 1);
                if (human instanceof  Ennemy) {
                    this.destroyActor(human);
                } else if (human instanceof Player) {
                    this.destroyActor(human);
                }
            }
        }
    }
    public void onBulletCollided(Bullet bullet, Human human) {
        this.hurtHuman(human, bullet.getShootedBy().getDamage());
        this.destroyActor(bullet);
        bullet.setIsCollided(true);
    }

    public void checkBulletExitedWindow(Bullet bullet) {
        if (bullet.getPosition().getX() < 0 || bullet.getPosition().getY() > this.GAME_HEIGHT || bullet.getPosition().getX() > this.GAME_WIDTH ||bullet.getPosition().getY() < 0) {
            bullet.setIsCollided(true);
            this.destroyActor(bullet);
        }
    }
    public void listenEnnemyCount() {
        if (ennemies.size() == 0) {
            ennemyCount++;
            callReinforcement();
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
        } if (this.isPressed(KeyCode.SHIFT)) {
            for (Ennemy ennemy : ennemies) {
                ennemy.getWeaponEquipped().setRateOfFire(4);
            }
        } else {
            for (Ennemy ennemy : ennemies) {
                ennemy.getWeaponEquipped().setRateOfFire(16);
            }
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
        if (gameStarted) {
            if (this.currentPlayer.getWeaponEquipped().getClip().size() > 0 ) {
                Bullet bullet = this.currentPlayer.getWeaponEquipped().getClip().get(this.currentPlayer.getWeaponEquipped().getClip().size() - 1);
                spawnActor(bullet);
                playerBullets.add(bullet);
            }
            this.currentPlayer.shoot(this.cursorPosition);
        }
    }
    public void spawnActor(Actor actor) {
        actor.notifyGameView(ViewType.ADD_ACTOR);
    }
    public void destroyActor(Actor actor) {
        actor.notifyGameView(ViewType.REMOVE_ACTOR);
    }
    public void callReinforcement() {
        for (int i = 0; i < ennemyCount; i++) {
            Ennemy ennemy = Factory.createEnemy(this.GAME_WIDTH, this.GAME_HEIGHT, this.currentPlayer.getPosition());
            ennemies.add(ennemy);
            spawnActor(ennemy);
        }
    }
    public boolean isPressed(Object key) {
        return this.keys.getOrDefault(key, false);
    }
    public void initPlayer() {
        Player player = Factory.createPlayer(this.GAME_WIDTH, this.GAME_HEIGHT);
        this.currentPlayer = player;
        this.spawnActor(player);
    }
    public void removeMenu() {
        this.appView.removeMenu();
    }
    public void displayGame() {
        this.appView.displayGame(this.currentPlayer);
    }
    public void displayMenu() {this.appView.displayMenu();}



}
