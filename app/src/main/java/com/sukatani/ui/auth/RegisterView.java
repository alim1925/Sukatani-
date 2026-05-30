package com.sukatani.ui.auth;

import com.sukatani.app.AppNavigator;
import com.sukatani.model.User;
import com.sukatani.service.AuthService;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class RegisterView {
    private final AuthService authService = new AuthService();

    public Scene getScene() {
        VBox root = new VBox(20);
        root.setPadding(new Insets(40));
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: #F7FAF7;");

        Text title = new Text("Daftar Sukatani");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 28));
        title.setFill(javafx.scene.paint.Color.web("#1E293B"));

        VBox form = new VBox(10);
        form.setMaxWidth(300);

        TextField fullNameField = new TextField();
        fullNameField.setPromptText("Nama Lengkap");
        fullNameField.setStyle("-fx-background-radius: 10; -fx-padding: 10;");

        TextField usernameField = new TextField();
        usernameField.setPromptText("Nama Pengguna");
        usernameField.setStyle("-fx-background-radius: 10; -fx-padding: 10;");

        ComboBox<String> roleBox = new ComboBox<>();
        roleBox.getItems().addAll("Petani", "Pembeli");
        roleBox.setValue("Petani");
        roleBox.setMaxWidth(Double.MAX_VALUE);
        roleBox.setStyle("-fx-background-radius: 10;");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Kata Sandi");
        passwordField.setStyle("-fx-background-radius: 10; -fx-padding: 10;");

        PasswordField confirmPasswordField = new PasswordField();
        confirmPasswordField.setPromptText("Konfirmasi Kata Sandi");
        confirmPasswordField.setStyle("-fx-background-radius: 10; -fx-padding: 10;");

        Button registerButton = new Button("Daftar");
        registerButton.setMaxWidth(Double.MAX_VALUE);
        registerButton.setStyle("-fx-background-color: #2E7D32; -fx-text-fill: white; -fx-background-radius: 10; -fx-padding: 10; -fx-font-weight: bold;");
        registerButton.setCursor(javafx.scene.Cursor.HAND);

        Hyperlink loginLink = new Hyperlink("Sudah punya akun? Masuk di sini");
        loginLink.setStyle("-fx-text-fill: #64748B;");

        form.getChildren().addAll(
                new Label("Nama Lengkap"), fullNameField,
                new Label("Nama Pengguna"), usernameField,
                new Label("Peran"), roleBox,
                new Label("Kata Sandi"), passwordField,
                new Label("Konfirmasi Kata Sandi"), confirmPasswordField,
                registerButton, loginLink
        );

        registerButton.setOnAction(e -> {
            String fullName = fullNameField.getText();
            String username = usernameField.getText();
            String role = roleBox.getValue().toLowerCase();
            String password = passwordField.getText();
            String confirmPassword = confirmPasswordField.getText();

            if (fullName.isEmpty() || username.isEmpty() || password.isEmpty()) {
                new Alert(Alert.AlertType.WARNING, "Semua kolom harus diisi").show();
                return;
            }

            if (!password.equals(confirmPassword)) {
                new Alert(Alert.AlertType.WARNING, "Kata sandi tidak cocok").show();
                return;
            }

            User user = new User(fullName, username, password, role);
            if (authService.register(user)) {
                new Alert(Alert.AlertType.INFORMATION, "Pendaftaran berhasil! Silakan masuk.").showAndWait();
                AppNavigator.navigate(new LoginView().getScene());
            } else {
                new Alert(Alert.AlertType.ERROR, "Pendaftaran gagal. Nama pengguna mungkin sudah digunakan.").show();
            }
        });

        loginLink.setOnAction(e -> {
            AppNavigator.navigate(new LoginView().getScene());
        });

        root.getChildren().addAll(title, form);

        return new Scene(root, 400, 600);
    }
}
