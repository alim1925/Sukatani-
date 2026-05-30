package com.sukatani.ui.preorder;

import com.sukatani.model.Crop;
import com.sukatani.model.PreOrder;
import com.sukatani.service.InventoryService;
import com.sukatani.service.PreOrderService;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.util.List;

public class PreOrderView extends VBox {
    private final PreOrderService preOrderService = new PreOrderService();
    private final InventoryService inventoryService = new InventoryService();
    private TableView<PreOrder> table;
    private ComboBox<Crop> cropComboBox;

    public PreOrderView() {
        setSpacing(20);
        setPadding(new Insets(10));

        Label title = new Label("Manajemen Pesanan (Pre-Order)");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 24));
        title.setStyle("-fx-text-fill: #1E293B;");

        HBox mainLayout = new HBox(20);
        VBox.setVgrow(mainLayout, Priority.ALWAYS);

        VBox form = createPreOrderForm();
        form.setPrefWidth(300);

        table = createPreOrderTable();
        HBox.setHgrow(table, Priority.ALWAYS);

        mainLayout.getChildren().addAll(form, table);
        getChildren().addAll(title, mainLayout);

        refreshData();
    }

    private VBox createPreOrderForm() {
        VBox form = new VBox(10);
        form.getStyleClass().add("card");

        Label formTitle = new Label("Pesanan Baru");
        formTitle.setFont(Font.font("Segoe UI", FontWeight.BOLD, 18));

        cropComboBox = new ComboBox<>();
        cropComboBox.setPromptText("Pilih Tanaman");
        cropComboBox.setMaxWidth(Double.MAX_VALUE);
        cropComboBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(Crop crop) {
                return crop == null ? "" : crop.getName();
            }
            @Override
            public Crop fromString(String string) { return null; }
        });

        TextField buyerField = new TextField();
        buyerField.setPromptText("Nama Pembeli");

        TextField qtyField = new TextField();
        qtyField.setPromptText("Jumlah");

        TextField priceField = new TextField();
        priceField.setPromptText("Total Harga");

        Button submitBtn = new Button("Catat Pesanan");
        submitBtn.setMaxWidth(Double.MAX_VALUE);
        submitBtn.getStyleClass().add("button-primary");

        submitBtn.setOnAction(e -> {
            try {
                Crop selectedCrop = cropComboBox.getValue();
                if (selectedCrop == null) {
                    new Alert(Alert.AlertType.WARNING, "Silakan pilih tanaman").show();
                    return;
                }

                PreOrder po = new PreOrder(
                    selectedCrop.getId(),
                    buyerField.getText(),
                    Double.parseDouble(qtyField.getText()),
                    Double.parseDouble(priceField.getText()),
                    LocalDate.now(),
                    "pending"
                );

                if (preOrderService.placeOrder(po)) {
                    new Alert(Alert.AlertType.INFORMATION, "Pesanan berhasil dicatat!").show();
                    buyerField.clear();
                    qtyField.clear();
                    priceField.clear();
                    refreshData();
                }
            } catch (Exception ex) {
                new Alert(Alert.AlertType.ERROR, "Input tidak valid").show();
            }
        });

        form.getChildren().addAll(
            formTitle,
            new Label("Tanaman"), cropComboBox,
            new Label("Nama Pembeli"), buyerField,
            new Label("Jumlah"), qtyField,
            new Label("Harga"), priceField,
            submitBtn
        );

        return form;
    }

    private TableView<PreOrder> createPreOrderTable() {
        TableView<PreOrder> table = new TableView<>();

        TableColumn<PreOrder, String> buyerCol = new TableColumn<>("Pembeli");
        buyerCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getBuyerName()));

        TableColumn<PreOrder, String> cropCol = new TableColumn<>("ID Tanaman");
        cropCol.setCellValueFactory(data -> {
            return new SimpleStringProperty("ID: " + data.getValue().getCropId());
        });

        TableColumn<PreOrder, String> qtyCol = new TableColumn<>("Jumlah");
        qtyCol.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getQuantity())));

        TableColumn<PreOrder, String> priceCol = new TableColumn<>("Harga");
        priceCol.setCellValueFactory(data -> new SimpleStringProperty("Rp " + String.format("%,.0f", data.getValue().getPrice())));

        TableColumn<PreOrder, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(data -> {
            String status = data.getValue().getStatus();
            return new SimpleStringProperty("pending".equals(status) ? "MENUNGGU" : status.toUpperCase());
        });

        table.getColumns().addAll(buyerCol, cropCol, qtyCol, priceCol, statusCol);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        return table;
    }

    private void refreshData() {
        table.getItems().setAll(preOrderService.getMyPreOrders());
        cropComboBox.getItems().setAll(inventoryService.getMyCrops());
    }
}
