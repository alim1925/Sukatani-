package com.sukatani.util;

import javafx.scene.chart.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.control.Label;
import javafx.geometry.Insets;

public class ChartUtil {

    public static VBox createBarChart(String title, String categoryLabel, String valueLabel, XYChart.Series<String, Number>... series) {
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel(categoryLabel);

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel(valueLabel);

        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle(null); // We use a custom label for title
        barChart.setAnimated(false);
        barChart.setLegendVisible(true);
        barChart.getData().addAll(series);
        
        // Styling
        barChart.setStyle("-fx-background-color: white;");

        VBox container = createChartContainer(title, barChart);
        return container;
    }

    public static VBox createPieChart(String title, PieChart.Data... data) {
        PieChart pieChart = new PieChart();
        pieChart.getData().addAll(data);
        pieChart.setLabelsVisible(true);
        pieChart.setLegendVisible(true);
        pieChart.setAnimated(false);
        
        VBox container = createChartContainer(title, pieChart);
        return container;
    }

    private static VBox createChartContainer(String title, Chart chart) {
        VBox container = new VBox(10);
        container.getStyleClass().add("card");
        container.setPadding(new Insets(20));

        Label titleLabel = new Label(title);
        titleLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 18));
        titleLabel.setStyle("-fx-text-fill: #1E293B;");

        container.getChildren().addAll(titleLabel, chart);
        return container;
    }
}
