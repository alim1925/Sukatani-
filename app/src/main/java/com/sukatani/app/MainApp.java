package com.sukatani.app;

import com.sukatani.ui.auth.LoginView;
import com.sukatani.util.DatabaseManager;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Initialize database
        DatabaseManager.initializeDatabase();

        // Setup Navigator
        AppNavigator.setStage(primaryStage);
        primaryStage.setTitle("Sukatani - Smart Agriculture Management");
        
        // Show Login View
        AppNavigator.navigate(new LoginView().getScene());
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
