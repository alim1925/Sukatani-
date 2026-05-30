package com.sukatani.ui.auth;

import com.sukatani.app.AppNavigator;
import com.sukatani.service.AuthService;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class LoginView {
    private final AuthService authService = new AuthService();

    public Scene getScene() {
        VBox root = new VBox(20);
        root.setPadding(new Insets(40));
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: #F7FAF7;");

        Text title = new Text("Masuk Sukatani");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 28));
        title.setFill(javafx.scene.paint.Color.web("#1E293B"));

        VBox form = new VBox(10);
        form.setMaxWidth(300);

        TextField usernameField = new TextField();
        usernameField.setPromptText("Nama Pengguna");
        usernameField.setStyle("-fx-background-radius: 10; -fx-padding: 10;");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Kata Sandi");
        passwordField.setStyle("-fx-background-radius: 10; -fx-padding: 10;");

        Button loginButton = new Button("Masuk");
        loginButton.setMaxWidth(Double.MAX_VALUE);
        loginButton.setStyle("-fx-background-color: #2E7D32; -fx-text-fill: white; -fx-background-radius: 10; -fx-padding: 10; -fx-font-weight: bold;");
        loginButton.setCursor(javafx.scene.Cursor.HAND);

        Hyperlink registerLink = new Hyperlink("Belum punya akun? Daftar di sini");
        registerLink.setStyle("-fx-text-fill: #64748B;");

        form.getChildren().addAll(new Label("Nama Pengguna"), usernameField, new Label("Kata Sandi"), passwordField, loginButton, registerLink);

        loginButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            if (authService.login(username, password)) {
                System.out.println("Login berhasil!");
                AppNavigator.navigate(new com.sukatani.ui.dashboard.DashboardView().getScene());
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Nama pengguna atau kata sandi salah", ButtonType.OK);
                alert.setTitle("Kesalahan Masuk");
                alert.setHeaderText(null);
                alert.showAndWait();
            }
        });

        registerLink.setOnAction(e -> {
            AppNavigator.navigate(new RegisterView().getScene());
        });

        root.getChildren().addAll(title, form);

        return new Scene(root, 400, 500);
    }
}
