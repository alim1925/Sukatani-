package com.sukatani.ui.finance;

import com.sukatani.model.FinancialRecord;
import com.sukatani.service.FinancialService;
import com.sukatani.ui.components.StatsCard;
import com.sukatani.util.SessionManager;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.time.LocalDate;

public class FinanceView extends VBox {
    private final FinancialService financialService = new FinancialService();
    private TableView<FinancialRecord> table;
    private FlowPane summaryPane;

    public FinanceView() {
        setSpacing(20);
        setPadding(new Insets(10));

        Label title = new Label("Catatan Keuangan");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 24));
        title.setStyle("-fx-text-fill: #1E293B;");

        summaryPane = new FlowPane(20, 20);
        
        HBox mainLayout = new HBox(20);
        VBox.setVgrow(mainLayout, Priority.ALWAYS);

        VBox form = createAddRecordForm();
        form.setPrefWidth(300);

        table = createFinanceTable();
        HBox.setHgrow(table, Priority.ALWAYS);

        mainLayout.getChildren().addAll(form, table);
        getChildren().addAll(title, summaryPane, mainLayout);

        refreshData();
    }

    private VBox createAddRecordForm() {
        VBox form = new VBox(10);
        form.getStyleClass().add("card");

        Label formTitle = new Label("Transaksi Baru");
        formTitle.setFont(Font.font("Segoe UI", FontWeight.BOLD, 18));

        ComboBox<String> typeBox = new ComboBox<>();
        typeBox.getItems().addAll("Pendapatan", "Pengeluaran");
        typeBox.setValue("Pendapatan");
        typeBox.setMaxWidth(Double.MAX_VALUE);

        TextField descField = new TextField();
        descField.setPromptText("Deskripsi (misal: Jual padi)");

        TextField amountField = new TextField();
        amountField.setPromptText("Jumlah (Rp)");

        DatePicker datePicker = new DatePicker(LocalDate.now());
        datePicker.setMaxWidth(Double.MAX_VALUE);

        Button addBtn = new Button("Tambah Catatan");
        addBtn.setMaxWidth(Double.MAX_VALUE);
        addBtn.getStyleClass().add("button-primary");

        addBtn.setOnAction(e -> {
            try {
                String typeValue = typeBox.getValue();
                String type = "Pendapatan".equals(typeValue) ? "income" : "expense";
                String desc = descField.getText();
                double amount = Double.parseDouble(amountField.getText());
                
                FinancialRecord record = new FinancialRecord(
                    SessionManager.getCurrentUser().getId(),
                    type,
                    desc,
                    amount,
                    datePicker.getValue()
                );

                if (financialService.addRecord(record)) {
                    new Alert(Alert.AlertType.INFORMATION, "Catatan berhasil ditambahkan!").show();
                    descField.clear();
                    amountField.clear();
                    refreshData();
                }
            } catch (Exception ex) {
                new Alert(Alert.AlertType.ERROR, "Input tidak valid").show();
            }
        });

        form.getChildren().addAll(
            formTitle,
            new Label("Jenis"), typeBox,
            new Label("Deskripsi"), descField,
            new Label("Jumlah"), amountField,
            new Label("Tanggal"), datePicker,
            addBtn
        );

        return form;
    }

    private TableView<FinancialRecord> createFinanceTable() {
        TableView<FinancialRecord> table = new TableView<>();

        TableColumn<FinancialRecord, String> dateCol = new TableColumn<>("Tanggal");
        dateCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getRecordDate().toString()));

        TableColumn<FinancialRecord, String> typeCol = new TableColumn<>("Jenis");
        typeCol.setCellValueFactory(data -> {
            String type = data.getValue().getType();
            return new SimpleStringProperty("income".equals(type) ? "PENDAPATAN" : "PENGELUARAN");
        });

        TableColumn<FinancialRecord, String> descCol = new TableColumn<>("Deskripsi");
        descCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDescription()));

        TableColumn<FinancialRecord, String> amountCol = new TableColumn<>("Jumlah");
        amountCol.setCellValueFactory(data -> {
            String prefix = "income".equalsIgnoreCase(data.getValue().getType()) ? "+" : "-";
            return new SimpleStringProperty(prefix + " Rp " + String.format("%,.0f", data.getValue().getAmount()));
        });

        table.getColumns().addAll(dateCol, typeCol, descCol, amountCol);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        return table;
    }

    private void refreshData() {
        table.getItems().setAll(financialService.getMyRecords());
        
        summaryPane.getChildren().clear();
        summaryPane.getChildren().addAll(
            new StatsCard("Total Pendapatan", String.format("%,.0f", financialService.getTotalIncome()), "Rp", "#2E7D32"),
            new StatsCard("Total Pengeluaran", String.format("%,.0f", financialService.getTotalExpense()), "Rp", "#E53935"),
            new StatsCard("Profit Bersih", String.format("%,.0f", financialService.getNetProfit()), "Rp", "#1B5E20")
        );
    }
}
