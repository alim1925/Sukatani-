package com.sukatani.ui.inventory;

import com.sukatani.model.Crop;
import com.sukatani.model.Inventory;
import com.sukatani.service.InventoryService;
import com.sukatani.util.SessionManager;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.time.LocalDate;

public class InventoryView extends VBox {
    private final InventoryService inventoryService = new InventoryService();
    private TableView<Crop> table;

    public InventoryView() {
        setSpacing(20);
        setPadding(new Insets(10));

        Label title = new Label("Inventaris Tanaman");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 24));
        title.setStyle("-fx-text-fill: #1E293B;");

        HBox mainLayout = new HBox(20);
        VBox.setVgrow(mainLayout, Priority.ALWAYS);

        // Form to add crop
        VBox form = createAddCropForm();
        form.setPrefWidth(300);

        // Table to list crops
        table = createCropTable();
        HBox.setHgrow(table, Priority.ALWAYS);

        mainLayout.getChildren().addAll(form, table);
        getChildren().addAll(title, mainLayout);

        refreshTable();
    }

    private VBox createAddCropForm() {
        VBox form = new VBox(10);
        form.getStyleClass().add("card");

        Label formTitle = new Label("Tambah Tanaman Baru");
        formTitle.setFont(Font.font("Segoe UI", FontWeight.BOLD, 18));

        TextField nameField = new TextField();
        nameField.setPromptText("Nama Tanaman (misal: Padi, Jagung)");

        DatePicker plantingDate = new DatePicker(LocalDate.now());
        DatePicker harvestDate = new DatePicker(LocalDate.now().plusMonths(4));

        TextField quantityField = new TextField();
        quantityField.setPromptText("Estimasi Jumlah");

        TextField unitField = new TextField("kg");
        unitField.setPromptText("Satuan");

        Button addBtn = new Button("Tambah Tanaman");
        addBtn.setMaxWidth(Double.MAX_VALUE);
        addBtn.getStyleClass().add("button-primary");

        addBtn.setOnAction(e -> {
            try {
                String name = nameField.getText();
                double qty = Double.parseDouble(quantityField.getText());
                String unit = unitField.getText();
                
                Crop crop = new Crop(
                    SessionManager.getCurrentUser().getId(),
                    name,
                    plantingDate.getValue(),
                    harvestDate.getValue(),
                    qty
                );

                if (inventoryService.addCrop(crop, unit)) {
                    new Alert(Alert.AlertType.INFORMATION, "Tanaman berhasil ditambahkan!").show();
                    nameField.clear();
                    quantityField.clear();
                    refreshTable();
                }
            } catch (Exception ex) {
                new Alert(Alert.AlertType.ERROR, "Input tidak valid: " + ex.getMessage()).show();
            }
        });

        form.getChildren().addAll(
            formTitle, 
            new Label("Nama"), nameField, 
            new Label("Tanggal Tanam"), plantingDate,
            new Label("Estimasi Tanggal Panen"), harvestDate,
            new Label("Estimasi Jumlah"), quantityField,
            new Label("Satuan"), unitField,
            addBtn
        );

        return form;
    }

    private TableView<Crop> createCropTable() {
        TableView<Crop> table = new TableView<>();
        
        TableColumn<Crop, String> nameCol = new TableColumn<>("Nama");
        nameCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));

        TableColumn<Crop, String> plantingCol = new TableColumn<>("Tanggal Tanam");
        plantingCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPlantingDate().toString()));

        TableColumn<Crop, String> harvestCol = new TableColumn<>("Tanggal Panen");
        harvestCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getEstimatedHarvestDate().toString()));

        TableColumn<Crop, String> stockCol = new TableColumn<>("Stok Saat Ini");
        stockCol.setCellValueFactory(data -> {
            Inventory inv = inventoryService.getInventoryByCropId(data.getValue().getId());
            return new SimpleStringProperty(inv != null ? inv.getQuantity() + " " + inv.getUnit() : "0");
        });

        table.getColumns().addAll(nameCol, plantingCol, harvestCol, stockCol);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
        return table;
    }

    private void refreshTable() {
        table.getItems().setAll(inventoryService.getMyCrops());
    }
}
