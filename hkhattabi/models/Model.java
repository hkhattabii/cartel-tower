package hkhattabi.models;

import hkhattabi.views.GameView;

public abstract class Model {
    public static GameView gameView;


    public void notifyView(Object value, NotifyType notifyType) {
        gameView.updateView(value, notifyType);
    }

}
