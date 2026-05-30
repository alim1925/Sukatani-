package com.sukatani.app;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class AppNavigator {
    private static Stage stage;
    private static Pane contentArea;

    public static void setStage(Stage stage) {
        AppNavigator.stage = stage;
    }

    public static void setContentArea(Pane contentArea) {
        AppNavigator.contentArea = contentArea;
    }

    public static void navigate(Scene scene) {
        if (stage != null) {
            stage.setScene(scene);
            stage.centerOnScreen();
        }
    }

    public static void setView(Node view) {
        if (contentArea != null) {
            contentArea.getChildren().setAll(view);
        }
    }

    public static Stage getStage() {
        return stage;
    }
}
