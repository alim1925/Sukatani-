package com.sukatani.ui.components;

import com.sukatani.app.AppNavigator;
import com.sukatani.util.SessionManager;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class Sidebar extends VBox {
    private final HeaderBar headerBar;

    public Sidebar(HeaderBar headerBar) {
        this.headerBar = headerBar;
        setPrefWidth(250);
        setPadding(new Insets(30, 15, 30, 15));
        setSpacing(10);
        getStyleClass().add("sidebar");

        Label logo = new Label("🌱 SUKATANI");
        logo.setFont(Font.font("Segoe UI", FontWeight.BOLD, 22));
        logo.setStyle("-fx-text-fill: #2E7D32;");
        logo.setPadding(new Insets(0, 0, 30, 10));

        getChildren().add(logo);

        String role = SessionManager.getCurrentUser() != null ? SessionManager.getCurrentUser().getRole() : "petani";

        addNavItem("Beranda", "Beranda");
        
        if ("petani".equals(role)) {
            addNavItem("Inventaris", "Manajemen Inventaris");
            addNavItem("Pre-Order", "Sistem Pesanan");
            addNavItem("Jadwal", "Jadwal Kegiatan");
            addNavItem("Keuangan", "Catatan Keuangan");
            addNavItem("Sensor", "Monitoring Real-time");
            addNavItem("Rekomendasi", "Saran Cerdas");
        } else if ("pembeli".equals(role)) {
            addNavItem("Pre-Order", "Pesanan Saya");
            addNavItem("Rekomendasi", "Saran Produk");
        }
    }

    private void addNavItem(String label, String pageTitle) {
        Button btn = new Button(label);
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.getStyleClass().add("nav-item");
        
        btn.setOnAction(e -> {
            // Reset all active styles
            getChildren().forEach(node -> {
                if (node instanceof Button) {
                    node.getStyleClass().remove("nav-item-active");
                }
            });
            btn.getStyleClass().add("nav-item-active");
            headerBar.setPageTitle(pageTitle);
            
            switch (label) {
                case "Inventaris":
                    AppNavigator.setView(new com.sukatani.ui.inventory.InventoryView());
                    break;
                case "Pre-Order":
                    AppNavigator.setView(new com.sukatani.ui.preorder.PreOrderView());
                    break;
                case "Jadwal":
                    AppNavigator.setView(new com.sukatani.ui.schedule.ScheduleView());
                    break;
                case "Keuangan":
                    AppNavigator.setView(new com.sukatani.ui.finance.FinanceView());
                    break;
                case "Sensor":
                    AppNavigator.setView(new com.sukatani.ui.sensor.SensorView());
                    break;
                case "Rekomendasi":
                    AppNavigator.setView(new com.sukatani.ui.recommendation.RecommendationView());
                    break;
                case "Beranda":
                    AppNavigator.setView(com.sukatani.ui.dashboard.DashboardView.createDefaultDashboard());
                    break;
                default:
                    Label placeholder = new Label(pageTitle + " Placeholder Konten");
                    placeholder.setFont(Font.font(20));
                    AppNavigator.setView(placeholder);
                    break;
            }
        });

        // Set Dashboard as active by default
        if (label.equals("Beranda")) {
            btn.getStyleClass().add("nav-item-active");
        }

        getChildren().add(btn);
    }
}
