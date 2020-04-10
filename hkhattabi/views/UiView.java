package hkhattabi.views;

import javafx.scene.Node;
import javafx.scene.layout.Pane;

public class UiView {
    private Pane uiPane;

    public UiView() {
        uiPane = new Pane();
    }

    public void addNode(Node node) {
        uiPane.getChildren().addAll(node);
    }

    public void removeNode(Node node) {
        uiPane.getChildren().remove(node);
    }

    public void updateUi(Node node) {
        uiPane.getChildren().get(0);
    }

    public Pane getUiPane() {
        return uiPane;
    }
}
