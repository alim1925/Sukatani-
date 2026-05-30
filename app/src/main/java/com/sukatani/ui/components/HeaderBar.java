package com.sukatani.ui.components;

import com.sukatani.app.AppNavigator;
import com.sukatani.service.AuthService;
import com.sukatani.ui.auth.LoginView;
import com.sukatani.util.SessionManager;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class HeaderBar extends HBox {
    private final Label pageTitleLabel;

    public HeaderBar() {
        setAlignment(Pos.CENTER_LEFT);
        setPadding(new Insets(15, 30, 15, 30));
        setStyle("-fx-background-color: white; -fx-border-color: #DCE7DC; -fx-border-width: 0 0 1 0;");

        pageTitleLabel = new Label("Dashboard");
        pageTitleLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 20));
        pageTitleLabel.setStyle("-fx-text-fill: #1E293B;");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        String userName = SessionManager.getCurrentUser() != null ? SessionManager.getCurrentUser().getFullName() : "Pengguna";
        Label userLabel = new Label("Selamat datang, " + userName);
        userLabel.setStyle("-fx-text-fill: #64748B; -fx-font-weight: bold;");

        Button logoutBtn = new Button("Keluar");
        logoutBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: #E53935; -fx-cursor: hand; -fx-font-weight: bold;");
        logoutBtn.setOnAction(e -> {
            new AuthService().logout();
            AppNavigator.navigate(new LoginView().getScene());
        });

        getChildren().addAll(pageTitleLabel, spacer, userLabel, logoutBtn);
    }

    public void setPageTitle(String title) {
        pageTitleLabel.setText(title);
    }
}
