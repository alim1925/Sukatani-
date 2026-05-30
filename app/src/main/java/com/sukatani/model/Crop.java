package com.sukatani.model;

import java.time.LocalDate;

public class Crop {
    private int id;
    private int userId;
    private String name;
    private LocalDate plantingDate;
    private LocalDate estimatedHarvestDate;
    private double estimatedQuantity;

    public Crop() {}

    public Crop(int userId, String name, LocalDate plantingDate, LocalDate estimatedHarvestDate, double estimatedQuantity) {
        this.userId = userId;
        this.name = name;
        this.plantingDate = plantingDate;
        this.estimatedHarvestDate = estimatedHarvestDate;
        this.estimatedQuantity = estimatedQuantity;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public LocalDate getPlantingDate() { return plantingDate; }
    public void setPlantingDate(LocalDate plantingDate) { this.plantingDate = plantingDate; }

    public LocalDate getEstimatedHarvestDate() { return estimatedHarvestDate; }
    public void setEstimatedHarvestDate(LocalDate estimatedHarvestDate) { this.estimatedHarvestDate = estimatedHarvestDate; }

    public double getEstimatedQuantity() { return estimatedQuantity; }
    public void setEstimatedQuantity(double estimatedQuantity) { this.estimatedQuantity = estimatedQuantity; }
}
