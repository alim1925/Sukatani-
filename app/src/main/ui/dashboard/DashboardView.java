package com.sukatani.ui.dashboard;

import com.sukatani.app.AppNavigator;
import com.sukatani.model.Crop;
import com.sukatani.model.Inventory;
import com.sukatani.service.FinancialService;
import com.sukatani.service.InventoryService;
import com.sukatani.service.PreOrderService;
import com.sukatani.service.ScheduleService;
import com.sukatani.ui.components.HeaderBar;
import com.sukatani.ui.components.Sidebar;
import com.sukatani.ui.components.StatsCard;
import com.sukatani.util.ChartUtil;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.*;
import javafx.scene.control.ScrollPane;

public class DashboardView {
    public Scene getScene() {
        BorderPane root = new BorderPane();
        root.getStylesheets().add(getClass().getResource("/css/base.css").toExternalForm());
        root.getStylesheets().add(getClass().getResource("/css/theme.css").toExternalForm());

        HeaderBar headerBar = new HeaderBar();
        Sidebar sidebar = new Sidebar(headerBar);
        
        StackPane contentArea = new StackPane();
        contentArea.setPadding(new Insets(30));
        AppNavigator.setContentArea(contentArea);

        root.setLeft(sidebar);
        root.setTop(headerBar);
        
        ScrollPane scrollPane = new ScrollPane(contentArea);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-background: #F7FAF7;");
        root.setCenter(scrollPane);

        // Initial View (Summary Dashboard)
        new com.sukatani.service.SensorService().startSimulation();
        AppNavigator.setView(createDefaultDashboard());

        return new Scene(root, 1200, 800);
    }

    public static VBox createDefaultDashboard() {
        FinancialService financeService = new FinancialService();
        InventoryService inventoryService = new InventoryService();
        PreOrderService preOrderService = new PreOrderService();
        ScheduleService scheduleService = new ScheduleService();

        VBox dashboard = new VBox(30);
        
        // Stats Cards
        FlowPane statsPane = new FlowPane(20, 20);
        double totalStock = inventoryService.getMyCrops().stream()
                .mapToDouble(c -> {
                    Inventory inv = inventoryService.getInventoryByCropId(c.getId());
                    return inv != null ? inv.getQuantity() : 0;
                }).sum();

        statsPane.getChildren().addAll(
            new StatsCard("Total Stok", String.format("%,.0f", totalStock), "kg", "#2E7D32"),
            new StatsCard("Total Pre-order", String.valueOf(preOrderService.getMyPreOrders().size()), "Pesanan", "#F9A825"),
            new StatsCard("Profit Bersih", "Rp " + String.format("%,.0f", financeService.getNetProfit()), "", "#1B5E20"),
            new StatsCard("Tugas Mendatang", String.valueOf(scheduleService.getMyUpcomingTasks().size()), "Tugas", "#E53935")
        );

        // Charts Section
        HBox chartsLayout = new HBox(20);
        
        // 1. Finance Bar Chart
        XYChart.Series<String, Number> incomeSeries = new XYChart.Series<>();
        incomeSeries.setName("Pendapatan");
        incomeSeries.getData().add(new XYChart.Data<>("Saat Ini", financeService.getTotalIncome()));

        XYChart.Series<String, Number> expenseSeries = new XYChart.Series<>();
        expenseSeries.setName("Pengeluaran");
        expenseSeries.getData().add(new XYChart.Data<>("Saat Ini", financeService.getTotalExpense()));

        VBox financeChart = ChartUtil.createBarChart("Ringkasan Keuangan", "Status", "Jumlah (Rp)", incomeSeries, expenseSeries);
        HBox.setHgrow(financeChart, Priority.ALWAYS);

        // 2. Inventory Pie Chart
        PieChart.Data[] pieData = inventoryService.getMyCrops().stream()
                .map(c -> {
                    Inventory inv = inventoryService.getInventoryByCropId(c.getId());
                    return new PieChart.Data(c.getName(), inv != null ? inv.getQuantity() : 0);
                }).toArray(PieChart.Data[]::new);

        VBox inventoryChart = ChartUtil.createPieChart("Distribusi Inventaris", pieData);
        HBox.setHgrow(inventoryChart, Priority.ALWAYS);

        chartsLayout.getChildren().addAll(financeChart, inventoryChart);

        dashboard.getChildren().addAll(statsPane, chartsLayout);
        return dashboard;
    }
}
