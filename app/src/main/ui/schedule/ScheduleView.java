package com.sukatani.ui.schedule;

import com.sukatani.model.Crop;
import com.sukatani.model.ScheduleTask;
import com.sukatani.service.InventoryService;
import com.sukatani.service.ScheduleService;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.StringConverter;

import java.time.LocalDate;

public class ScheduleView extends VBox {
    private final ScheduleService scheduleService = new ScheduleService();
    private final InventoryService inventoryService = new InventoryService();
    private TableView<ScheduleTask> table;
    private ComboBox<Crop> cropComboBox;

    public ScheduleView() {
        setSpacing(20);
        setPadding(new Insets(10));

        Label title = new Label("Jadwal Kegiatan Pertanian");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 24));
        title.setStyle("-fx-text-fill: #1E293B;");

        HBox mainLayout = new HBox(20);
        VBox.setVgrow(mainLayout, Priority.ALWAYS);

        VBox form = createAddTaskForm();
        form.setPrefWidth(300);

        table = createScheduleTable();
        HBox.setHgrow(table, Priority.ALWAYS);

        mainLayout.getChildren().addAll(form, table);
        getChildren().addAll(title, mainLayout);

        refreshData();
    }

    private VBox createAddTaskForm() {
        VBox form = new VBox(10);
        form.getStyleClass().add("card");

        Label formTitle = new Label("Atur Jadwal");
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

        TextField activityField = new TextField();
        activityField.setPromptText("Kegiatan (misal: Penyiraman)");

        DatePicker datePicker = new DatePicker(LocalDate.now());
        datePicker.setMaxWidth(Double.MAX_VALUE);

        TextArea notesArea = new TextArea();
        notesArea.setPromptText("Catatan...");
        notesArea.setPrefRowCount(3);

        Button addBtn = new Button("Tambah Jadwal");
        addBtn.setMaxWidth(Double.MAX_VALUE);
        addBtn.getStyleClass().add("button-primary");

        addBtn.setOnAction(e -> {
            try {
                Crop selectedCrop = cropComboBox.getValue();
                if (selectedCrop == null) {
                    new Alert(Alert.AlertType.WARNING, "Pilih tanaman").show();
                    return;
                }

                ScheduleTask task = new ScheduleTask(
                    selectedCrop.getId(),
                    activityField.getText(),
                    datePicker.getValue(),
                    "pending",
                    notesArea.getText()
                );

                if (scheduleService.addTask(task)) {
                    new Alert(Alert.AlertType.INFORMATION, "Jadwal berhasil ditambahkan!").show();
                    activityField.clear();
                    notesArea.clear();
                    refreshData();
                }
            } catch (Exception ex) {
                new Alert(Alert.AlertType.ERROR, "Input tidak valid").show();
            }
        });

        form.getChildren().addAll(
            formTitle,
            new Label("Tanaman"), cropComboBox,
            new Label("Kegiatan"), activityField,
            new Label("Tanggal"), datePicker,
            new Label("Catatan"), notesArea,
            addBtn
        );

        return form;
    }

    private TableView<ScheduleTask> createScheduleTable() {
        TableView<ScheduleTask> table = new TableView<>();

        TableColumn<ScheduleTask, String> dateCol = new TableColumn<>("Tanggal");
        dateCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getScheduledDate().toString()));

        TableColumn<ScheduleTask, String> cropCol = new TableColumn<>("ID Tanaman");
        cropCol.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getCropId())));

        TableColumn<ScheduleTask, String> activityCol = new TableColumn<>("Kegiatan");
        activityCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getActivityType()));

        TableColumn<ScheduleTask, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getStatus().toUpperCase()));

        TableColumn<ScheduleTask, Void> actionCol = new TableColumn<>("Aksi");
        actionCol.setCellFactory(param -> new TableCell<>() {
            private final Button btn = new Button("Selesai");
            {
                btn.setStyle("-fx-background-color: #2E7D32; -fx-text-fill: white; -fx-font-size: 10px;");
                btn.setOnAction(event -> {
                    ScheduleTask task = getTableView().getItems().get(getIndex());
                    if (scheduleService.completeTask(task.getId())) {
                        refreshData();
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    ScheduleTask task = getTableView().getItems().get(getIndex());
                    if ("completed".equals(task.getStatus())) {
                        btn.setDisable(true);
                        btn.setText("Selesai");
                        btn.setStyle("-fx-background-color: #DCE7DC; -fx-text-fill: #64748B; -fx-font-size: 10px;");
                    } else {
                        btn.setDisable(false);
                        btn.setText("Selesai");
                        btn.setStyle("-fx-background-color: #2E7D32; -fx-text-fill: white; -fx-font-size: 10px;");
                    }
                    setGraphic(btn);
                }
            }
        });

        table.getColumns().addAll(dateCol, cropCol, activityCol, statusCol, actionCol);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        return table;
    }

    private void refreshData() {
        table.getItems().setAll(scheduleService.getMyUpcomingTasks());
        cropComboBox.getItems().setAll(inventoryService.getMyCrops());
    }
}
