package com.sukatani.ui.components;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class StatsCard extends VBox {
    public StatsCard(String title, String value, String unit, String colorHex) {
        setSpacing(5);
        setPadding(new Insets(20));
        getStyleClass().add("card");
        setMinWidth(180);

        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-text-fill: #64748B; -fx-font-size: 14px;");

        Label valueLabel = new Label(value + (unit.isEmpty() ? "" : " " + unit));
        valueLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 24));
        valueLabel.setStyle("-fx-text-fill: " + colorHex + ";");

        getChildren().addAll(titleLabel, valueLabel);
    }
}
