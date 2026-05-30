package com.sukatani.model;

import java.time.LocalDate;

public class ScheduleTask {
    private int id;
    private int cropId;
    private String activityType;
    private LocalDate scheduledDate;
    private String status;
    private String notes;

    public ScheduleTask() {}

    public ScheduleTask(int cropId, String activityType, LocalDate scheduledDate, String status, String notes) {
        this.cropId = cropId;
        this.activityType = activityType;
        this.scheduledDate = scheduledDate;
        this.status = status;
        this.notes = notes;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getCropId() { return cropId; }
    public void setCropId(int cropId) { this.cropId = cropId; }

    public String getActivityType() { return activityType; }
    public void setActivityType(String activityType) { this.activityType = activityType; }

    public LocalDate getScheduledDate() { return scheduledDate; }
    public void setScheduledDate(LocalDate scheduledDate) { this.scheduledDate = scheduledDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}
