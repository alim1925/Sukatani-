package com.sukatani.model;

import java.time.LocalDate;

public class FinancialRecord {
    private int id;
    private int userId;
    private String type; // 'income' or 'expense'
    private String description;
    private double amount;
    private LocalDate recordDate;

    public FinancialRecord() {}

    public FinancialRecord(int userId, String type, String description, double amount, LocalDate recordDate) {
        this.userId = userId;
        this.type = type;
        this.description = description;
        this.amount = amount;
        this.recordDate = recordDate;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public LocalDate getRecordDate() { return recordDate; }
    public void setRecordDate(LocalDate recordDate) { this.recordDate = recordDate; }
}
