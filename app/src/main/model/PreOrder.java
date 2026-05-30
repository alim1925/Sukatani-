package com.sukatani.model;

import java.time.LocalDate;

public class PreOrder {
    private int id;
    private int cropId;
    private String buyerName;
    private double quantity;
    private double price;
    private LocalDate orderDate;
    private String status;

    public PreOrder() {}

    public PreOrder(int cropId, String buyerName, double quantity, double price, LocalDate orderDate, String status) {
        this.cropId = cropId;
        this.buyerName = buyerName;
        this.quantity = quantity;
        this.price = price;
        this.orderDate = orderDate;
        this.status = status;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getCropId() { return cropId; }
    public void setCropId(int cropId) { this.cropId = cropId; }

    public String getBuyerName() { return buyerName; }
    public void setBuyerName(String buyerName) { this.buyerName = buyerName; }

    public double getQuantity() { return quantity; }
    public void setQuantity(double quantity) { this.quantity = quantity; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public LocalDate getOrderDate() { return orderDate; }
    public void setOrderDate(LocalDate orderDate) { this.orderDate = orderDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
