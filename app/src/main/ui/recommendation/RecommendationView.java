package com.sukatani.ui.recommendation;

import com.sukatani.model.Crop;
import com.sukatani.model.SensorData;
import com.sukatani.service.InventoryService;
import com.sukatani.service.RecommendationService;
import com.sukatani.service.SensorService;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.StringConverter;

import java.util.List;

public class RecommendationView extends VBox {
    private final SensorService sensorService = new SensorService();
    private final InventoryService inventoryService = new InventoryService();
    private final RecommendationService recommendationService = new RecommendationService();
    private final VBox recommendationsContainer;
    private final ComboBox<Crop> cropComboBox;

    public RecommendationView() {
        setSpacing(20);
        setPadding(new Insets(20));

        Label title = new Label("Rekomendasi Cerdas");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 24));

        cropComboBox = new ComboBox<>();
        cropComboBox.setPromptText("Pilih Tanaman");
        cropComboBox.setMaxWidth(300);
        cropComboBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(Crop crop) { return crop == null ? "" : crop.getName(); }
            @Override
            public Crop fromString(String string) { return null; }
        });
        cropComboBox.getItems().setAll(inventoryService.getMyCrops());

        recommendationsContainer = new VBox(15);
        
        cropComboBox.setOnAction(e -> refreshRecommendations());

        getChildren().addAll(title, cropComboBox, recommendationsContainer);
        
        sensorService.startSimulation();
    }

    private void refreshRecommendations() {
        Crop selected = cropComboBox.getValue();
        if (selected == null) return;

        recommendationsContainer.getChildren().clear();
        SensorData latest = sensorService.getLatestData(selected.getId());
        
        if (latest == null) {
            recommendationsContainer.getChildren().add(new Label("Data sensor tidak tersedia. Simulasi sedang berjalan..."));
            return;
        }

        List<RecommendationService.Recommendation> recommendations = recommendationService.generateRecommendations(latest);
        
        for (RecommendationService.Recommendation rec : recommendations) {
            VBox card = new VBox(5);
            card.getStyleClass().add("card");
            
            String color = "#64748B"; // default info
            if ("warning".equals(rec.getSeverity())) color = "#F9A825";
            else if ("danger".equals(rec.getSeverity())) color = "#E53935";

            Label condLabel = new Label(rec.getCondition());
            condLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));
            condLabel.setStyle("-fx-text-fill: " + color + ";");

            Label suggLabel = new Label("Saran: " + rec.getSuggestion());
            suggLabel.setStyle("-fx-text-fill: #1E293B;");

            card.getChildren().addAll(condLabel, suggLabel);
            recommendationsContainer.getChildren().add(card);
        }
    }
}
