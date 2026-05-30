package com.sukatani.service;

import com.sukatani.model.Crop;
import com.sukatani.model.SensorData;
import com.sukatani.repository.SensorRepository;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.sql.SQLException;
import java.util.List;
import java.util.Random;

public class SensorService {
    private static SensorService instance;
    private final SensorRepository sensorRepo = new SensorRepository();
    private final InventoryService inventoryService = new InventoryService();
    private final Random random = new Random();
    private Timeline simulationTimeline;

    private SensorService() {}

    public static synchronized SensorService getInstance() {
        if (instance == null) {
            instance = new SensorService();
        }
        return instance;
    }

    public void startSimulation() {
        if (simulationTimeline != null) return;

        simulationTimeline = new Timeline(new KeyFrame(Duration.seconds(3), e -> {
            if (com.sukatani.util.SessionManager.isLoggedIn()) {
                simulateData();
            }
        }));
        simulationTimeline.setCycleCount(Timeline.INDEFINITE);
        simulationTimeline.play();
    }

    public void stopSimulation() {
        if (simulationTimeline != null) {
            simulationTimeline.stop();
            simulationTimeline = null;
        }
    }

    private void simulateData() {
        List<Crop> crops = inventoryService.getMyCrops();
        for (Crop crop : crops) {
            // Simulate random data
            double moisture = 20 + random.nextDouble() * 60; // 20% - 80%
            double temp = 25 + random.nextDouble() * 15;    // 25C - 40C
            double humidity = 40 + random.nextDouble() * 40; // 40% - 80%
            double ph = 5.0 + random.nextDouble() * 3.0;    // 5.0 - 8.0

            SensorData data = new SensorData(crop.getId(), moisture, temp, humidity, ph);
            try {
                sensorRepo.create(data);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public List<SensorData> getRecentData(int cropId) {
        try {
            return sensorRepo.getLatestByCropId(cropId, 20);
        } catch (SQLException e) {
            e.printStackTrace();
            return List.of();
        }
    }

    public SensorData getLatestData(int cropId) {
        try {
            List<SensorData> data = sensorRepo.getLatestByCropId(cropId, 1);
            return data.isEmpty() ? null : data.get(0);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
