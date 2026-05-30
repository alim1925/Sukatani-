package com.sukatani.model;

import java.time.LocalDateTime;

public class SensorData {
    private int id;
    private int cropId;
    private double soilMoisture;
    private double temperature;
    private double humidity;
    private double ph;
    private LocalDateTime recordedAt;

    public SensorData() {}

    public SensorData(int cropId, double soilMoisture, double temperature, double humidity, double ph) {
        this.cropId = cropId;
        this.soilMoisture = soilMoisture;
        this.temperature = temperature;
        this.humidity = humidity;
        this.ph = ph;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getCropId() { return cropId; }
    public void setCropId(int cropId) { this.cropId = cropId; }

    public double getSoilMoisture() { return soilMoisture; }
    public void setSoilMoisture(double soilMoisture) { this.soilMoisture = soilMoisture; }

    public double getTemperature() { return temperature; }
    public void setTemperature(double temperature) { this.temperature = temperature; }

    public double getHumidity() { return humidity; }
    public void setHumidity(double humidity) { this.humidity = humidity; }

    public double getPh() { return ph; }
    public void setPh(double ph) { this.ph = ph; }

    public LocalDateTime getRecordedAt() { return recordedAt; }
    public void setRecordedAt(LocalDateTime recordedAt) { this.recordedAt = recordedAt; }
}
