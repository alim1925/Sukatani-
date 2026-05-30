package com.sukatani.ui.sensor;

import com.sukatani.model.Crop;
import com.sukatani.model.SensorData;
import com.sukatani.service.InventoryService;
import com.sukatani.service.SensorService;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;
import javafx.util.StringConverter;

import java.util.List;

public class SensorView extends VBox {
    private final SensorService sensorService = new SensorService();
    private final InventoryService inventoryService = new InventoryService();
    private final ComboBox<Crop> cropComboBox;
    private final LineChart<Number, Number> moistureChart;
    private final XYChart.Series<Number, Number> moistureSeries;
    private Timeline updateTimeline;

    public SensorView() {
        setSpacing(20);
        setPadding(new Insets(20));

        Label title = new Label("Monitoring Sensor Real-time");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 24));

        cropComboBox = new ComboBox<>();
        cropComboBox.setPromptText("Pilih Tanaman untuk Dimonitor");
        cropComboBox.setMaxWidth(300);
        cropComboBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(Crop crop) { return crop == null ? "" : crop.getName(); }
            @Override
            public Crop fromString(String string) { return null; }
        });

        List<Crop> myCrops = inventoryService.getMyCrops();
        cropComboBox.getItems().setAll(myCrops);

        // Chart setup
        NumberAxis xAxis = new NumberAxis();
        xAxis.setLabel("Waktu (catatan)");
        NumberAxis yAxis = new NumberAxis(0, 100, 10);
        yAxis.setLabel("Kelembaban (%)");

        moistureChart = new LineChart<>(xAxis, yAxis);
        moistureChart.setTitle("Tren Kelembaban Tanah");
        moistureChart.setAnimated(false);
        moistureChart.getStyleClass().add("card");

        moistureSeries = new XYChart.Series<>();
        moistureSeries.setName("Kelembaban");
        moistureChart.getData().add(moistureSeries);

        VBox.setVgrow(moistureChart, Priority.ALWAYS);

        getChildren().addAll(title, cropComboBox, moistureChart);

        sensorService.startSimulation();
        setupUpdateTimeline();
    }

    private void setupUpdateTimeline() {
        updateTimeline = new Timeline(new KeyFrame(Duration.seconds(3), e -> updateChart()));
        updateTimeline.setCycleCount(Timeline.INDEFINITE);
        updateTimeline.play();
    }

    private void updateChart() {
        Crop selected = cropComboBox.getValue();
        if (selected == null) return;

        List<SensorData> dataList = sensorService.getRecentData(selected.getId());
        moistureSeries.getData().clear();

        for (int i = 0; i < dataList.size(); i++) {
            SensorData data = dataList.get(dataList.size() - 1 - i);
            moistureSeries.getData().add(new XYChart.Data<>(i, data.getSoilMoisture()));
        }
    }

    public void stopUpdates() {
        if (updateTimeline != null) updateTimeline.stop();
    }
}
