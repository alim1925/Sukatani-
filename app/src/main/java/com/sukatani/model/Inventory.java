package com.sukatani.model;

public class Inventory {
    private int id;
    private int cropId;
    private double quantity;
    private String unit;
    private String status;

    public Inventory() {}

    public Inventory(int cropId, double quantity, String unit, String status) {
        this.cropId = cropId;
        this.quantity = quantity;
        this.unit = unit;
        this.status = status;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getCropId() { return cropId; }
    public void setCropId(int cropId) { this.cropId = cropId; }

    public double getQuantity() { return quantity; }
    public void setQuantity(double quantity) { this.quantity = quantity; }

    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
